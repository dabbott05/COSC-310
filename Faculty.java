package chap8.users;

public class Faculty extends Employee {

    public Faculty(String name, String id, String username, String password) {
        super(name, id, username, password);
    }

    @Override
    public String getType() {
        return "FACULTY";
    }
}