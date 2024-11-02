package Delete;

import App.Word;
import java.util.List;
import java.util.Scanner;

public class DeleteWord {

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 영어단어를 입력하세요 >> ");
        String english = scanner.nextLine();

        Word wordToDelete = null;
        for (Word word : wordList) {
            // 영어 단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(english)) {  // getEnglishWord() 사용
                wordToDelete = word;
                break;
            }
        }

        if (wordToDelete != null) {
            wordList.remove(wordToDelete);
            System.out.println("'" + english + "' 단어가 삭제되었습니다.\n");
        } else {
            System.out.println("단어장에 '" + english + "' 단어가 존재하지 않습니다.\n");
        }
    }
}
