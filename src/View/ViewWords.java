package View;

import App.Word;
import App.Word.PartOfSpeech;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewWords {
    public void run(List<Word> wordList) {
        if (wordList.isEmpty()) {
            System.out.println("저장된 단어가 없습니다.");
        } else {
            Collections.sort(wordList, (w1, w2) -> w1.getEnglish().compareToIgnoreCase(w2.getEnglish()));
            System.out.println("저장된 단어 목록:");

            for (Word word : wordList) {
                System.out.println(word.getEnglish());
                int index = 1;
                for (Map.Entry<String, PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                    PartOfSpeech pos = entry.getValue();
                    System.out.println("\n" + index + ". " + entry.getKey());
                    System.out.println("뜻 : " + pos.getMeaning());
                    System.out.println("발음기호 : " + pos.getPronunciation());
                    System.out.println("1차강세 : " + pos.getPrimaryStress());
                    System.out.println("2차강세 : " + pos.getSecondaryStress());
                    System.out.println("발음 : " + pos.getPronunciationText());

                    Map<String, String> additionalInfo = pos.getAdditionalInfo();
                    for (Map.Entry<String, String> infoEntry : additionalInfo.entrySet()) {
                        System.out.println(infoEntry.getKey() + " : " + infoEntry.getValue());
                    }
                    index++;
                }
            }
        }
    }
}
