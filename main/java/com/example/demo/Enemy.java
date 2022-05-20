package com.example.demo;
import java.util.Random;

public class Enemy  extends Character{
    private int health;
    private String name;
    private int randNum;

    public int getRandNum() {
        return randNum;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
        Random random = new Random();
        randNum = random.nextInt(3 - 1 + 1) + 1;
       // System.out.println(randNum);

    }

    @Override
    public String toString() {
        return "Enemy{" +
                "name='" + name + '\'' +
                '}';
    }
}
