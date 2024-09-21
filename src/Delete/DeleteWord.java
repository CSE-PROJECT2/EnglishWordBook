package Delete;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class DeleteWord {

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 단어를 입력하세요: ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getEnglish().equalsIgnoreCase(searchWord)) {
                wordList.remove(i);
                found = true;
                System.out.println("단어가 삭제되었습니다.");
                break;
            }
        }

        if (!found) {
            System.out.println("해당 단어가 존재하지 않습니다.");
        }
    }
}
