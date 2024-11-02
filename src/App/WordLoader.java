package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";

    public void loadWords(List<Word> wordList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 라인을 ':' 기준으로 5개로 분리 (영어 단어, 음절로 구분된 단어, 발음, 강세 위치, 의미)
                String[] parts = line.split(":", 5);

                // 데이터가 부족할 경우 오류 처리
                if (parts.length < 5) {
                    System.out.println("데이터 형식 오류: 필수 정보가 부족합니다. (" + line + ")");
                    continue;
                }

                String englishWord = parts[0].trim();
                String syllableSeparated = parts[1].trim();
                String pronunciation = parts[2].trim();

                int accentPosition;
                try {
                    accentPosition = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("데이터 형식 오류: 강세 위치는 숫자로 입력되어야 합니다. (" + line + ")");
                    continue;
                }

                // 품사와 의미 파트에서 괄호 제거
                String meaningPart = parts[4].replaceAll("\\(", "").replaceAll("\\)", "");
                String[] posAndMeanings = meaningPart.split(",");

                Map<String, String> meanings = new HashMap<>();
                for (String posMeaning : posAndMeanings) {
                    String[] posMean = posMeaning.split(":");
                    if (posMean.length == 2) {
                        meanings.put(posMean[0].trim(), posMean[1].trim());
                    }
                }

                int syllableCount = syllableSeparated.split("·").length;

                Word word = new Word(englishWord, syllableSeparated, pronunciation, accentPosition, syllableCount, meanings);
                wordList.add(word);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
