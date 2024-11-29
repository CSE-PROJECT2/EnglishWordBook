package Search;

import App.Word;
import Add.WordValidator;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchWord {

    private WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 검색 ***");
        Scanner scanner = new Scanner(System.in);

        String searchWord;

            System.out.print("검색할 단어 또는 정보를 입력하세요(영단어 뿐만아니라 다른 의미, 과거형 등으로도 검색 가능합니다) >> "); // 모든 뜻, 발음등 교수님의 요구사항대로 검색
            searchWord = scanner.nextLine();


        boolean found = false;

        for (Word word : wordList) {
            // 영단어 기준으로 검색
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
                printWordDetails(word);
                found = true;
                break;
            }

            // 뜻, 발음, 과거형, 복수형, 비교급 등을 검색
            for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                Word.PartOfSpeech partOfSpeech = entry.getValue();
                if (partOfSpeech.getMeaning().contains(searchWord) ||
                        partOfSpeech.getPronunciation().contains(searchWord) ||
                        partOfSpeech.getPronunciationText().contains(searchWord)) {
                    printWordDetails(word);
                    found = true;
                    break;
                }

                // 품사별 추가 정보를 검색
                if (partOfSpeech instanceof Word.Verb) {
                    Word.Verb verb = (Word.Verb) partOfSpeech;
                    if (verb.getPresent().contains(searchWord) ||
                            verb.getPast().contains(searchWord) ||
                            verb.getPastParticiple().contains(searchWord)) {
                        printWordDetails(word);
                        found = true;
                        break;
                    }
                } else if (partOfSpeech instanceof Word.Noun) {
                    Word.Noun noun = (Word.Noun) partOfSpeech;
                    if (noun.getSingular().contains(searchWord) ||
                            noun.getPlural().contains(searchWord)) {
                        printWordDetails(word);
                        found = true;
                        break;
                    }
                } else if (partOfSpeech instanceof Word.Adjective) {
                    Word.Adjective adjective = (Word.Adjective) partOfSpeech;
                    if (adjective.getBaseForm().contains(searchWord) ||
                            adjective.getComparative().contains(searchWord) ||
                            adjective.getSuperlative().contains(searchWord)) {
                        printWordDetails(word);
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                break;
            }
        }

        if (!found) {
            System.out.println("해당 정보가 존재하지 않습니다.\n");
        }
    }

    private void printWordDetails(Word word) {
        System.out.println("\n단어: " + word.getEnglish());
        for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
            String pos = entry.getKey();
            Word.PartOfSpeech partOfSpeech = entry.getValue();

            System.out.println("\n품사: " + pos);
            System.out.println("  뜻: " + partOfSpeech.getMeaning());
            System.out.println("  발음기호: " + partOfSpeech.getPronunciation());
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
        }
    }
}
