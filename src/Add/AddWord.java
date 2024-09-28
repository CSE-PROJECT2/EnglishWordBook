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

        Word newWord = new Word(english, meaning);
        wordList.add(newWord);
        System.out.println("단어가 저장되었습니다.\n");
    }
}
