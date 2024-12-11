package Delete;

import App.Word;
import Add.WordValidator;
import java.util.List;
import java.util.Scanner;

public class DeleteWord {

    private WordValidator validator = new WordValidator();  // WordValidator 인스턴스 생성

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        String english;

        // 유효성 검사 및 오류 메시지
        while (true) {
            System.out.print("삭제할 영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            // 1. 탭이나 개행 문자가 포함되어 있는지 확인
            if (!validator.noTabOrNewLine(english)) {
                System.out.println("오류: 영단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            // 2. 단어의 처음과 끝에 공백이 없는지 확인
            if (!validator.noLeadingOrTrailingSpaces(english)) {
                System.out.println("오류: 영단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 3. 영어 단어 형식이 유효한지 확인
            if (!validator.isValidEnglishWord(english)) {
                System.out.println("오류: 영단어는 영어 알파벳으로만 구성되어야 합니다. 다시 입력해주세요.");
                continue;
            }

            break;  // 유효성 검사를 통과한 경우 반복문 종료
        }

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
            while (true) {
                System.out.printf("‘%s' 을/를 단어장에서 삭제하시겠습니까?\n", wordToDelete.getEnglishWord());
                System.out.println("(1) 예");
                System.out.println("(2) 아니오");
                System.out.print("메뉴를 선택하세요 >> ");

                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    wordList.remove(wordToDelete);
                    System.out.println("단어가 삭제되었습니다.\n");
                    break;
                } else if (choice.equals("2")) {
                    System.out.println("삭제가 취소되었습니다.\n");
                    break;
                } else {
                    System.out.println("숫자 1 또는 2를 입력해주세요.\n");
                }
            }
        } else {
            System.out.println("단어장에 '" + english + "' 단어가 존재하지 않습니다.\n");
        }
    }
}
