package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";

    public List<Word> loadWords(List<Word> wordList) {
        wordList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // 빈 줄이나 주석 무시
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 영어 단어 (예: swim)
                String englishWord = line.trim();
                Word word = new Word(englishWord);

                // 다음 줄에 품사 정보가 위치함
                String partOfSpeechLine = reader.readLine();
                if (partOfSpeechLine == null || partOfSpeechLine.trim().isEmpty()) {
                    continue; // 품사 정보가 없는 경우 건너뜀
                }

                // 품사 정보를 괄호 단위로 분리
                String[] partOfSpeechEntries = partOfSpeechLine.split("\\),\\(");

                for (String entry : partOfSpeechEntries) {
                    try {
                        entry = entry.replaceAll("[()]", ""); // 괄호 제거

                        // 필드 추출
                        String[] elements = entry.split(",");

                        if (elements.length < 6) {
                            throw new IllegalArgumentException("Entry does not have enough fields: " + entry);
                        }

                        // `>`로 올바르게 분리하여 pos와 다른 필드를 구분
                        String[] posAndMeaning = elements[0].split(">");
                        if (posAndMeaning.length < 2) {
                            throw new IllegalArgumentException("Invalid pos and meaning format: " + elements[0]);
                        }
                        String pos = posAndMeaning[0].trim(); // 품사
                        String meaning = posAndMeaning[1].trim(); // 뜻

                        // 나머지 필드 추출
                        String pronunciation = extractField(elements[1], "발음기호");
                        int primaryStress = parseStressValue(extractField(elements[2], "1차강세"));
                        int secondaryStress = parseStressValue(extractField(elements[3], "2차강세"));
                        String pronunciationText = extractField(elements[4], "발음");

                        // 추가 정보 처리
                        Map<String, String> additionalInfo = new HashMap<>();
                        if (elements.length > 5) {
                            String additional = extractField(elements[5], "추가정보");
                            String[] additionalParts = additional.split(",");
                            for (String part : additionalParts) {
                                String[] keyValue = part.split(">");
                                if (keyValue.length == 2) {
                                    additionalInfo.put(keyValue[0].trim(), keyValue[1].trim());
                                }
                            }
                        }

                        // PartOfSpeech 생성 및 Word에 추가
                        Word.PartOfSpeech partOfSpeech = new Word.PartOfSpeech(
                                meaning, pronunciation, primaryStress, secondaryStress, pronunciationText, additionalInfo
                        );
                        word.addPartOfSpeech(pos, partOfSpeech);
                    } catch (Exception e) {
                        System.out.println("ERROR: Failed to process entry: " + entry);
                        System.out.println("ERROR: Exception details: " + e.getMessage());
                    }
                }

                // Word 리스트에 추가
                wordList.add(word);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
        }

        return wordList;
    }

    // 특정 필드를 안전하게 추출하는 메서드
    private String extractField(String element, String fieldName) {
        try {
            String[] parts = element.split(">");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid format for " + fieldName + ": " + element);
            }
            return parts[1].trim();
        } catch (Exception e) {
            System.out.println("ERROR: Failed to extract " + fieldName + " from: " + element);
            throw e; // 예외를 다시 던져서 상위에서 처리
        }
    }

    // 강세 값을 검증 및 파싱하는 메서드
    private int parseStressValue(String value) {
        if (!value.matches("\\d+")) { // 숫자 형식 검증
            throw new NumberFormatException("Invalid stress value: " + value);
        }
        return Integer.parseInt(value);
    }
}
