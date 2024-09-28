package Add;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class AddWord {
    private static final String ENGLISH_PATTERN = "^[a-zA-Z]+$";

    public void run(List<Word> wordList) {
    	System.out.println("\n*** 단어 추가 ***");
        Scanner scanner = new Scanner(System.in);


        String english;
        while (true) {
            System.out.print("영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            // 입력한 영단어가 영어로만 구성되어 있는지 확인
            if (!english.matches(ENGLISH_PATTERN)) {
                System.out.println("오류: 영단어는 영어 알파벳만 입력 가능합니다. 다시 입력해주세요.");
            } else {
                break;  // 올바른 입력이면 반복문 종료
            }
        }

        System.out.print("뜻을 입력하세요 (한글로) >> ");
        String meaning = scanner.nextLine();

        // 확인 절차: 입력한 영단어와 뜻을 저장할 것인지 확인
        System.out.printf("'%s : %s'의 단어를 추가하시겠습니까?\n", english, meaning);
        System.out.println("(1) 예");
        System.out.println("(2) 아니오");
        System.out.print("메뉴를 선택하세요 >> ");
        String choice = scanner.nextLine();

        // 선택에 따라 단어 저장 여부 결정
        if (choice.equals("1")) {
            Word newWord = new Word(english, meaning);
            wordList.add(newWord);
            System.out.println("단어가 저장되었습니다.\n");
        } else {
            System.out.println("단어 저장이 취소되었습니다.\n");
        }

    }
}
