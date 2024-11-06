package App;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WordSaver {
    private static final String FILE_PATH = "src/WordBook.txt";

    public void saveWords(List<Word> wordList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Word word : wordList) {
                StringBuilder sb = new StringBuilder();
                sb.append(word.getEnglishWord()).append(":") // 영어 단어
                        .append(word.getSyllableSeparated()).append(":") // 발음 구분된 단어
                        .append(word.getPronunciation()).append(":") // 발음
                        .append(word.getAccentPosition()).append(":"); // 강세 위치

                for (Map.Entry<String, String> entry : word.getMeanings().entrySet()) {
                    sb.append("(").append(entry.getKey()).append(":").append(entry.getValue()).append("), ");
                }

                // 마지막 ", " 제거
                sb.delete(sb.length() - 2, sb.length());
                sb.append("\n");

                writer.write(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다:\n"
                    + "프로그램을 종료합니다.");
            System.exit(0);

        }
    }
}
