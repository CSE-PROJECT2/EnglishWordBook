package Search;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class SearchWord {

    public void run(List<Word> wordList) {
    	System.out.println("\n*** 단어 검색 ***");
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 영단어를 입력하세요 >> ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        for (Word word : wordList) {
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
                System.out.println("찾은 영단어 >> " + word.getEnglish());
                System.out.println(word.getEnglish() + "의 뜻 >> " + word.getMeaning() + "\n");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("해당 영단어가 존재하지 않습니다.\n");
        }
    }
}
