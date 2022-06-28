package lab1.rest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    private String name;

    private String category;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Rank> ranks = new ArrayList<Rank>();

    public Game() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Rank> getRanks() {
        return ranks;
    }
}
