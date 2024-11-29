package View;

import App.Word;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewWords {
    public void run(List<Word> wordList) {
        if (wordList.isEmpty()) {
            System.out.println("저장된 단어가 없습니다.");
        } else {
            // 단어 목록을 음절 구분을 기준으로 알파벳 순으로 정렬
            Collections.sort(wordList, (w1, w2) -> w1.getSyllableSeparated().compareToIgnoreCase(w2.getSyllableSeparated()));
            System.out.println("저장된 단어 목록:");

            for (Word word : wordList) {
                System.out.println(word.getEnglishWord()); // 단어 이름 출력
                System.out.println();

                int i = 1;
                // 품사별로 출력
                for (Map.Entry<String, String> entry : word.getMeanings().entrySet()) {
                    String pos = entry.getKey(); // 품사
                    String meaning = entry.getValue(); // 뜻

                    // 품사별 발음, 강세 위치, 2차 강세 등 출력
                    String pronunciation = word.getPronunciation();
                    String accentPosition = word.getAccentPosition() > 0 ? word.getAccentPosition() + "번째 음절" : "악센트 정보 없음";
                    String secondaryAccentPosition = word.getSecondaryAccentPosition() > 0 ? word.getSecondaryAccentPosition() + "번째 음절" : "2차 강세 정보 없음";
                    String meaningPronunciation = word.getMeaningPronunciations().get(pos);

                    // 출력 형식: 품사별로 발음, 뜻, 악센트 정보 출력
                    System.out.printf("%d. <%s> %s [%s]", i++, pos, word.getSyllableSeparated(), pronunciation);

                    // 동사, 형용사 등 변형형태 출력 (예: "swam - swum" 등)
                    if (pos.equals("동사")) {
                        System.out.print(" - " + word.getAdditionalInfo().getOrDefault(pos, ""));
                    } else if (pos.equals("형용사")) {
                        System.out.print(" - " + word.getAdditionalInfo().getOrDefault(pos, ""));
                    }

                    System.out.println();
                    System.out.println("뜻 : " + meaning);
                    System.out.println("해당 뜻의 발음 : " + meaningPronunciation);
                    System.out.println("악센트 : " + accentPosition + (secondaryAccentPosition.equals("2차 강세 정보 없음") ? "" : ", " + secondaryAccentPosition));
                    System.out.println();
                }
            }
        }
    }
}
