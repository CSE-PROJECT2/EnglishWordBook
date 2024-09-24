package View;

import App.Word;

import java.util.List;

public class ViewWords {

    public void run(List<Word> wordList) {
    	System.out.println("\n*** 현재 저장된 단어 출력 ***");
        if (wordList.isEmpty()) {
            System.out.println("저장된 단어가 없습니다.");
        } else {

            System.out.println("저장된 단어 목록:");
            for (Word word : wordList) {
                System.out.println(word);
            }
        }
        System.out.println("=======================================");
    }
}
