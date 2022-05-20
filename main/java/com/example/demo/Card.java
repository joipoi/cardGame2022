package com.example.demo;
public class Card {

    private int cost;
    private String type;
    private String description;
    private Effect[] effects;
    private String name;

   /* private int ID;
    private static int idCounter = 0; */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Effect[] getEffects() {
        return effects;
    }


    public void setEffects(Effect[] effects) {
        this.effects = effects;
    }



    public Card(String name, int cost, String type, String description, Effect[] effects) {
        this.cost = cost;
        this.type = type;
        this.description = description;
        this.effects = effects;
        this.name = name;
    }
    public Card(String name, int cost, String type, String description, Effect effect) {
        this.cost = cost;
        this.type = type;
        this.description = description;
        this.effects = new Effect[]{effect};
        this.name = name;
    }
    public Card() {

    }
    public Card(Effect[] effects) {
        this.effects = effects;
    }
    public Card(Effect effect) {
        this.effects = new Effect[]{effect};
    }

}
