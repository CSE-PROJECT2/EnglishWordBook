package Delete;

import java.io.*;
import java.util.Scanner;

public class DeleteWord {

    private static final String FILE_PATH = "src/WordBook.txt";
    private static final String TEMP_FILE = "src/TempWordBook.txt";

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 단어를 입력하세요: ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             FileWriter writer = new FileWriter(TEMP_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(searchWord + " :")) {
                    found = true;
                    System.out.println("단어가 삭제되었습니다.");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("해당 단어가 존재하지 않습니다.");
            }

        } catch (IOException e) {
            System.out.println("파일 처리 중 오류가 발생했습니다.");
        }

        // 파일 교체
        new File(FILE_PATH).delete();
        new File(TEMP_FILE).renameTo(new File(FILE_PATH));
    }
}
