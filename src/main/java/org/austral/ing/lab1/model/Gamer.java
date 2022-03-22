package org.austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Gamer {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    public Gamer() { }

    public static GamerBuilder create(String userName) {
        return new GamerBuilder(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Gamer(GamerBuilder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
    }

    public static class GamerBuilder{
        private final String userName;
        private String password;

        public GamerBuilder(String userName){
            this.userName = userName;
        }

        public GamerBuilder password(String password){
            this.password = password;
            return this;
        }

        public Gamer build(){
            return new Gamer(this);
        }

    }
}
