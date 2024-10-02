package Add;

import App.Word;

import java.util.List;

public class WordValidator {

    // 영어 단어만 허용하는 정규 표현식 패턴 (대소문자 포함)
    private static final String ENGLISH_PATTERN = "^[a-zA-Z]+$";

    // 단어의 뜻이 영어만 포함되어 있는지 확인하기 위한 정규 표현식 패턴
    private static final String MEANING_ENGLISH_PATTERN = "^[a-zA-Z\\s]+$";


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

}
