package test.codebabe.model;

/**
 * author: code.babe
 * date: 2016-11-30 19:08
 */
public class User {

    public User() {}

    public User(Long id, String name, Integer age, String location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
    }

    private Long id;
    private String name;
    private Integer age;
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User test(String param) {
        name = param;
        return this;
    }
}
