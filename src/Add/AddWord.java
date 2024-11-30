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

        // 영단어 입력
        String english;
        while (true) {
            System.out.print("영단어를 입력하세요 >> ");
            english = scanner.nextLine();

            if (!validator.hasValidLength(english)) {
                System.out.println("오류: 단어의 길이는 1자 이상이어야 합니다. 다시 입력해주세요.");
                continue;
            }
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

        // 품사와 뜻 입력
        Map<String, Word.PartOfSpeech> partsOfSpeech = new HashMap<>();
        while (true) {
            // 품사 입력
            String pos;
            while (true) {
                System.out.print("품사를 입력하세요 (예: 명사, 동사, 형용사) >> ");
                pos = scanner.nextLine();
                if (!validator.isAllowedPos(pos)) {
                    System.out.println("오류: 품사는 ‘동사’,‘명사’,‘형용사’ 중 하나로 입력해주세요.");
                } else {
                    break;
                }
            }

            // 뜻 입력
            String meaning;
            while (true) {
                System.out.print("뜻을 입력하세요 (한글로) >> ");
                meaning = scanner.nextLine();
                if (!validator.isValidMeaning(meaning)) {
                    System.out.println("오류: 잘못된 뜻 입력 형식입니다.");
                } else {
                    break;
                }
            }

            // 발음 입력
            String pronunciationText;
            while (true) {
                System.out.print("뜻의 발음을 입력하세요 (예: 애플) >> ");
                pronunciationText = scanner.nextLine();
                if (!validator.isValidPronunciation(pronunciationText)) {
                    System.out.println("오류: 한글로만 입력해주세요.");
                } else {
                    break;
                }
            }

            //발음기호 입력
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

            // 1차 강세 입력
            int primaryStress;
            while (true) {
                System.out.print("1차 강세 위치를 입력하세요 (음절 번호) >> ");
                try {
                    primaryStress = Integer.parseInt(scanner.nextLine());
                    if (!validator.isValidAccentPosition(syllableSeparated, primaryStress)) {
                        System.out.println("오류: 강세 위치는 음절의 범위 내에서 선택해야 합니다.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("오류: 강세 위치는 숫자로 입력되어야 합니다.");
                }
            }

            // 2차 강세 입력
            int secondaryStress;
            while (true) {
                System.out.print("2차 강세 위치를 입력하세요 (없거나 모르면 0 입력) >> ");
                try {
                    secondaryStress = Integer.parseInt(scanner.nextLine());
                    if (secondaryStress == 0 || validator.isValidSecondaryAccentPosition(syllableSeparated, secondaryStress)) {
                        break;
                    } else {
                        System.out.println("오류: 2차 강세 위치는 음절의 범위 내에서 선택하거나 0이어야 합니다.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("오류: 2차 강세 위치는 숫자로 입력되어야 합니다.");
                }
            }

            // 추가 정보 입력
            Word.PartOfSpeech partOfSpeech;
            switch (pos) {
                case "동사":
                    String present = getOptionalInput(scanner, "현재형");
                    String past = getOptionalInput(scanner, "과거형");
                    String pastParticiple = getOptionalInput(scanner, "과거분사");
                    partOfSpeech = new Word.Verb(meaning, syllableSeparated, primaryStress, secondaryStress,
                            pronunciationText, present, past, pastParticiple);
                    break;

                case "명사":
                    String singular = getOptionalInput(scanner, "단수형");
                    String plural = getOptionalInput(scanner, "복수형");
                    partOfSpeech = new Word.Noun(meaning, syllableSeparated, primaryStress, secondaryStress,
                            pronunciationText, singular, plural);
                    break;

                case "형용사":
                    String baseForm = getOptionalInput(scanner, "원형");
                    String comparative = getOptionalInput(scanner, "비교급");
                    String superlative = getOptionalInput(scanner, "최상급");
                    partOfSpeech = new Word.Adjective(meaning, syllableSeparated, primaryStress, secondaryStress,
                            pronunciationText, baseForm, comparative, superlative);
                    break;

                default:
                    throw new IllegalArgumentException("지원되지 않는 품사: " + pos);
            }

            partsOfSpeech.put(pos, partOfSpeech);

            // 추가 품사 입력 여부 확인
            System.out.print("다른 품사를 추가하시겠습니까? (1: 예, 2: 아니오) >> ");
            String choice = scanner.nextLine();
            if ("2".equals(choice)) {
                break;
            }
        }

        // 최종 추가 확인
        while (true) {
            System.out.printf("‘%s의 단어를 추가하시겠습니까?\n", english);
            System.out.println("(1) 예");
            System.out.println("(2) 아니오");
            System.out.print("메뉴를 선택하세요 >> ");
            String confirmation = scanner.nextLine();

            if ("1".equals(confirmation)) {
                Word newWord = new Word(english);
                partsOfSpeech.forEach(newWord::addPartOfSpeech);
                wordList.add(newWord);
                System.out.println("단어가 저장되었습니다.");
                break;
            } else if ("2".equals(confirmation)) {
                System.out.println("단어 저장이 취소되었습니다.");
                break;
            } else {
                System.out.println("오류: 숫자 1 또는 2를 입력해주세요.");
            }
        }
    }

    // 입력을 받을 때 엔터로 넘어가면 기본값("미입력")을 저장하는 메서드
    private String getOptionalInput(Scanner scanner, String prompt) {
        System.out.printf("%s (미입력을 원하면 Enter) >> ", prompt);
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            return "미입력";
        }
        if (!validator.isValidEnglishWord(input)) {
            System.out.printf("오류: %s은 올바른 영어 단어 형식이어야 합니다.\n", prompt);
            return getOptionalInput(scanner, prompt); // 재귀 호출로 재입력 요청
        }
        return input;
    }
}
