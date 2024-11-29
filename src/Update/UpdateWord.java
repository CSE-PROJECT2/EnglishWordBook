package Update;

import Add.WordValidator;
import App.Word;
import App.Word.PartOfSpeech;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UpdateWord {

    public WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 수정 ***");
        Scanner scanner = new Scanner(System.in);

        String searchWord;
        boolean found = false;

        while (true) {
            System.out.print("수정할 영단어를 입력하세요 >> ");
            searchWord = scanner.nextLine();

            // 유효성 검사 추가
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

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }

        for (Word word : wordList) {
            // 영어 단어 기준으로 검색
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
                found = true;

                // 품사별 정보를 처리
                Map<String, PartOfSpeech> partsOfSpeech = word.getPartsOfSpeech();

                // 품사가 여러 개인 경우 선택 후 수정
                if (partsOfSpeech.size() > 1) {
                    System.out.println("'" + word.getEnglish() + "'의 여러 품사가 있습니다.");
                    int index = 1;
                    for (Map.Entry<String, PartOfSpeech> entry : partsOfSpeech.entrySet()) {
                        System.out.println(index + ". <" + entry.getKey() + "> " + entry.getValue().getMeaning());
                        index++;
                    }
                    System.out.print("수정할 품사의 번호를 선택하세요 >> ");
                    String input = scanner.nextLine(); // 입력을 문자열로 받음

                    // 입력된 값이 숫자인지 확인하고 범위 검사까지 진행
                    if (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > partsOfSpeech.size()) {
                        System.out.println("잘못된 번호입니다. 수정이 취소되었습니다.\n");
                        return;
                    }

                    int selectedMeaningIndex = Integer.parseInt(input);
                    String selectedPos = (String) partsOfSpeech.keySet().toArray()[selectedMeaningIndex - 1];
                    PartOfSpeech selectedPart = partsOfSpeech.get(selectedPos);

                    String newPos;
                    while (true) {
                        System.out.print("새로운 품사를 입력하세요 (예: 명사, 동사 등) >> ");
                        newPos = scanner.nextLine();
                        if (!validator.isAllowedPos(newPos)) {
                            System.out.println("오류: 품사는 ‘동사’, ‘명사’, ‘형용사’, ‘부사’, ‘전치사’, ‘접속사’, ‘대명사’, ‘감탄사’ 중 하나로 입력해주세요.");
                        } else {
                            break;
                        }
                    }

                    String newMeaning;
                    while (true) {
                        System.out.print("새로운 뜻을 입력하세요 (한글로) >> ");
                        newMeaning = scanner.nextLine();
                        if (!validator.isValidMeaning(newMeaning)) {
                            System.out.println("오류: 잘못된 뜻 입력 형식입니다.");
                        } else if (partsOfSpeech.containsKey(newPos) && partsOfSpeech.get(newPos).getMeaning().equals(newMeaning)) {
                            System.out.println("오류: 이미 저장된 뜻입니다.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.printf("\n'%s'의 %d번째 품사 '<%s> %s'로 수정하시겠습니까?\n",
                                word.getEnglish(), selectedMeaningIndex, newPos, newMeaning);
                        System.out.println("(1) 예");
                        System.out.println("(2) 아니오");
                        System.out.print("메뉴를 선택하세요 >> ");
                        int confirmation = scanner.nextInt();
                        scanner.nextLine(); // 개행 문자 처리

                        if (confirmation == 1) {
                            partsOfSpeech.remove(selectedPos);
                            PartOfSpeech updatedPart = selectedPart;
                            updatedPart.setMeaning(newMeaning);
                            updatedPart.setPronunciation("새로운 발음"); // 추가 변경사항 반영
                            partsOfSpeech.put(newPos, updatedPart);
                            System.out.println("품사가 수정되었습니다.\n");
                            break;
                        } else if (confirmation == 2) {
                            System.out.println("수정이 취소되었습니다.\n");
                            break;
                        } else {
                            System.out.println("숫자 1 또는 2를 입력해주세요.");
                        }
                    }
                } else {
                    System.out.println("'" + word.getEnglish() + "'의 품사를 수정합니다.");

                    String newPos;
                    while (true) {
                        System.out.print("새로운 품사를 입력하세요 (예: 명사, 동사 등) >> ");
                        newPos = scanner.nextLine();
                        if (!validator.isAllowedPos(newPos)) {
                            System.out.println("오류: 품사는 ‘동사’, ‘명사’, ‘형용사’, ‘부사’, ‘전치사’, ‘접속사’, ‘대명사’, ‘감탄사’ 중 하나로 입력해주세요.");
                        } else {
                            break;
                        }
                    }

                    String newMeaning;
                    while (true) {
                        System.out.print("새로운 뜻을 입력하세요 (한글로) >> ");
                        newMeaning = scanner.nextLine();
                        if (!validator.isValidMeaning(newMeaning)) {
                            System.out.println("오류: 잘못된 뜻 입력 형식입니다.");
                        } else if (partsOfSpeech.containsKey(newPos) && partsOfSpeech.get(newPos).getMeaning().equals(newMeaning)) {
                            System.out.println("오류: 이미 저장된 뜻입니다.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.printf("\n'%s'의 뜻을 '<%s> %s'(으)로 수정하시겠습니까?\n", word.getEnglish(), newPos, newMeaning);
                        System.out.println("(1) 예");
                        System.out.println("(2) 아니오");
                        System.out.print("메뉴를 선택하세요 >> ");

                        String choice = scanner.nextLine();

                        if (choice.equals("1")) {
                            // 품사 및 뜻 수정
                            partsOfSpeech.clear();
                            PartOfSpeech newPart = new PartOfSpeech(newMeaning, "새로운 발음", 1, 0, "새로운 발음", new HashMap<>());
                            partsOfSpeech.put(newPos, newPart);
                            System.out.println("뜻이 수정되었습니다.\n");
                            break;
                        } else if (choice.equals("2")) {
                            System.out.println("수정이 취소되었습니다.\n");
                            break;
                        } else {
                            System.out.println("숫자 1 또는 2를 입력해주세요.");
                        }
                    }
                }
                break;
            }
        }

        if (!found) {
            System.out.println("해당 영단어가 존재하지 않습니다.");
        }
    }
}
