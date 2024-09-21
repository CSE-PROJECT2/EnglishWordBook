package View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ViewWords {

    private static final String FILE_PATH = "src/WordBook.txt";

    public void run() {
        ArrayList<String> wordsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordsList.add(line);
            }

            if (wordsList.isEmpty()) {
                System.out.println("저장된 단어가 없습니다.");
            } else {
                Collections.sort(wordsList);  // 알파벳 순 정렬
                System.out.println("저장된 단어 목록:");
                for (String word : wordsList) {
                    System.out.println(word);
                }
            }

        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다.");
        }
    }
}
