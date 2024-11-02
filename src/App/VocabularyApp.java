package App;


import Add.AddWord;
import Delete.DeleteWord;
import Search.SearchWord;
import Update.UpdateWord;
import View.ViewWords;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VocabularyApp {
    private static List<Word> wordList = new ArrayList<>();

    public static void main(String[] args) {
        WordLoader loader = new WordLoader();
        loader.loadWords(wordList);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                System.out.println("영어 단어장 프로그램");
                System.out.println("0. 현재 저장된 단어 출력");
                System.out.println("1. 단어 입력");
                System.out.println("2. 단어 검색");
                System.out.println("3. 단어 수정");
                System.out.println("4. 단어 삭제");
                System.out.println("5. 단어 조회");
                System.out.println("6. 종료");
                System.out.print("선택: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 0:
                        new ViewWords().run(wordList);
                        break;
                    case 1:
                        new AddWord().run(wordList);
                        break;
                    case 2:
                        new SearchWord().run(wordList);
                        break;
                    case 3:
                        new UpdateWord().run(wordList);
                        break;
                    case 4:
                        new DeleteWord().run(wordList);
                        break;
                    case 5:
                        new ViewWords().run(wordList);
                        break;
                    case 6:
                        running = false;
                        WordSaver saver = new WordSaver();
                        saver.saveWords(wordList);
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 0에서 6 사이의 숫자를 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("오류: 숫자를 입력해주세요.");
            }
        }
    }
}