package Add;

import App.Word;

import java.util.List;
import java.util.Scanner;

public class AddWord {

    public void run(List<Word> wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("단어를 입력하세요: ");
        String english = scanner.nextLine();
        System.out.print("의미를 입력하세요 (한글로): ");
        String meaning = scanner.nextLine();

        Word newWord = new Word(english, meaning);
        wordList.add(newWord);
        System.out.println("단어가 저장되었습니다.");
    }
}
