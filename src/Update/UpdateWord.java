package Update;

import App.Word;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UpdateWord {

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 수정 ***");
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 영단어를 입력하세요 >> ");
        String searchWord = scanner.nextLine();
        boolean found = false;

        for (Word word : wordList) {
            // 영어 단어 기준으로 검색
            if (word.getEnglishWord().equalsIgnoreCase(searchWord)) {
                found = true;
                Map<String, String> meanings = word.getMeanings();

                if (meanings.size() > 1) {
                    System.out.println("'" + word.getEnglishWord() + "'의 여러 뜻이 있습니다.");
                    int index = 1;
                    for (Map.Entry<String, String> entry : meanings.entrySet()) {
                        System.out.println(index + ". <" + entry.getKey() + "> " + entry.getValue());
                        index++;
                    }
                    System.out.print("수정할 뜻의 번호를 선택하세요 >> ");
                    int selectedMeaningIndex = scanner.nextInt();
                    scanner.nextLine(); // 개행 문자 처리

                    if (selectedMeaningIndex < 1 || selectedMeaningIndex > meanings.size()) {
                        System.out.println("잘못된 번호입니다. 수정이 취소되었습니다.\n");
                        return;
                    }

                    String selectedPos = (String) meanings.keySet().toArray()[selectedMeaningIndex - 1];

                    System.out.print("새로운 품사를 입력하세요 >> ");
                    String newPos = scanner.nextLine();

                    System.out.print("새로운 뜻을 입력하세요 >> ");
                    String newMeaning = scanner.nextLine();

                    while (true) {
                        System.out.printf("\n'%s'의 %d번째 뜻을 '<%s> %s'로 수정하시겠습니까?\n",
                                word.getEnglishWord(), selectedMeaningIndex, newPos, newMeaning);
                        System.out.println("(1) 예");
                        System.out.println("(2) 아니오");
                        System.out.print("메뉴를 선택하세요 >> ");
                        int confirmation = scanner.nextInt();
                        scanner.nextLine(); // 개행 문자 처리

                        if (confirmation == 1) {
                            meanings.remove(selectedPos);
                            meanings.put(newPos, newMeaning);
                            System.out.println("뜻이 수정되었습니다.\n");
                            break;
                        } else if (confirmation == 2) {
                            System.out.println("수정이 취소되었습니다.\n");
                            break;
                        } else {
                            System.out.println("숫자 1 또는 2를 입력해주세요.");
                        }
                    }
                } else {
                    System.out.println("'" + word.getEnglishWord() + "'의 뜻을 수정합니다.");
                    String onlyPos = meanings.keySet().iterator().next();

                    System.out.print("새로운 품사를 입력하세요 >> ");
                    String newPos = scanner.nextLine();

                    System.out.print("새로운 뜻을 입력하세요 >> ");
                    String newMeaning = scanner.nextLine();

                    while (true) {
                        System.out.printf("\n'%s'의 의미를 '<%s> %s'(으)로 수정하시겠습니까?\n", word.getEnglishWord(), newPos, newMeaning);
                        System.out.println("(1) 예");
                        System.out.println("(2) 아니오");
                        System.out.print("메뉴를 선택하세요 >> ");
                        int confirmation = scanner.nextInt();
                        scanner.nextLine(); // 개행 문자 처리

                        if (confirmation == 1) {
                            meanings.clear();
                            meanings.put(newPos, newMeaning);
                            System.out.println("뜻이 수정되었습니다.\n");
                            break;
                        } else if (confirmation == 2) {
                            System.out.println("수정이 취소되었습니다.\n");
                            break;
                        } else {
                            System.out.println("숫자 1 또는 2를 입력해주세요.");
                        }
                    }
                }
                break;
            }
        }

        if (!found) {
            System.out.println("해당 영단어가 존재하지 않습니다.");
        }
    }
}
