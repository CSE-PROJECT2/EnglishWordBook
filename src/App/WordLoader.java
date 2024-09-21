package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";

    // 파일에서 단어 목록을 로드하는 기능
    public void loadWords(List<Word> wordList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    wordList.add(new Word(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다.");
        }
    }
}
