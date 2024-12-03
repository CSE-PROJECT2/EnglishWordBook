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
            // 단어 목록을 알파벳순으로 정렬
            //Collections.sort(wordList, (w1, w2) -> w1.getEnglish().compareToIgnoreCase(w2.getEnglish()));

            System.out.println("저장된 단어 목록:");
            for (Word word : wordList) {
                System.out.println("\n단어: " + word.getEnglish());

                int index = 1;
                for (Map.Entry<String, PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                    String pos = entry.getKey();
                    PartOfSpeech partOfSpeech = entry.getValue();

                    System.out.println("\n" + index + ". 품사: " + pos);
                    System.out.println("  뜻: " + partOfSpeech.getMeaning());
                    System.out.println("  음절구분된단어: " + partOfSpeech.getPronunciation());
                    System.out.println("  1차강세: " + partOfSpeech.getPrimaryStress());
                    System.out.println("  2차강세: " + partOfSpeech.getSecondaryStress());
                    System.out.println("  발음: " + partOfSpeech.getPronunciationText());

                    // 품사별 추가 정보 출력
                    if (partOfSpeech instanceof Word.Verb) {
                        Word.Verb verb = (Word.Verb) partOfSpeech;
                        System.out.println("  현재형: " + verb.getPresent());
                        System.out.println("  과거형: " + verb.getPast());
                        System.out.println("  과거분사: " + verb.getPastParticiple());
                    } else if (partOfSpeech instanceof Word.Noun) {
                        Word.Noun noun = (Word.Noun) partOfSpeech;
                        System.out.println("  단수형: " + noun.getSingular());
                        System.out.println("  복수형: " + noun.getPlural());
                    } else if (partOfSpeech instanceof Word.Adjective) {
                        Word.Adjective adjective = (Word.Adjective) partOfSpeech;
                        System.out.println("  원형: " + adjective.getBaseForm());
                        System.out.println("  비교급: " + adjective.getComparative());
                        System.out.println("  최상급: " + adjective.getSuperlative());
                    }

                    index++;
                }
            }
        }
    }
}
