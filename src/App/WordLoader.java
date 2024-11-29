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
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // 빈 줄이나 주석은 무시
                }

                // 단어 (예: swim, jump 등)
                String englishWord = line.trim();

                // 다음 줄부터 품사별 정보
                String partOfSpeechLine;
                if ((partOfSpeechLine = reader.readLine()) == null) {
                    continue; // 빈 줄이거나 끝까지 도달한 경우
                }

                String[] partOfSpeechEntries = partOfSpeechLine.split("\\),\\(");

                Word word = new Word(englishWord); // 새로운 Word 객체 생성

                for (String entry : partOfSpeechEntries) {
                    // 예시: (동사>수영하다,발음기호>s·wim,1차강세>1,2차강세>0,발음>스윔,{현재>swim,과거>swam,과거분사>swum})
                    String[] elements = entry.split(",");
                    if (elements.length < 6) {
                        System.out.println("파일 저장 중 오류가 발생했습니다. 품사 정보가 부족합니다.");
                        System.exit(0);
                    }

                    // 품사별 정보 처리
                    String pos = elements[0].split(">")[1].trim(); // 품사
                    String meaning = elements[1].split(">")[1].trim(); // 뜻
                    String pronunciation = elements[2].split(">")[1].trim(); // 발음기호
                    int primaryStress = Integer.parseInt(elements[3].split(">")[1].trim()); // 1차 강세
                    int secondaryStress = Integer.parseInt(elements[4].split(">")[1].trim()); // 2차 강세
                    String pronunciationText = elements[5].split(">")[1].trim(); // 발음

                    // 추가 정보 처리
                    Map<String, String> additionalInfo = new HashMap<>();
                    String additional = elements[6].split(">")[1].trim();
                    String[] additionalParts = additional.split(",");
                    for (String part : additionalParts) {
                        String[] keyValue = part.split(">");
                        if (keyValue.length == 2) {
                            additionalInfo.put(keyValue[0].trim(), keyValue[1].trim());
                        }
                    }

                    // PartOfSpeech 객체 생성
                    Word.PartOfSpeech partOfSpeech = new Word.PartOfSpeech(
                            meaning,
                            pronunciation,
                            primaryStress,
                            secondaryStress,
                            pronunciationText,
                            additionalInfo
                    );

                    // Word 객체에 품사 추가
                    word.addPartOfSpeech(pos, partOfSpeech);
                }

                // 단어 목록에 추가
                wordList.add(word);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
            System.exit(0);
        }
    }
}
