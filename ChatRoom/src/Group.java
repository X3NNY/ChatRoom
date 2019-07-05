public class Group {
    private int id;
    private int uid;
    private String groupname;
    private String password;
    private String desc;

    public Group(String groupname, String password, String desc,int uid) {
        this.groupname = groupname;
        this.password = MD5.encode(password);
        this.desc = desc;
        this.uid = uid;
    }

    public Group(String groupname, String password) {
        this.groupname = groupname;
        this.password = MD5.encode(password);
    }

    public Group(String groupname) {
        this.groupname = groupname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setPassword(String password) {
        this.password = MD5.encode(password);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", uid=" + uid +
                ", groupname='" + groupname + '\'' +
                ", password='" + password + '\'' +
                ", desc='" + desc +
                '}';
    }
}
