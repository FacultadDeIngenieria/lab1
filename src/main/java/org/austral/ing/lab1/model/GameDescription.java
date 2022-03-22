package org.austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GameDescription {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long idGameDescription;

    /*@Column(name = "GAME")
    private Game game;*/

    @Column(name = "LEVEL")
    private int lvl;

    @Column(name = "RANK")
    private String rank;

    public GameDescription() { }

    public GameDescriptionBuilder createGameDescription(int level){
        return new GameDescriptionBuilder(level);
    }

    public long getIdGameDescription() {
        return idGameDescription;
    }

    public void setIdGameDescription(long idGameDescription) {
        this.idGameDescription = idGameDescription;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    private GameDescription(GameDescriptionBuilder builder){
        this.lvl = builder.lvl;
    }

    public static class GameDescriptionBuilder {
        private int lvl;
        private String rank;

        public GameDescriptionBuilder(int lvl){
            this.lvl = lvl;
        }

        public GameDescriptionBuilder rank(String rank){
            this.rank = rank;
            return this;
        }

        public GameDescription buildGameDescription(){
            return new GameDescription(this);
        }
    }
}
