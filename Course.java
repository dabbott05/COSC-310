package chap8.courses;

public class Course {
    private String subject;
    private String number; // Changed to String
    private String title;
    private int credits;

    public Course(String subject, String number, String title, int credits) {
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.credits = credits;
    }

    public String getSubject() { return subject; }
    public String getNumber() { return number; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }

    @Override
    public String toString() {
        return subject + " " + number + ": " + title + " (" + credits + " credits)";
    }
}