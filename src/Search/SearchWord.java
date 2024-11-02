package Search;

import App.Word;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchWord {

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 검색 ***");
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 영단어를 입력하세요 >> ");
        String searchWord = scanner.nextLine();
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
