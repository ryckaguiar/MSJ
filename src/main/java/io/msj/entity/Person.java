package io.msj.entity;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Person{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 30, nullable = false)
    private String name;
    
    @Column(length = 40)
    private String lastName;
    
    @Column(length = 3)
    private int age;
    
    @Column(length = 10)
    private String sex;
    
    private boolean deficient;
    
    @Temporal(TemporalType.DATE)
    private Calendar birthdate;
    
    @OneToOne
    private User user;

    public Person(String name, String lastName, int age, String sex, boolean deficient, Calendar birthdate) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.deficient = deficient;
        this.birthdate = birthdate;
    }

    public Person() {
       
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isDeficient() {
        return deficient;
    }

    public void setDeficient(boolean deficient) {
        this.deficient = deficient;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Calendar birthdate) {
        this.birthdate = birthdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
