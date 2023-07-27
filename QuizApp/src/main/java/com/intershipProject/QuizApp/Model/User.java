package com.intershipProject.QuizApp.Model;

import jakarta.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Date bithDate;
    @Column
    private int role;
    @Column(name = "session_id")
    private String sessionId;
    @Column
    private String gender;
    @Column
    private String email;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<ContactUs> contactUs;

    public List<ContactUs> getContactUs() {
        return contactUs;
    }

    public void setContactUs(List<ContactUs> contactUs) {
        this.contactUs = contactUs;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserQuiz> userQuizzes;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getBithDate() {
        return bithDate;
    }

    public void setBithDate(Date bithDate) {
        this.bithDate = bithDate;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<UserQuiz> getUserQuizzes() {
        return userQuizzes;
    }

    public void setUserQuizzes(List<UserQuiz> userQuizzes) {
        this.userQuizzes = userQuizzes;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
