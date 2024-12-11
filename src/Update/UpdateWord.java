package Update;

import Add.WordValidator;
import App.Word;
import App.Word.PartOfSpeech;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UpdateWord {

    private WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 수정 ***");
        Scanner scanner = new Scanner(System.in);

        String searchWord;
        while (true) {
            System.out.print("수정할 영단어를 입력하세요 >> ");
            searchWord = scanner.nextLine();

            if (!validator.hasValidLength(searchWord)) {
                System.out.println("오류: 단어의 길이는 1자 이상이어야 합니다. 다시 입력해주세요.");
                continue;
            }
            if (!validator.noTabOrNewLine(searchWord)) {
                System.out.println("오류: 영단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }
            if (!validator.noLeadingOrTrailingSpaces(searchWord)) {
                System.out.println("오류: 영단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }
            if (!validator.isValidEnglishWord(searchWord)) {
                System.out.println("오류: 영단어는 영어 알파벳으로만 구성되어야 합니다. 다시 입력해주세요.");
                continue;
            }
            break;
        }

        Word wordToUpdate = null;
        for (Word word : wordList) {
            if (word.getEnglishWord().equalsIgnoreCase(searchWord)) {
                wordToUpdate = word;
                break;
            }
        }


        if (wordToUpdate == null) {
            System.out.println("해당 영단어가 존재하지 않습니다.\n");
            return;
        }

        Map<String, PartOfSpeech> partsOfSpeech = wordToUpdate.getPartsOfSpeech();
        if (partsOfSpeech.isEmpty()) {
            System.out.println("해당 단어에 등록된 품사가 없습니다.\n");
            return;
        }

        // 검색 결과 출력
        int partCount = partsOfSpeech.size();
        System.out.println("\n< " + wordToUpdate.getEnglishWord() + " >\n");
        int index = 1;
        for (Map.Entry<String, PartOfSpeech> entry : partsOfSpeech.entrySet()) {
            PartOfSpeech partOfSpeech = entry.getValue();
            String syllableSeparated = partOfSpeech.getPronunciation();
            int syllableCount = syllableSeparated.split("·").length; // 음절 수 계산
            if (partCount > 1) {
                System.out.println(index + ".");
            }
            printPartOfSpeech(entry.getKey(), partOfSpeech, syllableCount);


            index++;
        }
        String selectedPos;
        PartOfSpeech selectedPart;
        if (partCount == 1) {
            // 검색된 뜻이 하나일 경우, 바로 첫 번째 뜻을 선택
            selectedPos = partsOfSpeech.keySet().iterator().next();
            selectedPart = partsOfSpeech.get(selectedPos);
        } else {
            // 여러 뜻이 있는 경우, 사용자 입력 받아 선택
            System.out.print("수정할 뜻의 번호를 선택하세요 >> ");
            int selectedIndex;
            try {
                selectedIndex = Integer.parseInt(scanner.nextLine());
                if (selectedIndex < 1 || selectedIndex > partsOfSpeech.size()) {
                    System.out.println("잘못된 번호입니다. 수정이 취소되었습니다.\n");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요. 수정이 취소되었습니다.\n");
                return;
            }
            selectedPos = (String) partsOfSpeech.keySet().toArray()[selectedIndex - 1];
            selectedPart = partsOfSpeech.get(selectedPos);
        }

        // 새로운 품사 입력
        System.out.print("새로운 품사를 입력하세요 (예: 명사, 동사 등) >> ");
        String newPos;
        while (true) {
            newPos = scanner.nextLine();
            if (!validator.isAllowedPos(newPos)) {
                System.out.println("오류: 품사는 ‘동사’, ‘명사’, ‘형용사’, ‘부사’, ‘전치사’, ‘접속사’, ‘대명사’, ‘감탄사’ 중 하나로 입력해주세요.");
                System.out.print("새로운 품사를 입력하세요 >> ");
            } else {
                break;
            }
        }

// 뜻 입력
        String newMeaning;
        while (true) {
            System.out.print("뜻을 입력하세요 (한글로) >> ");
            newMeaning = scanner.nextLine();

            if (!validator.isValidMeaning(newMeaning)||newMeaning.contains("\t")) {
                System.out.println("오류: 잘못된 뜻 입력 형식입니다.");
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

// 음절 구분된 단어 입력
        String syllableSeparated;
        while (true) {
            System.out.print("음절 구분된 단어를 입력하세요 (예: ap.ple) >> ");
            syllableSeparated = scanner.nextLine();

            // 입력된 "."을 "·"로 변환
            String formattedSyllableSeparated = syllableSeparated.replace(".", "·");

            if (!validator.isValidSyllableFormat(wordToUpdate.getEnglishWord(), formattedSyllableSeparated)) {
                System.out.println("오류: 잘못된 입력 형식입니다.");
                continue;
            }

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
        } else {
            // 1차 강세 입력
            while (true) {
                System.out.print("1차 강세 위치를 입력하세요 (없으면 x, 모르면 ?) >> ");
                primaryStress = scanner.nextLine().trim();

                if (primaryStress.equalsIgnoreCase("x") || primaryStress.equals("?")) {
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
                    System.out.print("2차 강세 위치를 입력하세요 (없으면 x, 모르면 ?) >> ");
                    secondaryStress = scanner.nextLine();

                    // x 또는 ?인 경우 처리
                    if (secondaryStress.equalsIgnoreCase("x") || secondaryStress.equals("?")) {
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

// 품사별로 추가 정보를 입력받는 로직 추가
        PartOfSpeech updatedPart = null;
        switch (newPos) {
            case "동사":
                System.out.print("현재형을 입력하세요 (미입력을 원하면 Enter) >> ");
                String present = getValidatedInput(scanner, "현재형");

                System.out.print("과거형을 입력하세요 (미입력을 원하면 Enter) >> ");
                String past = getValidatedInput(scanner, "과거형");

                System.out.print("과거분사를 입력하세요 (미입력을 원하면 Enter) >> ");
                String pastParticiple = getValidatedInput(scanner, "과거분사");

                updatedPart = new Word.Verb(
                        newMeaning,
                        syllableSeparated,
                        primaryStress,
                        secondaryStress,
                        pronunciationText,
                        present,
                        past,
                        pastParticiple
                );
                break;

            case "명사":
                System.out.print("단수형을 입력하세요 (미입력을 원하면 Enter) >> ");
                String singular = getValidatedInput(scanner, "단수형");

                System.out.print("복수형을 입력하세요 (미입력을 원하면 Enter) >> ");
                String plural = getValidatedInput(scanner, "복수형");

                updatedPart = new Word.Noun(
                        newMeaning,
                        syllableSeparated,
                        primaryStress,
                        secondaryStress,
                        pronunciationText,
                        singular,
                        plural
                );
                break;

            case "형용사":
                System.out.print("원형을 입력하세요 (미입력을 원하면 Enter) >> ");
                String baseForm = getValidatedInput(scanner, "원형");

                System.out.print("비교급을 입력하세요 (미입력을 원하면 Enter) >> ");
                String comparative = getValidatedInput(scanner, "비교급");

                System.out.print("최상급을 입력하세요 (미입력을 원하면 Enter) >> ");
                String superlative = getValidatedInput(scanner, "최상급");

                updatedPart = new Word.Adjective(
                        newMeaning,
                        syllableSeparated,
                        primaryStress,
                        secondaryStress,
                        pronunciationText,
                        baseForm,
                        comparative,
                        superlative
                );
                break;

            default:
                updatedPart = new Word.PartOfSpeech(
                        newMeaning,
                        syllableSeparated,
                        primaryStress,
                        secondaryStress,
                        pronunciationText
                ) {};
                break;
        }

        // 출력: 수정 전 정보
        System.out.println("\n< 수정 전 >");
        printPartOfSpeech(selectedPos, selectedPart, selectedPart.getPronunciation().split("·").length);

        // 출력: 수정 후 정보
        System.out.println("\n< 수정 후 >");
        printPartOfSpeech(newPos, updatedPart, syllableSeparated.split("·").length);


        while (true) {
            System.out.printf("\n'%s'의 단어 정보를 수정하시겠습니까?%n",
                    wordToUpdate.getEnglishWord());
            System.out.println("(1) 예");
            System.out.println("(2) 아니오");
            System.out.print("메뉴를 선택하세요 >> ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // 수정 적용
                partsOfSpeech.remove(selectedPos); // 기존 품사를 제거
                partsOfSpeech.put(newPos, updatedPart); // 새 품사를 추가
                System.out.println("단어 수정이 완료되었습니다.\n");
                break;
            } else if (choice.equals("2")) {
                System.out.println("수정이 취소되었습니다.\n");
                break;
            } else {
                System.out.println("숫자 1 또는 2를 입력해주세요.");
            }
        }

    }
    private void printPartOfSpeech(String pos, PartOfSpeech partOfSpeech, int syllableCount) {
        System.out.println("  품사: " + pos);
        System.out.println("  뜻: " + partOfSpeech.getMeaning());
        System.out.println("  음절구분된단어: " + partOfSpeech.getPronunciation());

        // 강세 출력
        if (syllableCount > 1) {
            System.out.println("  1차강세: " + partOfSpeech.getPrimaryStress());
        }
        if (syllableCount > 2) {
            System.out.println("  2차강세: " + partOfSpeech.getSecondaryStress());
        }

        System.out.println("  발음: " + partOfSpeech.getPronunciationText());

        // 품사별 추가 정보 출력
        if (partOfSpeech instanceof Word.Verb) {
            Word.Verb verb = (Word.Verb) partOfSpeech;
            if (!"미입력".equals(verb.getPresent())) {
                System.out.println("  현재형: " + verb.getPresent());
            }
            if (!"미입력".equals(verb.getPast())) {
                System.out.println("  과거형: " + verb.getPast());
            }
            if (!"미입력".equals(verb.getPastParticiple())) {
                System.out.println("  과거분사: " + verb.getPastParticiple());
            }
        } else if (partOfSpeech instanceof Word.Noun) {
            Word.Noun noun = (Word.Noun) partOfSpeech;
            if (!"미입력".equals(noun.getSingular())) {
                System.out.println("  단수형: " + noun.getSingular());
            }
            if (!"미입력".equals(noun.getPlural())) {
                System.out.println("  복수형: " + noun.getPlural());
            }
        } else if (partOfSpeech instanceof Word.Adjective) {
            Word.Adjective adjective = (Word.Adjective) partOfSpeech;
            if (!"미입력".equals(adjective.getBaseForm())) {
                System.out.println("  원형: " + adjective.getBaseForm());
            }
            if (!"미입력".equals(adjective.getComparative())) {
                System.out.println("  비교급: " + adjective.getComparative());
            }
            if (!"미입력".equals(adjective.getSuperlative())) {
                System.out.println("  최상급: " + adjective.getSuperlative());
            }
        }

        System.out.println();
    }
    private String getValidatedInput(Scanner scanner, String description) {
        while (true) {
            String input = scanner.nextLine();


            // 입력값이 비어 있는 경우 기본값 반환
            if (input.isEmpty()) {
                return "미입력";
            }

            // 입력값이 유효한 영어 단어인지 검사
            if (!validator.isValidEnglishWord(input)||input.contains("\t")) {
                System.out.printf("오류: %s은 올바른 영어 단어 형식이어야 합니다. 다시 입력해주세요.\n", description);
                continue;
            }

            return input.trim(); // 올바른 입력 반환
        }
    }

}
