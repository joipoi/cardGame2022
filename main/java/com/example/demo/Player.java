package com.example.demo;
import java.util.ArrayList;

public class Player extends Character{
    private int mana;
    private String[] perks;
    private int health;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String[] getPerks() {
        return perks;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand( ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setPerks(String[] perks) {
        this.perks = perks;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void printDeck() {
        System.out.println("Printing deck: ");
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(i + ": " +  deck.get(i).getName());
        }
    }

    public Player(int health, ArrayList<Card> deck, int startMana) {
        super();
        this.health = health;
        this.deck = deck;
        hand = new ArrayList<>();
        this.mana = startMana;


    }
}
