package App;

import java.util.Map;

public class Word {
    // 기존 필드
    private String english;
    private String syllableSeparated;
    private String pronunciation;
    private int accentPosition;
    private int secondaryAccentPosition;  // 2차 강세 추가
    private int syllableCount;
    private Map<String, String> meanings; // 품사별 의미 저장
    private Map<String, String> additionalInfo; // 품사별 추가 정보 저장
    private Map<String, String> meaningPronunciations; // 뜻별 발음 저장

    // 생성자
    public Word(String english, String syllableSeparated, String pronunciation, int accentPosition,
                int secondaryAccentPosition, int syllableCount, Map<String, String> meanings,
                Map<String, String> additionalInfo, Map<String, String> meaningPronunciations) {
        this.english = english;
        this.syllableSeparated = syllableSeparated;
        this.pronunciation = pronunciation;
        this.accentPosition = accentPosition;
        this.secondaryAccentPosition = secondaryAccentPosition;
        this.syllableCount = syllableCount;
        this.meanings = meanings;
        this.additionalInfo = additionalInfo;
        this.meaningPronunciations = meaningPronunciations;
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

    public int getSecondaryAccentPosition() {
        return secondaryAccentPosition;
    }

    public int getSyllableCount() {
        return syllableCount;
    }

    public Map<String, String> getMeanings() {
        return meanings;
    }

    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public Map<String, String> getMeaningPronunciations() {
        return meaningPronunciations;
    }


}
