package com.example.demo;
public class Character {
    private int health;
    private int block;
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        if(block < 0) {
            this.block = 0;
        } else
        this.block = block;
    }
    public Character() {
        this.block = 0;
    }
}
