package Add;

import App.Word;

import java.util.List;

public class WordValidator {

    // 영어 단어만 허용하는 정규 표현식 패턴 (대소문자 포함)
    private static final String ENGLISH_PATTERN = "^[a-zA-Z]+$";

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

}
