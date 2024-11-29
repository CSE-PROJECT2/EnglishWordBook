package Search;

import App.Word;
import Add.WordValidator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchWord {

    private WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 검색 ***");
        Scanner scanner = new Scanner(System.in);

        String searchWord;
        while (true) {
            System.out.print("검색할 단어 또는 정보를 입력하세요 >> ");
            searchWord = scanner.nextLine();

            // 1. 탭이나 개행 문자가 포함되어 있는지 확인
            if (!validator.noTabOrNewLine(searchWord)) {
                System.out.println("오류: 영단어에는 탭이나 개행 문자가 포함될 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            // 2. 단어의 처음과 끝에 공백이 없는지 확인
            if (!validator.noLeadingOrTrailingSpaces(searchWord)) {
                System.out.println("오류: 영단어의 시작과 끝에는 공백 문자가 없어야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 3. 영어 단어 형식이 유효한지 확인
            if (!validator.isValidEnglishWord(searchWord)) {
                System.out.println("오류: 영단어는 영어 알파벳으로만 구성되어야 합니다. 다시 입력해주세요.");
                continue;
            }

            break; // 유효성 검사를 통과한 경우 반복문 종료
        }

        boolean found = false;

        for (Word word : wordList) {
            // 영단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(searchWord)) {
                printWordDetails(word);
                found = true;
                break;
            }

            // 뜻, 발음, 과거형, 복수형, 비교급을 검색
            for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                Word.PartOfSpeech partOfSpeech = entry.getValue();
                if (partOfSpeech.getMeaning().contains(searchWord) ||
                        partOfSpeech.getPronunciation().contains(searchWord) ||
                        partOfSpeech.getPronunciationText().contains(searchWord) ||
                        partOfSpeech.getAdditionalInfo().values().stream().anyMatch(info -> info.contains(searchWord))) {
                    printWordDetails(word);
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        if (!found) {
            System.out.println("해당 정보가 존재하지 않습니다.\n");
        }
    }

    private void printWordDetails(Word word) {
        System.out.println(word.getEnglish());
        for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
            Word.PartOfSpeech partOfSpeech = entry.getValue();
            System.out.println("\n" + entry.getKey());
            System.out.println("뜻 : " + partOfSpeech.getMeaning());
            System.out.println("발음기호 : " + partOfSpeech.getPronunciation());
            System.out.println("1차강세 : " + partOfSpeech.getPrimaryStress());
            System.out.println("2차강세 : " + partOfSpeech.getSecondaryStress());
            System.out.println("발음 : " + partOfSpeech.getPronunciationText());

            // 추가 정보 출력 (현재형, 과거형, 복수형 등)
            partOfSpeech.getAdditionalInfo().forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });
        }
    }
}
