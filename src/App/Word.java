package App;

public class Word {
    private String english;
    private String meaning;

    public Word(String english, String meaning) {
        this.english = english;
        this.meaning = meaning;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return english + " : " + meaning;
    }
}
