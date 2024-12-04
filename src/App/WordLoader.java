package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WordLoader {
    private static final String FILE_PATH = "src/WordBook.txt";

    public List<Word> loadWords(List<Word> wordList) {
        wordList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String englishWord = null; // 현재 단어 저장
            Word currentWord = null;  // 현재 처리 중인 Word 객체

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // "#" 기호로 새로운 단어 시작
                if (line.startsWith("#")) {
                    if (currentWord != null) {
                        wordList.add(currentWord); // 이전 단어 저장
                    }
                    englishWord = reader.readLine().trim(); // 다음 줄에 단어
                    currentWord = new Word(englishWord);
                    continue;
                }

                if (currentWord == null || englishWord == null) {
                    System.out.println("파일 저장 중 오류가 발생했습니다:\n프로그램을 종료합니다.");
                    System.exit(0);
                    continue; // "#"와 단어 없이 품사 정보만 발견된 경우
                }

                // 품사 데이터를 처리
                String[] partOfSpeechEntries = line.split("\\),\\(");

                for (String entry : partOfSpeechEntries) {
                    try {
                        entry = entry.replaceAll("[()]", ""); // 괄호 제거
                        String[] elements = entry.split(",");
                        String[] posAndMeaning = elements[0].split(">");

                        // 품사와 의미 추출
                        String pos = posAndMeaning[0].trim();
                        String meaning = posAndMeaning[1].trim();

                        // 공통 데이터 추출
                        String pronunciation = elements[1].split(">")[1].trim();
                        String primaryStress = elements[2].split(">")[1].trim(); // 강세 데이터 추출
                        String secondaryStress = elements[3].split(">")[1].trim(); // 강세 데이터 추출
                        String pronunciationText = elements[4].split(">")[1].trim();

                        // 강세 데이터 검증 (하드코딩)
                        if (!(primaryStress.equals("x") || primaryStress.equals("?") || primaryStress.equals("-") || isNumeric(primaryStress))) {
                            System.out.println("유효하지 않은 1차 강세 값: ");
                            throw new IllegalArgumentException("유효하지 않은 1차 강세 값: " + primaryStress);

                        }

                        if (!(secondaryStress.equals("x") || secondaryStress.equals("?") || secondaryStress.equals("-") ||isNumeric(secondaryStress))) {
                            throw new IllegalArgumentException("유효하지 않은 2차 강세 값: " + secondaryStress);
                        }

                        // 품사별 추가 정보 처리
                        if (pos.equals("동사")) {
                            String present = elements[5].split(">")[1].trim();
                            String past = elements[6].split(">")[1].trim();
                            String pastParticiple = elements[7].split(">")[1].replace("}", "").trim();
                            Word.Verb verb = new Word.Verb(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText, present, past, pastParticiple);
                            currentWord.addPartOfSpeech(pos, verb);
                        } else if (pos.equals("명사")) {
                            String singular = elements[5].split(">")[1].trim();
                            String plural = elements[6].split(">")[1].replace("}", "").trim();
                            Word.Noun noun = new Word.Noun(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText, singular, plural);
                            currentWord.addPartOfSpeech(pos, noun);
                        } else if (pos.equals("형용사")) {
                            String baseForm = elements[5].split(">")[1].trim();
                            String comparative = elements[6].split(">")[1].trim();
                            String superlative = elements[7].split(">")[1].replace("}", "").trim();
                            Word.Adjective adjective = new Word.Adjective(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText, baseForm, comparative, superlative);
                            currentWord.addPartOfSpeech(pos, adjective);
                        } else if (pos.equals("부사") || pos.equals("전치사") || pos.equals("접속사") || pos.equals("대명사") || pos.equals("감탄사")) {
                            Word.PartOfSpeech generalPart = new Word.PartOfSpeech(meaning, pronunciation, primaryStress, secondaryStress, pronunciationText) {};
                            currentWord.addPartOfSpeech(pos, generalPart);
                        }
                    } catch (Exception e) {
                        System.out.println("파일 저장 중 오류가 발생했습니다:\n프로그램을 종료합니다.");
                        //System.out.println(e.getMessage());
                        System.exit(0);
                    }
                }
            }

            // 마지막 단어 저장
            if (currentWord != null) {
                wordList.add(currentWord);
            }
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다:\n프로그램을 종료합니다.");
            System.exit(0);
        }

        return wordList;
    }

    // 숫자인지 확인하는 하드코딩 유틸리티
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
