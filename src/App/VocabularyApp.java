package App;

import Add.AddWord;
import Delete.DeleteWord;
import Search.SearchWord;
import Update.UpdateWord;
import View.ViewWords;

import java.util.Scanner;

public class VocabularyApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("영어 단어장 프로그램");
            System.out.println("0. 현재 저장된 단어 출력");
            System.out.println("1. 단어 입력");
            System.out.println("2. 단어 검색");
            System.out.println("3. 단어 수정");
            System.out.println("4. 단어 삭제");
            System.out.println("5. 단어 조회");
            System.out.println("6. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 0:
                    System.out.println("현재 저장된 단어들:");
                    new ViewWords().run();  // 저장된 단어 출력
                    break;
                case 1:
                    new AddWord().run();
                    break;
                case 2:
                    new SearchWord().run();
                    break;
                case 3:
                    new UpdateWord().run();
                    break;
                case 4:
                    new DeleteWord().run();
                    break;
                case 5:
                    new ViewWords().run();
                    break;
                case 6:
                    running = false;
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        scanner.close();
    }
}
