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
        //*필독*
        // 프로그램 시작시 파일에서 단어들을 읽어와서 wordList에 저장
        // 즉, 맨 처음 시작은 txt 파일에 읽어와서 Word를 ArrayList에 저장해서 이 List로 모든 기능을 이용하는 것입니다 !
        // 프로그램이 종료되면 ArrayList에 추가/삭제 된 리스트들이 txt 파일에 마지막에 반영되는 형식입니다.
        WordLoader loader = new WordLoader();
        loader.loadWords(wordList);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("*** 영어 단어장 프로그램 ***");
            System.out.println("(1) 단어 입력");
            System.out.println("(2) 단어 검색");
            System.out.println("(3) 단어 수정");
            System.out.println("(4) 단어 삭제");
            System.out.println("(5) 단어 조회");
            System.out.println("(6) 종료");
            System.out.print("메뉴를 선택하세요 >> ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                //제가 틀을 만들다 보니 어쩔수 없이 주요 기능은 구현해 놓았습니다.
                //작업 하시며 그대로 사용해도 되고, 각자 맡은 Class에 바꾸고 싶은대로 수정해도 됩니다 !
                //단, 수정할 경우에 Case 문의 형식에는 크게 벗어나지 말게 해주세요 !! ㅎㅎ
                case 0:
                    new ViewWords().run(wordList);
                    break;
                case 1: //입력 : 동훈
                    new AddWord().run(wordList);
                    break;
                case 2:// 검색 : 라니아 탄닝웨이
                    new SearchWord().run(wordList);
                    break;
                case 3: // 수정 : 지예 아리 지수
                    new UpdateWord().run(wordList);
                    break;
                case 4: // 삭제 : 지예 아리 지수
                    new DeleteWord().run(wordList);
                    break;
                case 5: // 조회 : 정범
                    new ViewWords().run(wordList);
                    break;
                case 6:
                    running = false;
                    // 종료 시 단어를 파일에 저장 -> 즉, 종료전에는 List에서 삭제/추가를 하고
                    // 최종 수정본을(ArrayList)를  txt에 덮어쓴다고
                    //생각하면 됩니다 !!
                    WordSaver saver = new WordSaver();
                    saver.saveWords(wordList);
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        scanner.close();
    }

}
