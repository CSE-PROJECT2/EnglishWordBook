package App;

import Add.WordValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";
    private WordValidator validator = new WordValidator();

    public void loadWords(List<Word> wordList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 라인을 ':' 기준으로 5개로 분리 (영어 단어, 음절로 구분된 단어, 발음, 강세 위치, 의미)
                String[] parts = line.split(":", 5);

                // 데이터가 부족할 경우 오류 처리
                if (parts.length < 5) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                            + "프로그램을 종료합니다.");
                    System.exit(0);
                }

                String englishWord = parts[0].trim();
                String syllableSeparated = parts[1].trim();
                String pronunciation = parts[2].trim();

                // 유효성 검사
                if (!validator.hasValidLength(englishWord) ||
                        !validator.noTabOrNewLine(englishWord) ||
                        !validator.noLeadingOrTrailingSpaces(englishWord) ||
                        !validator.isValidEnglishWord(englishWord)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                            + "유효하지 않은 영단어 형식입니다. 프로그램을 종료합니다.");
                    System.exit(0);
                }

                if (!validator.isValidSyllableFormat(englishWord, syllableSeparated)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                            + "유효하지 않은 음절 구분 형식입니다. 프로그램을 종료합니다.");
                    System.exit(0);
                }

                if (!validator.isValidPronunciation(pronunciation)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                            + "발음이 유효하지 않습니다. 프로그램을 종료합니다.");
                    System.exit(0);
                }

                int accentPosition=-1;
                try {
                    accentPosition = Integer.parseInt(parts[3].trim());
                    if (!validator.isValidAccentPosition(syllableSeparated, accentPosition)) {
                        System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                                + "강세 위치가 잘못되었습니다. 프로그램을 종료합니다.");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                            + "강세 위치는 숫자로 입력되어야 합니다. 프로그램을 종료합니다.");
                    System.exit(0);
                }

                // 품사와 의미 파트에서 괄호 제거
                String meaningPart = parts[4].replaceAll("\\(", "").replaceAll("\\)", "");
                String[] posAndMeanings = meaningPart.split(",");

                Map<String, String> meanings = new HashMap<>();
                for (String posMeaning : posAndMeanings) {
                    String[] posMean = posMeaning.split(":");
                    if (posMean.length == 2) {
                        String pos = posMean[0].trim();
                        String meaning = posMean[1].trim();

                        // 품사 및 뜻 개별 검증
                        if (!validator.isAllowedPos(pos)) {
                            System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                                    + "유효하지 않은 품사입니다: " + pos + "\n프로그램을 종료합니다.");
                            System.exit(0);
                        }

                        if (!validator.isValidMeaning(meaning)) {
                            System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                                    + "유효하지 않은 뜻입니다: " + meaning + "\n프로그램을 종료합니다.");
                            System.exit(0);
                        }

                        meanings.put(pos, meaning);
                    } else {
                        System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                                + "유효하지 않은 품사와 뜻 형식입니다. 프로그램을 종료합니다.");
                        System.exit(0);
                    }
                }

                int syllableCount = syllableSeparated.split("·").length;

                Word word = new Word(englishWord, syllableSeparated, pronunciation, accentPosition, syllableCount, meanings);
                wordList.add(word);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
            System.exit(0);
        }
    }
}
