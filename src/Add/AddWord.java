package Add;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddWord {
    private static final String FILE_PATH = "Library05-word-data.txt";

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("단어를 입력하세요: ");
        String word = scanner.nextLine();
        System.out.print("의미를 입력하세요: ");
        String meaning = scanner.nextLine();

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(word + " : " + meaning + "\n");
            System.out.println("단어가 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다.");
        }
    }
}
