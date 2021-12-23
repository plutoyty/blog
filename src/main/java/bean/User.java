package bean;

public class User {



    private String head;
    private String id;
    private String name;
    private String email;
    private String password;
    private String sex;
    private String birth;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    private String brief;
    public User() {
    }

    public User(String name, String email, String password, String sex, String birth,String brief) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.birth = birth;
        this.brief = brief;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }
}
