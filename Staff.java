package chap8.users;

public class Staff extends Employee {
    public Staff(String name, String id, String username, String password) {
        super(name, id, username, password);
    }

    @Override
    public String getType() {
        return "STAFF";
    }
}