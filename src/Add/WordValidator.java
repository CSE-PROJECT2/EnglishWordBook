package Add;

import App.Word;
import java.util.List;

public class WordValidator {

    // 영어 단어(음절 구분 포함)만 허용하는 정규 표현식 패턴 (대소문자 포함)
    private static final String SYLLABLE_ENGLISH_PATTERN = "^[a-zA-Z·]+$";

    // 한글 발음만 허용하는 정규 표현식 패턴
    private static final String PRONUNCIATION_PATTERN = "^[가-힣]+$";

    // 한글로만 이루어진 의미를 검증하는 정규 표현식 패턴
    private static final String MEANING_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣ]+(\\s[가-힣ㄱ-ㅎㅏ-ㅣ]+)*$";

    // 음절로 구분된 영어 단어 형식 검증 (영어와 ·만 포함)
    public boolean isValidSyllableSeparatedWord(String word) {
        return word.matches(SYLLABLE_ENGLISH_PATTERN);
    }

    // 입력된 발음이 한글로만 이루어져 있는지 확인
    public boolean isValidPronunciation(String pronunciation) {
        return pronunciation.matches(PRONUNCIATION_PATTERN);
    }

    // 입력된 강세 위치가 음절 수 내에서 유효한지 확인
    public boolean isValidAccentPosition(String word, int accentPosition) {
        int syllableCount = word.split("·").length;
        return accentPosition > 0 && accentPosition <= syllableCount;
    }

    // 입력된 단어가 이미 존재하는지 확인
    public boolean isDuplicateWord(List<Word> wordList, String syllableSeparated) {
        for (Word word : wordList) {
            if (word.getSyllableSeparated().equalsIgnoreCase(syllableSeparated)) {
                return true; // 중복되는 단어가 존재함
            }
        }
        return false;
    }

    // 단어의 길이가 1 이상인지 확인
    public boolean hasValidLength(String word) {
        return word.length() > 0; // 빈 문자열만 체크하므로 길이가 1 이상이면 true 반환
    }

    // 탭과 개행이 없어야 함
    public boolean noTabOrNewLine(String word) {
        return !word.contains("\t") && !word.contains("\n");
    }

    // 처음과 끝에 공백이 없어야 함
    public boolean noLeadingOrTrailingSpaces(String word) {
        return !word.startsWith(" ") && !word.endsWith(" ");
    }

    // 단어의 의미가 올바른 한글 형식인지 확인
    public boolean isValidMeaning(String meaning) {
        return meaning != null && !meaning.trim().isEmpty() && meaning.matches(MEANING_PATTERN);
    }
}
