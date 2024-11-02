package Add;

import App.Word;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddWord {

    public WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 추가 ***");
        Scanner scanner = new Scanner(System.in);

        String englishWord;
        while (true) {
            System.out.print("영어 단어를 입력하세요 >> ");
            englishWord = scanner.nextLine();

            // 영어 단어 형식 검증
            if (!validator.hasValidLength(englishWord)) {
                System.out.println("오류: 단어의 길이는 1자 이상이어야 합니다. 다시 입력해주세요.");
                continue;
            }

            if (!validator.noTabOrNewLine(englishWord)) {
                System.out.println("오류: 단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            if (!validator.noLeadingOrTrailingSpaces(englishWord)) {
                System.out.println("오류: 단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }

            if (validator.isDuplicateWord(wordList, englishWord)) {
                System.out.println("오류: 이미 단어장에 존재하는 단어입니다. 다른 단어를 입력해주세요.");
                continue;
            }

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }

        System.out.print("음절 구분된 단어를 입력하세요 (예: ap·ple) >> ");
        String syllableSeparated = scanner.nextLine();

        System.out.print("발음을 입력하세요 (예: 애플) >> ");
        String pronunciation = scanner.nextLine();

        int accentPosition;
        while (true) {
            try {
                System.out.print("강세 위치를 입력하세요 (음절 번호) >> ");
                accentPosition = Integer.parseInt(scanner.nextLine());
                if (accentPosition > 0 && accentPosition <= syllableSeparated.split("·").length) {
                    break; // 유효한 강세 위치 입력
                } else {
                    System.out.println("오류: 강세 위치는 음절의 범위 내에서 선택해야 합니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("오류: 숫자만 입력 가능합니다. 다시 입력해주세요.");
            }
        }

        Map<String, String> meanings = new HashMap<>();
        while (true) {
            System.out.print("품사를 입력하세요 (예: 명사, 동사 등) >> ");
            String partOfSpeech = scanner.nextLine();

            System.out.print("뜻을 입력하세요 >> ");
            String meaning = scanner.nextLine();

            meanings.put(partOfSpeech, meaning);

            System.out.print("다른 품사를 추가하시겠습니까? (1) 예, (2) 아니오 >> ");
            String addMore = scanner.nextLine();
            if (!addMore.equals("1")) {
                break; // 더 이상 의미를 추가하지 않음
            }
        }

        // 단어와 뜻을 확인 후 추가 여부를 묻는 절차
        while (true) {
            try {
                System.out.printf("'%s : %s'의 단어를 추가하시겠습니까?\n", englishWord, meanings);
                System.out.println("(1) 예");
                System.out.println("(2) 아니오");
                System.out.print("메뉴를 선택하세요 >> ");
                String choice = scanner.nextLine();

                int menuChoice = Integer.parseInt(choice);

                if (menuChoice == 1) {
                    int syllableCount = syllableSeparated.split("·").length;
                    Word newWord = new Word(englishWord, syllableSeparated, pronunciation, accentPosition, syllableCount, meanings);
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
