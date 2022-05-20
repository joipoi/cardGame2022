package com.example.demo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Combat {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Card> discardPile;
    private int turn;
    // private Reward reward;


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }
    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Combat(String[] enemiesStrings) {
         this.player = new Player(25, makeCards(), 3);
         discardPile = new ArrayList<>();
     addEnemy(enemiesStrings);

    }

//1. load in player = create label/button that represents player, load in player data
//2.load in enemy = create label/button that represents enemy, load in enemy data
//3.load in players cards(hand) = create buttons that represents cards, load in card data
//4. start players turn

    public void playEffect(Effect effect, Character target) {
        if(target != null) {
                switch(effect.getType()) {
                    case "damage":
                        if(target.getBlock() < effect.getAmount() ) {
                            target.setHealth(target.getHealth() - (effect.getAmount() - target.getBlock() ));
                        }
                        target.setBlock(target.getBlock() - effect.getAmount());
                        break;
                    case "healing":
                        target.setHealth(target.getHealth() + effect.getAmount());
                        break;
                    default:
                        System.out.println("wrong effect1");
                }

        }else {
                switch(effect.getType()) {
                    case "block":
                        player.setBlock(player.getBlock() + effect.getAmount());
                        break;
                    case "draw":

                        break;
                    default:
                        System.out.println("wrong effect2");
                }


        }
    }

    private ArrayList<Card> makeCards() {

        Effect dmgThree = new Effect("damage", 3);

        Effect[] strikeEffects = {dmgThree};
        Effect healFive = new Effect("healing", 5);
        Effect[] healingBoltEffects = {healFive};

        Card strike = CardDB.returnCard("strike");
        Card healingBolt = CardDB.returnCard("healing bolt");
        ArrayList<Card> deck = new ArrayList<>();

        deck.add(CardDB.returnCard("shield"));
        deck.add(strike);
        deck.add(healingBolt);
        deck.add(strike);
        deck.add(healingBolt);
        deck.add(strike);
        deck.add(healingBolt);
        deck.add(strike);


        return deck;
    }

    private void addEnemy(String[] enemiesString) {
        enemies = new ArrayList<Enemy>();


    Enemy harpy = new Enemy("harpy", 20);
    Enemy goul = new Enemy("goul", 30);
    Enemy snake = new Enemy("snake", 25);
    Enemy[] allEnemyList = new Enemy[]{harpy, goul, snake};


    for (String enemyString : enemiesString) {
        for(int i = 0; i < allEnemyList.length; i++) {
            if(enemyString.equals(allEnemyList[i].getName())) {
                 enemies.add(allEnemyList[i]);
        }
        

        }
        
    }

}

    public void removeEnemy(int index) {
        enemies.remove(index);
    }

    public void printDiscardPile() {
        System.out.println("Printing discard pile: ");
        for(int i = 0; i < discardPile.size();i++) {
            System.out.println(i + ": " + discardPile.get(i).getName());
        }
    }

}

