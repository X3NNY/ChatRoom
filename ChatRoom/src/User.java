public class User {
    private int id;
    private String username;
    private String password;


    public User(String username) {
        this.username = username;
    }
    public User(String username,String password) {
        this.username = username;
        this.password = MD5.encode(password);
    }
    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = MD5.encode(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
