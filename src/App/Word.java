package App;

import java.util.HashMap;
import java.util.Map;

public class Word {
    private String english; // 영어 단어
    private Map<String, PartOfSpeech> partsOfSpeech; // 품사별 정보 저장

    // 품사별 정보
    public static abstract class PartOfSpeech {
        private String meaning; // 뜻
        private String pronunciation; // 음절구분된단어
        private String primaryStress; // 1차 강세
        private String secondaryStress; // 2차 강세
        private String pronunciationText; // 발음

        public PartOfSpeech(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            this.meaning = meaning;
            this.pronunciation = pronunciation;
            this.primaryStress = primaryStress;
            this.secondaryStress = secondaryStress;
            this.pronunciationText = pronunciationText;
        }

        public String getMeaning() {
            return meaning;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public String getPrimaryStress() {
            return primaryStress;
        }

        public String getSecondaryStress() {
            return secondaryStress;
        }

        public String getPronunciationText() {
            return pronunciationText;
        }
    }

    // 동사 품사 클래스
    public static class Verb extends PartOfSpeech {
        private String present; // 현재형
        private String past; // 과거형
        private String pastParticiple; // 과거분사

        public Verb(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText,
                    String present, String past, String pastParticiple) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
            this.present = present;
            this.past = past;
            this.pastParticiple = pastParticiple;
        }

        public String getPresent() {
            return present;
        }

        public String getPast() {
            return past;
        }

        public String getPastParticiple() {
            return pastParticiple;
        }
    }

    // 명사 품사 클래스
    public static class Noun extends PartOfSpeech {
        private String singular; // 단수형
        private String plural; // 복수형

        public Noun(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText,
                    String singular, String plural) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
            this.singular = singular;
            this.plural = plural;
        }

        public String getSingular() {
            return singular;
        }

        public String getPlural() {
            return plural;
        }
    }

    // 형용사 품사 클래스
    public static class Adjective extends PartOfSpeech {
        private String baseForm; // 원형
        private String comparative; // 비교급
        private String superlative; // 최상급

        public Adjective(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText,
                         String baseForm, String comparative, String superlative) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
            this.baseForm = baseForm;
            this.comparative = comparative;
            this.superlative = superlative;
        }

        public String getBaseForm() {
            return baseForm;
        }

        public String getComparative() {
            return comparative;
        }

        public String getSuperlative() {
            return superlative;
        }
    }
    // 부사 품사 클래스
    public static class Adverb extends PartOfSpeech {
        public Adverb(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
        }
    }

    // 전치사 품사 클래스
    public static class Preposition extends PartOfSpeech {
        public Preposition(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
        }
    }

    // 접속사 품사 클래스
    public static class Conjunction extends PartOfSpeech {
        public Conjunction(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
        }
    }

    // 대명사 품사 클래스
    public static class Pronoun extends PartOfSpeech {
        public Pronoun(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
        }
    }

    // 감탄사 품사 클래스
    public static class Interjection extends PartOfSpeech {
        public Interjection(String meaning, String pronunciation, String primaryStress, String secondaryStress, String pronunciationText) {
            super(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText);
        }
    }


    public Word(String english) {
        this.english = english;
        this.partsOfSpeech = new HashMap<>();
    }

    public void addPartOfSpeech(String pos, PartOfSpeech details) {
        partsOfSpeech.put(pos, details);
    }

    public String getEnglishWord() {
        return english;
    }

    public Map<String, PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }
}
