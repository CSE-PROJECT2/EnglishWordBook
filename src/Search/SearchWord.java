package Search;

import App.Word;
import Add.WordValidator;

import java.util.*;

public class SearchWord {

    private WordValidator validator = new WordValidator();

    public void run(List<Word> wordList) {
        System.out.println("\n*** 단어 검색 ***");
        Scanner scanner = new Scanner(System.in);

        System.out.print("검색할 단어 또는 정보를 입력하세요(영단어 뿐만 아니라 다른 의미, 과거형 등으로도 검색 가능합니다) >> ");
        String searchWord = scanner.nextLine().trim();

        // 영어일 경우 공백 제거 및 소문자 변환
        if (searchWord.matches(".*[a-zA-Z]+.*")) {
            searchWord = searchWord.replaceAll("\\s+", "").toLowerCase();
        }

        List<Word> matchedWords = new ArrayList<>();

        for (Word word : wordList) {
            boolean matched = false;

            // 영단어 기준으로 검색
            if (word.getEnglish().equalsIgnoreCase(searchWord)) {
                matchedWords.add(word);
                matched = true;
            }

            // 뜻, 발음, 과거형, 복수형, 비교급 등을 검색
            for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                Word.PartOfSpeech partOfSpeech = entry.getValue();
                if (partOfSpeech.getMeaning().equals(searchWord) ||
                        partOfSpeech.getPronunciation().equals(searchWord) ||
                        partOfSpeech.getPronunciationText().equals(searchWord)) {
                    if (!matchedWords.contains(word)) {
                        matchedWords.add(word);
                        matched = true;
                    }
                }

                // 품사별 추가 정보를 검색
                if (partOfSpeech instanceof Word.Verb) {
                    Word.Verb verb = (Word.Verb) partOfSpeech;
                    if (verb.getPresent().equals(searchWord) ||
                            verb.getPast().equals(searchWord) ||
                            verb.getPastParticiple().equals(searchWord)) {
                        if (!matchedWords.contains(word)) {
                            matchedWords.add(word);
                            matched = true;
                        }
                    }
                } else if (partOfSpeech instanceof Word.Noun) {
                    Word.Noun noun = (Word.Noun) partOfSpeech;
                    if (noun.getSingular().equals(searchWord) ||
                            noun.getPlural().equals(searchWord)) {
                        if (!matchedWords.contains(word)) {
                            matchedWords.add(word);
                            matched = true;
                        }
                    }
                } else if (partOfSpeech instanceof Word.Adjective) {
                    Word.Adjective adjective = (Word.Adjective) partOfSpeech;
                    if (adjective.getBaseForm().equals(searchWord) ||
                            adjective.getComparative().equals(searchWord) ||
                            adjective.getSuperlative().equals(searchWord)) {
                        if (!matchedWords.contains(word)) {
                            matchedWords.add(word);
                            matched = true;
                        }
                    }
                }
            }
        }

        // 검색된 리스트를 사전순으로 정렬
        matchedWords.sort(Comparator.comparing(Word::getEnglish, String.CASE_INSENSITIVE_ORDER));

        if (matchedWords.isEmpty()) {
            System.out.println("해당 정보가 존재하지 않습니다.\n");
        } else {
            System.out.println("\n검색 결과:");
            for (Word word : matchedWords) {
                printWordDetails(word);
            }
        }
    }

    private void printWordDetails(Word word) {
        System.out.println("\n< " + word.getEnglish() + " >");
        int index = 1;

        for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
            String pos = entry.getKey();
            Word.PartOfSpeech partOfSpeech = entry.getValue();
            String syllableSeparated = partOfSpeech.getPronunciation();
            // 음절 수 계산
            int syllableCount = syllableSeparated.split("·").length;



            System.out.println(index+"." );
            System.out.println("  품사: " + pos);
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
