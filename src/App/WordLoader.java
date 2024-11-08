package App;

import Add.WordValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";
    private WordValidator validator = new WordValidator();

    public void loadWords(List<Word> wordList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 5);

                if (parts.length < 5) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n프로그램을 종료합니다.");
                    System.exit(0);
                }

                String englishWord = parts[0];
                String syllableSeparated = parts[1];
                String pronunciation = parts[2];

                if (!validator.hasValidLength(englishWord) ||
                        !validator.noTabOrNewLine(englishWord) ||
                        !validator.noLeadingOrTrailingSpaces(englishWord) ||
                        !validator.isValidEnglishWord(englishWord)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:");
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }

                if (!validator.isValidSyllableFormat(englishWord, syllableSeparated)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:");
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }

                if (!validator.isValidPronunciation(pronunciation)) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:");
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }

                int accentPosition=-1;
                try {
                    accentPosition = Integer.parseInt(parts[3]);
                    if (!validator.isValidAccentPosition(syllableSeparated, accentPosition)) {
                        System.out.println("파일 저장 중 오류가 발생했습니다:");
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:");
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }

                String meaningPart = parts[4];
                if (!meaningPart.startsWith("(") || !meaningPart.endsWith(")")) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:");
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }

                String meaningContent = meaningPart.substring(1, meaningPart.length() - 1);
                String[] posAndMeanings = meaningContent.split("\\), \\(");

                Map<String, String> meanings = new HashMap<>();
                for (String posMeaning : posAndMeanings) {
                    String[] posMean = posMeaning.split(":");
                    if (posMean.length == 2) {
                        String pos = posMean[0];
                        String meaning = posMean[1];

                        // 품사와 뜻 앞뒤에 공백이 있으면 오류로 처리
                        if (pos.startsWith(" ") || pos.endsWith(" ") || meaning.startsWith(" ") || meaning.endsWith(" ")) {
                            System.out.println("파일 저장 중 오류가 발생했습니다:");
                            System.out.println("프로그램을 종료합니다.");
                            System.exit(0);
                        }

                        if (!validator.isAllowedPos(pos)) {
                            System.out.println("파일 저장 중 오류가 발생했습니다:");
                            System.out.println("프로그램을 종료합니다.");
                            System.exit(0);
                        }

                        if (!validator.isValidMeaning(meaning)) {
                            System.out.println("파일 저장 중 오류가 발생했습니다:" );
                            System.out.println("프로그램을 종료합니다.");
                            System.exit(0);
                        }

                        meanings.put(pos, meaning);
                    } else {
                        System.out.println("파일 저장 중 오류가 발생했습니다:");
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                    }
                }

                int syllableCount = syllableSeparated.split("·").length;
                Word word = new Word(englishWord, syllableSeparated, pronunciation, accentPosition, syllableCount, meanings);
                wordList.add(word);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: src\\WordBook.txt (지정된 파일을 찾을 수 없습니다)" );
            System.exit(0);
        }
    }
}
