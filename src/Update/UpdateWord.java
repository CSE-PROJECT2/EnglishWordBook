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
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
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

        System.out.println("'" + wordToUpdate.getEnglish() + "'의 여러 뜻이 있습니다.");
        int index = 1;
        for (Map.Entry<String, PartOfSpeech> entry : partsOfSpeech.entrySet()) {
            System.out.printf("%d.<%s> %s%n", index, entry.getKey(), entry.getValue().getMeaning());
            index++;
        }

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

        String selectedPos = (String) partsOfSpeech.keySet().toArray()[selectedIndex - 1];
        PartOfSpeech selectedPart = partsOfSpeech.get(selectedPos);

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

        // 새로운 뜻 입력
        String newMeaning;
        while (true) {
            System.out.print("새로운 뜻을 입력하세요 (한글로) >> ");
            newMeaning = scanner.nextLine();
            if (!validator.isValidMeaning(newMeaning)) {
                System.out.println("오류: 잘못된 뜻 입력 형식입니다. 한글만 입력해주세요.");
            } else {
                break;
            }
        }

        // 기존과 동일한 품사와 뜻인지 확인
        if (partsOfSpeech.containsKey(newPos) && partsOfSpeech.get(newPos).getMeaning().equals(newMeaning)) {
            System.out.println("동일한 품사와 뜻이 이미 존재합니다. 저장되지 않습니다.\n");
            return;
        }

        // 하드코딩으로 기존 객체의 내용을 수정
        PartOfSpeech updatedPart = new PartOfSpeech(
                newMeaning,
                selectedPart.getPronunciation(),
                selectedPart.getPrimaryStress(),
                selectedPart.getSecondaryStress(),
                selectedPart.getPronunciationText()
        ) {};

        while (true) {
            System.out.printf("\n'%s'의 품사 '<%s>'를 '<%s>'로, 뜻을 '%s'로 수정하시겠습니까?%n",
                    wordToUpdate.getEnglish(), selectedPos, newPos, newMeaning);
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
}
