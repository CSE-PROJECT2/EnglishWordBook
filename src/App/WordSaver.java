package App;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WordSaver {
    private static final String FILE_PATH = "src/WordBook.txt";

    public void saveWords(List<Word> wordList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Word word : wordList) {
                StringBuilder sb = new StringBuilder();

                // 단어 구분을 위해 "#" 추가
                sb.append("#\n");
                sb.append(word.getEnglishWord()).append("\n");

                for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                    String pos = entry.getKey();
                    Word.PartOfSpeech partOfSpeech = entry.getValue();

                    sb.append("(")
                            .append(pos).append(">") // 품사
                            .append(partOfSpeech.getMeaning()).append(",") // 뜻
                            .append("음절구분된단어>").append(partOfSpeech.getPronunciation()).append(",") // 음절구분된단어
                            .append("1차강세>").append(partOfSpeech.getPrimaryStress()).append(",") // 1차 강세
                            .append("2차강세>").append(partOfSpeech.getSecondaryStress()).append(",") // 2차 강세
                            .append("발음>").append(partOfSpeech.getPronunciationText()).append(","); // 발음

                    // 품사별 추가 정보 저장
                    if (partOfSpeech instanceof Word.Verb) {
                        Word.Verb verb = (Word.Verb) partOfSpeech;
                        sb.append("{")
                                .append("현재>").append(verb.getPresent()).append(",")
                                .append("과거>").append(verb.getPast()).append(",")
                                .append("과거분사>").append(verb.getPastParticiple())
                                .append("}");
                    } else if (partOfSpeech instanceof Word.Noun) {
                        Word.Noun noun = (Word.Noun) partOfSpeech;
                        sb.append("{")
                                .append("단수>").append(noun.getSingular()).append(",")
                                .append("복수>").append(noun.getPlural())
                                .append("}");
                    } else if (partOfSpeech instanceof Word.Adjective) {
                        Word.Adjective adjective = (Word.Adjective) partOfSpeech;
                        sb.append("{")
                                .append("원형>").append(adjective.getBaseForm()).append(",")
                                .append("비교급>").append(adjective.getComparative()).append(",")
                                .append("최상급>").append(adjective.getSuperlative())
                                .append("}");
                    }

                    sb.append(")\n");
                }

                writer.write(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다:\n" + e.getMessage());
        }
    }
}
