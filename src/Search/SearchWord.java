package Search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SearchWord {

    private static final String FILE_PATH = "src/WordBook.txt";

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 단어를 입력하세요: ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(searchWord + " :")) {
                    System.out.println("찾은 단어: " + line);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("해당 단어가 존재하지 않습니다.");
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다.");
        }
    }
}
