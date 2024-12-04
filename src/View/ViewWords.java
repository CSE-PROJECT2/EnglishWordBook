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
                    String syllableSeparated = partOfSpeech.getPronunciation();

                    // 음절 수 계산
                    int syllableCount = syllableSeparated.split("·").length;



                    System.out.println("\n" + index + ". 품사: " + pos);
                    System.out.println("  뜻: " + partOfSpeech.getMeaning());
                    System.out.println("  음절구분된단어: " + partOfSpeech.getPronunciation());
                    // 1음절 단어: 1차강세와 2차강세 출력 생략
                    if (syllableCount > 1) {
                        System.out.println("  1차강세: " + partOfSpeech.getPrimaryStress());
                    }
                    // 2음절 단어: 2차강세 출력 생략
                    if (syllableCount > 2) {
                        System.out.println("  2차강세: " + partOfSpeech.getSecondaryStress());
                    }
                    System.out.println("  발음: " + partOfSpeech.getPronunciationText());

                    // 품사별 추가 정보 출력
                    if (partOfSpeech instanceof Word.Verb) {
                        Word.Verb verb = (Word.Verb) partOfSpeech;
                        if (!"미입력".equals(verb.getPresent())) {
                            System.out.println("  현재형: " + verb.getPresent());
                        }
                        if (!"미입력".equals(verb.getPast())) {
                            System.out.println("  과거형: " + verb.getPast());
                        }
                        if (!"미입력".equals(verb.getPastParticiple())) {
                            System.out.println("  과거분사: " + verb.getPastParticiple());
                        }
                    } else if (partOfSpeech instanceof Word.Noun) {
                        Word.Noun noun = (Word.Noun) partOfSpeech;
                        if (!"미입력".equals(noun.getSingular())) {
                            System.out.println("  단수형: " + noun.getSingular());
                        }
                        if (!"미입력".equals(noun.getPlural())) {
                            System.out.println("  복수형: " + noun.getPlural());
                        }
                    } else if (partOfSpeech instanceof Word.Adjective) {
                        Word.Adjective adjective = (Word.Adjective) partOfSpeech;
                        if (!"미입력".equals(adjective.getBaseForm())) {
                            System.out.println("  원형: " + adjective.getBaseForm());
                        }
                        if (!"미입력".equals(adjective.getComparative())) {
                            System.out.println("  비교급: " + adjective.getComparative());
                        }
                        if (!"미입력".equals(adjective.getSuperlative())) {
                            System.out.println("  최상급: " + adjective.getSuperlative());
                        }
                    }


                    index++;
                }
            }
        }
    }
}
