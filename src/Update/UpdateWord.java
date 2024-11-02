package Update;

import App.Word;
import java.util.List;
import java.util.Scanner;

public class UpdateWord {

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 수정 ***");
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 영단어를 입력하세요 >> ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        for (Word word : wordList) {
            // 영어 단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(searchWord)) {  // getEnglishWord() 사용
                found = true;
                System.out.print("새로운 뜻을 입력하세요 (한글로) >> ");
                String newMeaning = scanner.nextLine();

                while (true) {
                    System.out.println("\n'" + word.getEnglishWord() + "'의 의미를 '" + newMeaning + "'(으)로 수정하시겠습니까?");
                    System.out.println("(1) 예");
                    System.out.println("(2) 아니오");
                    System.out.print("메뉴를 선택하세요 >> ");
                    int confirmation = scanner.nextInt();
                    scanner.nextLine(); // 개행 문자 처리

                    if (confirmation == 1) {
                        word.getMeanings().put("명사", newMeaning);  // 단어의 뜻을 업데이트 (예: 명사로 설정)
                        System.out.println("수정되었습니다.\n");
                        break;
                    } else if (confirmation == 2) {
                        System.out.println("수정이 취소되었습니다.\n");
                        break;
                    } else {
                        System.out.println("숫자 1 또는 2를 입력해주세요.");
                    }
                }
                break;
            }
        }

        if (!found) {
            System.out.println("해당 영단어가 존재하지 않습니다.");
        }
    }
}
