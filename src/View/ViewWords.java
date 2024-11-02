package View;


import App.Word;
import java.util.Collections;
import java.util.List;

public class ViewWords {
    public void run(List<Word> wordList) {
        if (wordList.isEmpty()) {
            System.out.println("저장된 단어가 없습니다.");
        } else {
            Collections.sort(wordList, (w1, w2) -> w1.getSyllableSeparated().compareToIgnoreCase(w2.getSyllableSeparated()));
            System.out.println("저장된 단어 목록:");

            for (Word word : wordList) {
                System.out.println(word);
            }
        }
    }
}
