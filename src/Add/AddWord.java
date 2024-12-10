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
            if (!validator.isValidEnglishWord(english) || english.contains(" ")) {
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
                System.out.print("품사를 입력하세요 (예: 명사, 동사 등) >> ");
                pos = scanner.nextLine();

                if (!validator.isAllowedPos(pos)) {
                    System.out.println("오류: 품사는 ‘동사’, ‘명사’, ‘형용사’, ‘부사’, ‘전치사’, ‘접속사’, ‘대명사’, ‘감탄사’ 중 하나로 입력해주세요.");
                } else {
                    break;
                }
            }

            // 뜻 입력
            String meaning;
            while (true) {
                System.out.print("뜻을 입력하세요 (한글로) >> ");
                meaning = scanner.nextLine();

                if (!validator.isValidMeaning(meaning)||meaning.contains("\t")) {
                    System.out.println("오류: 잘못된 뜻 입력 형식입니다.");
                } else if (isDuplicatePartOfSpeech(partsOfSpeech, pos, meaning)) {
                    System.out.println("오류: 같은 품사와 같은 뜻이 이미 존재합니다. 다시 입력해주세요.");
                } else {
                    break;
                }
            }

            // 발음 입력
            String pronunciationText;
            while (true) {
                System.out.print("발음을 입력하세요 (예: 애플) >> ");
                pronunciationText = scanner.nextLine();
                if (!validator.isValidPronunciation(pronunciationText)) {
                    System.out.println("오류: 한글로만 입력해주세요.");
                } else {
                    break;
                }
            }

            // 음절구분된 단어 입력
            String syllableSeparated;
            while (true) {
                System.out.print("음절 구분된 단어를 입력하세요 (예: ap.ple) >> ");
                syllableSeparated = scanner.nextLine();

                // 입력된 "."을 "·"로 변환
                String formattedSyllableSeparated = syllableSeparated.replace(".", "·");

                if (!validator.isValidSyllableFormat(english, formattedSyllableSeparated)) {
                    System.out.println("오류: 잘못된 입력 형식입니다.");
                    continue;
                }

                // 입력이 유효한 경우 변환된 값을 사용
                syllableSeparated = formattedSyllableSeparated;
                break;
            }

            // 1차 및 2차 강세 입력 처리
            String primaryStress;
            String secondaryStress = "-";

            if (syllableSeparated.split("·").length == 1) {
                // 음절 수가 1개인 경우
                primaryStress = "1";
                secondaryStress = "-";
                System.out.println("음절이 1개인 단어입니다. 1차 강세는 자동으로 '1', 2차 강세는 자동으로 '-'로 설정됩니다.");
            } else {
                // 1차 강세 입력
                while (true) {
                    System.out.print("1차 강세 위치를 입력하세요 (없으면 x, 모르면 ?) >> ");
                    primaryStress = scanner.nextLine().trim();

                    if (primaryStress.equals("x") || primaryStress.equals("?")) {
                        break;
                    }

                    if (primaryStress.matches("^\\d+$") && !primaryStress.equals("0")) {
                        try {
                            int stressPosition = Integer.parseInt(primaryStress);
                            if (validator.isValidSecondaryAccentPosition(syllableSeparated, stressPosition)) {
                                break;
                            } else {
                                System.out.println("오류: 1차 강세 위치는 음절의 범위 내에서 선택해야 합니다.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("오류: 1차 강세 위치는 유효한 숫자여야 합니다.");
                        }
                    } else {
                        System.out.println("오류: 입력은 숫자만 포함해야 하며, 숫자 앞뒤에 공백이나 탭이 포함될 수 없고 0이 올 수 없습니다. 다시 입력하세요.");
                    }
                }

                // 2차 강세 입력
                if (syllableSeparated.split("·").length == 2) {
                } else if (primaryStress.equals("?")) {
                    secondaryStress = "?";
                } else if (primaryStress.equals("x")) {
                    secondaryStress = "x";
                } else {
                    while (true) {
                        secondaryStress = scanner.nextLine();

                        // x 또는 ?인 경우 처리
                        if (secondaryStress.equals("x") || secondaryStress.equals("?")) {
                            break;
                        }

                        // 숫자 앞뒤에 공백/탭이 포함된 경우를 방지
                        if (secondaryStress.matches("^\\d+$") && !secondaryStress.equals("0")) {
                            try {
                                int stressPosition = Integer.parseInt(secondaryStress);
                                if (primaryStress.equals(secondaryStress)) {
                                    System.out.println("오류: 2차 강세는 1차 강세와 같은 위치일 수 없습니다.");
                                } else if (validator.isValidSecondaryAccentPosition(syllableSeparated, stressPosition)) {
                                    break;
                                } else {
                                    System.out.println("오류: 2차 강세 위치는 음절 범위 내의 숫자여야 합니다.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("오류: 2차 강세 위치는 유효한 숫자여야 합니다.");
                            }
                        } else {
                            System.out.println("오류: 입력은 숫자만 포함해야 하며, 숫자 앞뒤에 공백이나 탭이 포함될 수 없고 0이 올 수 없습니다. 다시 입력하세요.");
                        }
                    }
                }
            }


            // 추가 정보 입력
            Word.PartOfSpeech partOfSpeech;
            switch (pos) {
                // 추가된 품사( 추가 정보 없음)
                case "부사":
                case "전치사":
                case "접속사":
                case "대명사":
                case "감탄사":
                    partOfSpeech = new Word.PartOfSpeech(meaning, syllableSeparated, primaryStress, secondaryStress, pronunciationText) {
                    };
                    break;

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
            String choice;
            while (true) {
                System.out.print("다른 품사를 추가하시겠습니까? (1) 예, (2) 아니오 >> ");
                choice = scanner.nextLine();
                if ("1".equals(choice)) {
                    // 추가 품사 입력 로직 실행
                    break;
                } else if ("2".equals(choice)) {
                    break;
                } else {
                    System.out.println("오류 : 숫자 1 또는 2를 입력해주세요.");
                }
            }
            if ("2".equals(choice)) {
                break;
            }
        }

        // 최종 추가 확인
        while (true) {
            System.out.printf("‘%s’의 단어를 추가하시겠습니까?\n", english);
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

    // 중복 품사와 뜻 확인
    private boolean isDuplicatePartOfSpeech(Map<String, Word.PartOfSpeech> partsOfSpeech, String pos, String meaning) {
        if (partsOfSpeech.containsKey(pos)) {
            Word.PartOfSpeech existingPart = partsOfSpeech.get(pos);
            return existingPart.getMeaning().equals(meaning);
        }
        return false;
    }

    // 입력을 받을 때 엔터로 넘어가면 기본값("미입력")을 저장하는 메서드
    private String getOptionalInput(Scanner scanner, String prompt) {
        System.out.printf("%s (미입력을 원하면 Enter) >> ", prompt);
        String input = scanner.nextLine();


        // 입력값이 비어 있는 경우
        if (input.isEmpty()) {
            return "미입력";
        }

        // 입력값이 유효한 영어 단어인지 검사
        if (!validator.isValidEnglishWord(input)||input.contains("\t")) {
            System.out.printf("오류: %s은 올바른 영어 단어 형식이어야 합니다.\n", prompt);
            return getOptionalInput(scanner, prompt); // 재귀 호출로 재입력 요청
        }

        return input;
    }

}
