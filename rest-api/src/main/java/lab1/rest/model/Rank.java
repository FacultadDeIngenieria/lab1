package lab1.rest.model;

import javax.persistence.*;

@Entity
@Table(name = "RANK")
public class Rank {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    public Rank() {
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

}
