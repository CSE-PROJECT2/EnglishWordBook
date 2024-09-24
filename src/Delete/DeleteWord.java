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
            	found = true;
            	
            	while(true) {
            	System.out.println("\n'" + wordList.get(i).getEnglish() + "'을/를 단어장에서 삭제하시겠습니까?");
                System.out.println("(1) 예");
                System.out.println("(2) 아니오");
                System.out.print("메뉴를 선택하시오 : ");
                int confirmation = scanner.nextInt();
                if (confirmation == 1) {
                    wordList.remove(i);
                    System.out.println("단어가 삭제되었습니다.\n");
                    break;
                } else if (confirmation == 2) {
                    System.out.println("삭제가 취소되었습니다.\n");
                    break;
                } else System.out.println("숫자 1 또는 2를 입력해주세요.");
            	}
                break;
            }
        }

        if (!found) {
            System.out.println("해당 단어가 존재하지 않습니다.\n");
        }
    }
}
