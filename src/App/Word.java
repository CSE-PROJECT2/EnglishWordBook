package App;
import java.util.HashMap;
import java.util.Map;

public class Word {
    private String english; // 영어 단어
    private Map<String, PartOfSpeech> partsOfSpeech; // 품사별 정보 저장


    public static class PartOfSpeech {
        private String meaning; // 뜻
        private String pronunciation; // 발음기호
        private int primaryStress; // 1차 강세
        private int secondaryStress; // 2차 강세
        private String pronunciationText; // 발음
        private Map<String, String> additionalInfo; // 추가 정보 (예: 현재/과거/복수 등)


        public PartOfSpeech(String meaning, String pronunciation, int primaryStress, int secondaryStress,
                            String pronunciationText, Map<String, String> additionalInfo) {
            this.meaning = meaning;
            this.pronunciation = pronunciation;
            this.primaryStress = primaryStress;
            this.secondaryStress = secondaryStress;
            this.pronunciationText = pronunciationText;
            this.additionalInfo = additionalInfo;
        }


        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public int getPrimaryStress() {
            return primaryStress;
        }

        public void setPrimaryStress(int primaryStress) {
            this.primaryStress = primaryStress;
        }

        public int getSecondaryStress() {
            return secondaryStress;
        }

        public void setSecondaryStress(int secondaryStress) {
            this.secondaryStress = secondaryStress;
        }

        public String getPronunciationText() {
            return pronunciationText;
        }

        public void setPronunciationText(String pronunciationText) {
            this.pronunciationText = pronunciationText;
        }

        public Map<String, String> getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(Map<String, String> additionalInfo) {
            this.additionalInfo = additionalInfo;
        }


        @Override
        public String toString() {
            return "PartOfSpeech{" +
                    "meaning='" + meaning + '\'' +
                    ", pronunciation='" + pronunciation + '\'' +
                    ", primaryStress=" + primaryStress +
                    ", secondaryStress=" + secondaryStress +
                    ", pronunciationText='" + pronunciationText + '\'' +
                    ", additionalInfo=" + additionalInfo +
                    '}';
        }
    }


    public Word(String english) {
        this.english = english;
        this.partsOfSpeech = new HashMap<>();
    }


    public void addPartOfSpeech(String pos, PartOfSpeech details) {
        partsOfSpeech.put(pos, details);
    }

    public String getEnglish() {
        return english;
    }


    public PartOfSpeech getPartOfSpeech(String pos) {
        return partsOfSpeech.get(pos);
    }


    public Map<String, PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }


}
