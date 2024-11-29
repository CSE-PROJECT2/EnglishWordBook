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
                sb.append(word.getEnglish()).append("\n");

                for (Map.Entry<String, Word.PartOfSpeech> entry : word.getPartsOfSpeech().entrySet()) {
                    Word.PartOfSpeech pos = entry.getValue();
                    sb.append("(")
                            .append(entry.getKey()).append(">") // 품사
                            .append(pos.getMeaning()).append(",") // 의미
                            .append("발음기호>").append(pos.getPronunciation()).append(",") // 발음기호
                            .append("1차강세>").append(pos.getPrimaryStress()).append(",") // 1차 강세
                            .append("2차강세>").append(pos.getSecondaryStress()).append(",") // 2차 강세
                            .append("발음>").append(pos.getPronunciationText()).append(","); // 발음
                    // 추가 정보
                    sb.append("{");
                    for (Map.Entry<String, String> infoEntry : pos.getAdditionalInfo().entrySet()) {
                        sb.append(infoEntry.getKey()).append(">").append(infoEntry.getValue()).append(",");
                    }
                    // 마지막 "," 제거
                    if (sb.charAt(sb.length() - 1) == ',') {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    sb.append("})\n");
                }

                writer.write(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다:\n" + "프로그램을 종료합니다.");
            System.exit(0);
        }
    }
}
