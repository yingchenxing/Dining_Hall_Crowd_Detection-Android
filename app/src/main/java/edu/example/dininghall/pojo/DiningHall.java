package edu.example.dininghall.pojo;

import java.util.List;

import edu.example.dininghall.variable.Level;


public class DiningHall {
    private String name;
    private int number;
    private List<Restaurant> restaurants;


    public DiningHall(String name, int number, List<Restaurant> restaurants) {
        this(name, number);
        this.restaurants = restaurants;
    }

    public DiningHall(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Level getState() {
        if (number < 10)
            return Level.NORMAL;
        else if (number < 20)
            return Level.CROWDED;
        else
            return Level.OVERCROWDED;
    }
}
