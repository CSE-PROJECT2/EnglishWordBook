package Delete;

import App.Word;
import java.util.List;
import java.util.Scanner;

public class DeleteWord {

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 영어단어를 입력하세요 >> ");
        String english = scanner.nextLine();

        Word wordToDelete = null;
        for (Word word : wordList) {
            // 영어 단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(english)) {
                wordToDelete = word;
                break;
            }
        }

        if (wordToDelete != null) {
            // 삭제 확인 절차 추가
            System.out.printf("‘%s : %s’을/를 단어장에서 삭제하시겠습니까?\n", wordToDelete.getEnglishWord(), wordToDelete.getMeanings().values().iterator().next());
            System.out.println("(1) 예");
            System.out.println("(2) 아니오");
            System.out.print("메뉴를 선택하세요 >> ");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                wordList.remove(wordToDelete);
                System.out.println("'" + english + "' 단어가 삭제되었습니다.\n");
            } else if (choice.equals("2")) {
                System.out.println("삭제가 취소되었습니다.\n");
            } else {
                System.out.println("오류: 숫자 1 또는 2를 입력해주세요.");
            }
        } else {
            System.out.println("단어장에 '" + english + "' 단어가 존재하지 않습니다.\n");
        }
    }
}
