package Add;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class AddWord {

    public WordValidator validator = new WordValidator();


    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 추가 ***");
        Scanner scanner = new Scanner(System.in);

        String english;
        while (true) {
            System.out.print("영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            // 영어 단어 형식 검증 ( 4.1.1 -> 영단어로만 구성되고 탭과 개행이 들어가지 않고 단어의 처음과 끝은 공백문자 x)


            // 2. 단어의 길이가 1 이상인지 확인
            if (!validator.hasValidLength(english)) {
                System.out.println("오류: 영단어의 길이는 1자 이상이어야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 3. 탭이나 개행 문자가 포함되지 않았는지 확인
            if (!validator.noTabOrNewLine(english)) {
                System.out.println("오류: 영단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            // 4. 단어의 처음과 끝에 공백 문자가 포함되지 않았는지 확인
            if (!validator.noLeadingOrTrailingSpaces(english)) {
                System.out.println("오류: 영단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }
            // 1. 영어로만 구성되어 있는지 확인
            if (!validator.isEnglishOnly(english)) {
                System.out.println("오류: 영단어는 영어 알파벳으로만 구성되어야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 중복 단어 검증
            if (validator.isDuplicateWord(wordList, english)) {
                System.out.println("오류: 이미 단어장에 존재하는 단어입니다. 다른 단어를 입력해주세요.");
                continue;
            }

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }


        String meaning;
        while (true) {
            System.out.print("뜻을 입력하세요  >> ");
            meaning = scanner.nextLine();

            // 의미의 유효성 검증 (영어 또는 한글로만 이루어졌는지 확인)
            if (!validator.isValidMeaning(meaning)) {
                System.out.println("오류: 뜻은 반드시 1자 이상의 영어 또는 한글로 입력해야 합니다. 공백이나 특수문자는 입력할 수 없습니다.");
                continue;
            }

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }

        // 단어와 뜻을 확인 후 추가 여부를 묻는 절차
        while (true) {
            try {
                System.out.printf("'%s : %s'의 단어를 추가하시겠습니까?\n", english, meaning);
                System.out.println("(1) 예");
                System.out.println("(2) 아니오");
                System.out.print("메뉴를 선택하세요 >> ");
                String choice = scanner.nextLine();

                // 숫자 입력인지 확인
                int menuChoice = Integer.parseInt(choice); // 입력값을 숫자로 변환 시도

                if (menuChoice == 1) {
                    Word newWord = new Word(english, meaning);
                    wordList.add(newWord);
                    System.out.println("단어가 저장되었습니다.\n");
                    break;
                } else if (menuChoice == 2) {
                    System.out.println("단어 저장이 취소되었습니다.\n");
                    break;
                } else {
                    System.out.println("숫자 1 또는 2를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("오류: 숫자만 입력 가능합니다. 다시 입력해주세요.");
            }
        }
    }
}
