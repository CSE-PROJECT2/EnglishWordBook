package Add;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class AddWord {

    private WordValidator validator = new WordValidator();


    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 추가 ***");
        Scanner scanner = new Scanner(System.in);

        String english;
        while (true) {
            System.out.print("영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            // 영어 단어 형식 검증
            if (!validator.isValidEnglishWord(english)) {
                System.out.println("오류: 영단어는 영어 알파벳만 입력 가능합니다. 다시 입력해주세요.");
                continue;
            }

            // 중복 단어 검증
            if (validator.isDuplicateWord(wordList, english)) {
                System.out.println("오류: 이미 단어장에 존재하는 단어입니다. 다른 단어를 입력해주세요.");
                continue;
            }

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }

        System.out.print("뜻을 입력하세요 (한글로) >> ");
        String meaning = scanner.nextLine();

        // 입력한 단어와 뜻을 확인 후 추가
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
