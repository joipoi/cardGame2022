package com.example.demo;
import java.util.Objects;
import java.util.Random;

public class CardDB {

    public static Card returnCard(String cardName) {
        Effect dmgThree = new Effect("damage", 6);
        Effect healFive = new Effect("healing", 5);





        Card strike = new Card("strike", 1, "attack", "deal 6-8 dmg",
                new Effect("damage", returnRandAmount(6, 8)));
        Card healingBolt = new Card("healing bolt", 1, "healing", "heal 5 dmg", healFive);
        Card scratch = new Card("scratch",1, "attack", "deal 5 damage", new Effect("damage", 5) );
        Card bite = new Card("bite",1, "attack", "deal 9 damage", new Effect("damage", 9) );
        Card shield = new Card("shield", 1, "skill", "block 5 damage", new Effect("block", 5));


        Card[] cardList = {strike, healingBolt, scratch, bite, shield};


        Card cardToReturn = new Card();

        for (Card card:cardList) {
            if(card.getName().equalsIgnoreCase(cardName)) {
                cardToReturn = card;
            }

        }
        if(cardToReturn.getName() == "testTest") {
            System.out.println("Return Card method failed in CardDB class i think");
        }
        return cardToReturn;


    }
    private static int returnRandAmount(int min, int max) {
        Random rand = new Random();
        int rand1 = rand.nextInt(max - min + 1) + min;
        return rand1;
    }
}
