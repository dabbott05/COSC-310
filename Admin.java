package chap8.users;

public class Admin extends User {
    
    public Admin(String name, String id, String username, String password) {
        super(name, id, username, password);
    }

    @Override
    public String getType() {
        return "ADMIN";
    }
}