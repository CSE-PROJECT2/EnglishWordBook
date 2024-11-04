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
            System.out.print("검색할 영단어를 입력하세요 >> ");
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
            // 영어 단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(searchWord)) {  // getEnglishWord() 사용
                System.out.println(word.getEnglishWord() + " [" + word.getSyllableSeparated() + "]");
                System.out.println("발음: " + word.getPronunciation());
                System.out.println("악센트: " + word.getAccentPosition() + " 번째 음절");
                System.out.println("음절 수: " + word.getSyllableCount() + " 개");
                System.out.println("뜻:");

                int i = 1;
                for (Map.Entry<String, String> entry : word.getMeanings().entrySet()) {
                    System.out.println(i + ". <" + entry.getKey() + "> " + entry.getValue());
                    i++;
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("해당 영단어가 존재하지 않습니다.\n");
        }
    }
}
