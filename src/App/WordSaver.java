package App;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WordSaver {
    private static final String FILE_PATH = "src/WordBook.txt";

    // 단어 목록을 파일에 저장하는 기능
    public void saveWords(List<Word> wordList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Word word : wordList) {
                writer.write(word.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다.");
        }
    }
}
