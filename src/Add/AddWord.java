package Add;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class AddWord {

    public void run(List<Word> wordList) {
    	System.out.println("\n*** 단어 입력 ***");
        Scanner scanner = new Scanner(System.in);
        System.out.print("영단어를 입력하세요 >> ");
        String english = scanner.nextLine();
        System.out.print("뜻을 입력하세요 (한글로) >> ");
        String meaning = scanner.nextLine();

        Word newWord = new Word(english, meaning);
        wordList.add(newWord);
        System.out.println("단어가 저장되었습니다.\n");
    }
}
