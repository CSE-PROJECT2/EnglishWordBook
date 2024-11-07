package Add;

import App.Word;
import java.util.List;

public class WordValidator {

    private static final String SYLLABLE_ENGLISH_PATTERN = "^[a-zA-Z·]+$";
    private static final String VALID_ENGLISH_FORMAT_PATTERN = "^[a-zA-Z]+$";
    private static final String PRONUNCIATION_PATTERN = "^[가-힣]+$";
    private static final String MEANING_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣ]+(\\s[가-힣ㄱ-ㅎㅏ-ㅣ]+)*$"; // 뜻이 한글만 들어가는지 확인
    private static final String[] ALLOWED_POS = {"명사", "동사", "형용사", "부사", "전치사", "접속사", "대명사", "감탄사"};
    private static final String MEANING_ENG_PATTERN = "^[a-zA-Z\\\\s]+$"; // 뜻이 영어와 공백이 들어가는지 확인
    
    public boolean isValidSyllableFormat(String english, String syllableSeparated) {
        return syllableSeparated.matches(SYLLABLE_ENGLISH_PATTERN) &&
                syllableSeparated.replace("·", "").equalsIgnoreCase(english) &&
                !syllableSeparated.startsWith("·") &&
                !syllableSeparated.endsWith("·") &&
                syllableSeparated.indexOf("··") == -1;
    }
    
    public boolean isValidPronunciation(String pronunciation) {
        return pronunciation.matches(PRONUNCIATION_PATTERN);
    }

    public boolean isMeaningInEnglish(String meaning) {
        return !meaning.matches(MEANING_ENG_PATTERN);
    }

    public boolean isValidEnglishWord(String english) {
        return english.matches(VALID_ENGLISH_FORMAT_PATTERN);
    }

    public boolean isValidAccentPosition(String syllableSeparated, int accentPosition) {
        int syllableCount = syllableSeparated.split("·").length;
        return accentPosition > 0 && accentPosition <= syllableCount;
    }

    public boolean isAllowedPos(String pos) {
        for (String allowedPos : ALLOWED_POS) {
            if (allowedPos.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidMeaning(String meaning) {
        return meaning != null && !meaning.trim().isEmpty() && meaning.matches(MEANING_PATTERN);
    }

      public boolean hasValidLength(String english) {
        return english.length() > 0; // 빈 문자열만 체크하므로 길이가 1 이상이면 true 반환
    }

    public boolean noTabOrNewLine(String english) {
        return !word.contains("\t") && !word.contains("\n");
    }

    public boolean noLeadingOrTrailingSpaces(String english) {
        return !word.startsWith(" ") && !word.endsWith(" ");
    }

    public boolean isDuplicateWord(List<Word> wordList, String english) {
        for (Word word : wordList) {
            if (word.getEnglishWord().equalsIgnoreCase(english)) {
                return true;
            }
        }
        return false;
    }
}
