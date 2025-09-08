package chap8.courses;

public class Term {
    private String name;
    private String year;

    public Term(String name, String year) {
        this.name = name;
        this.year = year;
    }

    public String getName() { return name; }
    public String getYear() { return year; }

    @Override
    public String toString() {
        return name + " " + year;
    }
}