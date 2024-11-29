package Add;

import App.Word;
import java.util.List;

public class WordValidator {

    private static final String SYLLABLE_ENGLISH_PATTERN = "^[a-zA-Z·]+$";
    private static final String VALID_ENGLISH_FORMAT_PATTERN = "^[a-zA-Z]+$";
    private static final String PRONUNCIATION_PATTERN = "^[가-힣]+$";
    private static final String MEANING_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣ]+(\\s[가-힣ㄱ-ㅎㅏ-ㅣ]+)*$"; // 뜻이 한글만 들어가는지 확인
    private static final String[] ALLOWED_POS = {"명사", "동사", "형용사", "부사", "전치사", "접속사", "대명사", "감탄사"};
    private static final String MEANING_ENGLISH_PATTERN = "^[a-zA-Z\\\\s]+$"; // 뜻이 영어와 공백이 들어가는지 확인
    private static final String ADDITIONAL_INFO_PATTERN = "\\{[^{}]*}"; // 중괄호에 포함된 추가 정보 패턴
    private static final String PHONETIC_INFO_PATTERN = "\\[[^\\[\\]]*\\]"; // 대괄호에 포함된 발음 정보 패턴

    // 기존 함수 유지
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
        return !meaning.matches(MEANING_ENGLISH_PATTERN);
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
        return !english.contains("\t") && !english.contains("\n");
    }

    public boolean noLeadingOrTrailingSpaces(String english) {
        return !english.startsWith(" ") && !english.endsWith(" ");
    }

    public boolean isDuplicateWord(List<Word> wordList, String english) {
        for (Word word : wordList) {
            if (word.getEnglishWord().equalsIgnoreCase(english)) {
                return true;
            }
        }
        return false;
    }

    // 2차 강세 위치 유효성 검사
    public boolean isValidSecondaryAccentPosition(String syllableSeparated, int secondaryAccentPosition) {
        int syllableCount = syllableSeparated.split("·").length;
        return secondaryAccentPosition >= 0 && secondaryAccentPosition <= syllableCount;
    }

    // 품사 추가 정보 유효성 검사
    public boolean isValidAdditionalInfo(String additionalInfo) {
        return additionalInfo == null || additionalInfo.isEmpty() || additionalInfo.matches(ADDITIONAL_INFO_PATTERN);
    }

    // 발음 정보 유효성 검사
    public boolean isValidPhoneticInfo(String phoneticInfo) {
        return phoneticInfo != null && phoneticInfo.matches(PHONETIC_INFO_PATTERN);
    }

    // 품사, 뜻, 발음 정보와 추가 정보가 모두 포함된 유효성 검사
    public boolean isValidMeaningEntry(String pos, String meaningWithInfo) {
        String[] components = meaningWithInfo.split("\\s+", 2); // 품사와 뜻을 분리
        if (components.length < 2) {
            return false;
        }

        String meaning = components[1];
        String phoneticInfo = extractPattern(meaning, PHONETIC_INFO_PATTERN);
        String additionalInfo = extractPattern(meaning, ADDITIONAL_INFO_PATTERN);

        if (!isAllowedPos(pos)) return false;
        if (phoneticInfo != null && !isValidPhoneticInfo(phoneticInfo)) return false;
        if (additionalInfo != null && !isValidAdditionalInfo(additionalInfo)) return false;

        // 뜻만 남은 부분이 유효한지 검사
        meaning = meaning.replaceAll(PHONETIC_INFO_PATTERN, "").replaceAll(ADDITIONAL_INFO_PATTERN, "").trim();
        return isValidMeaning(meaning);
    }

    // Helper method to extract a pattern
    private String extractPattern(String input, String pattern) {
        return input.matches(".*" + pattern + ".*") ? input.replaceFirst(".*(" + pattern + ").*", "$1") : null;
    }
}
