package Update;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class UpdateWord {

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 단어를 입력하세요: ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        for (Word word : wordList) {
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
                System.out.print("새로운 의미를 입력하세요 (한글로): ");
                String newMeaning = scanner.nextLine();
                word.setMeaning(newMeaning);
                found = true;
                System.out.println("단어가 수정되었습니다.");
                break;
            }
        }

        if (!found) {
            System.out.println("해당 단어가 존재하지 않습니다.");
        }
    }
}
