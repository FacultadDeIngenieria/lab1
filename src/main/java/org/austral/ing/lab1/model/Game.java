package org.austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long gameId;

    @Column(name = "GAME_NAME")
    private String gameName;

    @Column(name = "LEVEL_MAX")
    private int lvlMax;

    @Column(name = "CATEGORY")
    private String category;

    public Game() { }

}
