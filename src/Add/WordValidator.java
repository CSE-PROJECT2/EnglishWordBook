package Add;

import App.Word;

import java.util.List;

public class WordValidator {

    // 영어 단어만 허용하는 정규 표현식 패턴 (대소문자 포함)
    private static final String ENGLISH_PATTERN = "^[a-zA-Z]+$";

    // 단어의 뜻이 영어만 포함되어 있는지 확인하기 위한 정규 표현식 패턴
    private static final String MEANING_ENGLISH_PATTERN = "^[a-zA-Z\\s]+$";

    // 개행문자 문법규칙 4.1.1 확인용
    private static final String VALID_ENGLISH_FORMAT_PATTERN = "^[a-zA-Z]+$";

    // 한글로만 이루어진 단어를 검증하는 정규 표현식 패턴 (탭, 개행, 특수문자 제외)
    private static final String MEANING_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣ]+(\\s[가-힣ㄱ-ㅎㅏ-ㅣ]+)*$";



    // 입력한 단어가 올바른 영어 단어인지 확인
    public boolean isValidEnglishWord(String english) {
        return english.matches(ENGLISH_PATTERN);
    }

    // 입력한 단어가 이미 존재하는지 확인
    public boolean isDuplicateWord(List<Word> wordList, String english) {
        for (Word word : wordList) {
            if (word.getEnglish().equalsIgnoreCase(english)) {
                return true;  // 중복되는 단어가 존재함
            }
        }
        return false;
    }
    // 입력된 뜻이 모두 영어로만 이루어져 있는지 확인하여, 모두 영어일 경우 false 반환
    public boolean isMeaningInEnglish(String meaning) {
        if (meaning.matches(MEANING_ENGLISH_PATTERN)) {

            return false;
        }
        return true;
    }
    //의미규칙 파악 메서드
    // 1. 영어로만 구성되어야 함
    public boolean isEnglishOnly(String word) {
        return word.matches(VALID_ENGLISH_FORMAT_PATTERN);
    }

    // 2. 길이가 1 이상이어야 함
    public boolean hasValidLength(String word) {
        return word.length() > 0; // 빈 문자열만 체크하므로 길이가 1 이상이면 true 반환
    }

    // 3. 탭과 개행이 없어야 함
    public boolean noTabOrNewLine(String word) {
        return !word.contains("\t") && !word.contains("\n");
    }

    // 4. 처음과 끝에 공백이 없어야 함
    public boolean noLeadingOrTrailingSpaces(String word) {
        return !word.startsWith(" ") && !word.endsWith(" ");
    }

    // 단어의 뜻이 올바른 형식(영어 또는 한글)인지 확인
    public boolean isValidMeaning(String meaning) {
        return meaning != null && !meaning.trim().isEmpty() && meaning.matches(MEANING_PATTERN);
    }

}
