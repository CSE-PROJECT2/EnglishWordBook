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

        String english;
        while (true) {
            System.out.print("영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            if (!validator.noTabOrNewLine(english)) {
                System.out.println("오류: 영단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            if (!validator.noLeadingOrTrailingSpaces(english)) {
                System.out.println("오류: 영단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }

            if (!validator.isValidEnglishWord(english)) {
                System.out.println("오류: 영단어는 영어 알파벳으로만 구성되어야 합니다. 다시 입력해주세요.");
                continue;
            }

            if (validator.isDuplicateWord(wordList, english)) {
                System.out.println("오류: 이미 단어장에 존재하는 단어입니다. 다른 단어를 입력해주세요.");
                continue;
            }

            break;
        }

        String syllableSeparated;
        while (true) {
            System.out.print("음절 구분된 단어를 입력하세요 (예: ap·ple) >> ");
            syllableSeparated = scanner.nextLine();

            if (!validator.isValidSyllableFormat(english, syllableSeparated)) {
                System.out.println("오류: 잘못된 입력 형식입니다.");
                continue;
            }

            break;
        }

        String pronunciation;
        while (true) {
            System.out.print("발음을 입력하세요 (예: 애플) >> ");
            pronunciation = scanner.nextLine();

            if (validator.isMeaningInEnglish(pronunciation)) {
                System.out.println("오류: 한글로만 입력해주세요.");
                continue;
            }

            break;
        }

        int accentPosition;
        while (true) {
            System.out.print("강세 위치를 입력하세요 (음절 번호) >> ");
            try {
                accentPosition = Integer.parseInt(scanner.nextLine());
                if (!validator.isValidAccentPosition(syllableSeparated, accentPosition)) {
                    System.out.println("오류: 강세 위치는 음절의 범위 내에서 선택해야 합니다.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("오류: 강세 위치는 숫자로 입력되어야 합니다.");
            }
        }

        Map<String, String> meanings = new HashMap<>();
        while (true) {
            String pos;
            while (true) {
                System.out.print("품사를 입력하세요 (예: 명사, 동사 등) >> ");
                pos = scanner.nextLine();

                if (!validator.isAllowedPos(pos)) {
                    System.out.println("오류: 품사는 ‘동사’, ‘명사’, ‘형용사’, ‘부사’, ‘전치사’, ‘접속사’, ‘대명사’, ‘감탄사’ 중 하나로 입력해주세요.");
                } else {
                    break;
                }
            }

            String meaning;
            while (true) {
                System.out.print("뜻을 입력하세요 (한글로) >> ");
                meaning = scanner.nextLine();

                if (!validator.isValidMeaning(meaning)) {
                    System.out.println("오류: 뜻은 한글로만 구성되어야 합니다. 다시 입력해주세요.");
                } else {
                    // 중복된 품사와 뜻 검사
                    if (meanings.containsKey(pos) && meanings.get(pos).equals(meaning)) {
                        System.out.println("오류: 이미 저장된 단어입니다.");
                        continue; // 품사 입력부터 다시 받도록
                    }
                    break;
                }
            }

            meanings.put(pos, meaning);

            // 추가 품사 입력 여부 확인
            String choice;
            while (true) {
                System.out.println("다른 품사를 추가하시겠습니까? (1) 예, (2) 아니오 >> ");
                System.out.print("메뉴를 선택하세요 ");
                choice = scanner.nextLine();

                if (choice.equals("1")) {
                    break;
                } else if (choice.equals("2")) {
                    break;
                } else {
                    System.out.println("오류: 숫자 1 또는 2를 입력해주세요.");
                }
            }

            if (choice.equals("2")) {
                break;
            }
        }

        // 최종 추가 확인
        while (true) {
            System.out.printf("‘%s : %s ’의 단어를 추가하시겠습니까?\n", english, meanings);
            System.out.println("(1) 예");
            System.out.println("(2) 아니오");
            System.out.print("메뉴를 선택하세요 >> ");
            String confirmation = scanner.nextLine();

            if (confirmation.equals("1")) {
                int syllableCount = syllableSeparated.split("·").length; // 음절 수 계산
                Word newWord = new Word(english, syllableSeparated, pronunciation, accentPosition, syllableCount, meanings);
                wordList.add(newWord);
                System.out.println("단어가 저장되었습니다.");
                break;
            } else if (confirmation.equals("2")) {
                System.out.println("단어 저장이 취소되었습니다.");
                break;
            } else {
                System.out.println("오류: 숫자 1 또는 2를 입력해주세요.");
            }
        }
    }
}
