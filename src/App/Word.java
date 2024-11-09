package App;

import java.util.Map;

public class Word {
    // 영어 단어 필드 추가
    private String english;
    private String syllableSeparated;
    private String pronunciation;
    private int accentPosition;
    private int syllableCount;
    private Map<String, String> meanings; // 품사별 의미 저장

    // 생성자
    public Word(String english, String syllableSeparated, String pronunciation, int accentPosition, int syllableCount, Map<String, String> meanings) {
        this.english = english;
        this.syllableSeparated = syllableSeparated;
        this.pronunciation = pronunciation;
        this.accentPosition = accentPosition;
        this.syllableCount = syllableCount;
        this.meanings = meanings;
    }

    // getter 메서드
    public String getEnglishWord() {
        return english;
    }

    public String getSyllableSeparated() {
        return syllableSeparated;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public int getAccentPosition() {
        return accentPosition;
    }

    public int getSyllableCount() {
        return syllableCount;
    }

    public Map<String, String> getMeanings() {
        return meanings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(english).append(" [").append(syllableSeparated).append("]\n");
        sb.append("발음: ").append(pronunciation).append("\n");
        sb.append("악센트: ").append(accentPosition).append(" 번째 음절\n");
        sb.append("음절 수: ").append(syllableCount).append(" 개\n");
        sb.append("뜻:\n");

        int i = 1;
        for (Map.Entry<String, String> entry : meanings.entrySet()) {
            sb.append(i).append(". <").append(entry.getKey()).append("> ").append(entry.getValue()).append("\n");
            i++;
        }

        return sb.toString();
    }
}
