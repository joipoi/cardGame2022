package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Controller
public class Mainboi {

    private Combat firstCombat = new Combat(new String[] { "goul", "harpy"} );;
    boolean targetBeingSelected = false;
    private Card cardBeingPlayed;
    private ArrayList<Card> playerDeck;

    private final int maxHandSize = 8;

    @GetMapping("/main")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        new Mainboi().go();

        String[] cardNames = new String[3];



     /*   for (int i = 0; i < firstCombat.getPlayer().getDeck().size(); i++) {
            cardNames[i] = firstCombat.getPlayer().getDeck().get(i).getName();
        } */
     cardNames[0] = "healing bolt";
     cardNames[1] = "strike";
     cardNames[2] = "shield";

        model.addAttribute("cards", firstCombat);

        return "poggy";
    }

    public void go() {



        firstCombat.setTurn(1);
        drawCards(3);
        firstCombat.getPlayer().setMana(3); //should set to startMana variable but this works for now

        //display enemy intent
        String enemyIntentString = "<html>";
        ArrayList<Enemy> tempEnemies = firstCombat.getEnemies();
        for (int j = 0; j < tempEnemies.size(); j++) {
            Effect enemyMoveEffect = EnemyDB.getEnemyMove(firstCombat, tempEnemies.get(j), firstCombat.getTurn());

            enemyIntentString += ("<br>" + tempEnemies.get(j).getName() + " will do ");
            enemyIntentString += enemyMoveEffect.getAmount() + " " + enemyMoveEffect.getType();
        }
        enemyIntentString += "</html>";

        //enemyIntentLabel.setText(enemyIntentString);

    }

    private void update() {
        if(firstCombat.getPlayer().getHealth() < 1) {
            endCombat();
        }
        String playerInfoString = "Player. HP = " + Integer.toString(firstCombat.getPlayer().getHealth())
                + " Mana = " + firstCombat.getPlayer().getMana();

        if(firstCombat.getPlayer().getBlock() > 0) {
            playerInfoString += " Block = " + firstCombat.getPlayer().getBlock();
        }


        // playerInfoLabel.setText (playerInfoString);
        updateEnemies();

    }
    private void updateEnemies() {
       /*
        for (int i = 0;i < firstCombat.getEnemies().size(); i++) {
            Enemy enemy = firstCombat.getEnemies().get(i);
          enemieLabels.get(i).setText(enemy.getName() + ". HP =" + enemy.getHealth());

          if(enemy.getHealth() < 1) {
              myFrame.getContentPane().remove(enemieButtons.get(i));
              firstCombat.removeEnemy(i);
              enemieButtons.remove(i);
              enemieLabels.remove(i);
          }
        }
        */
    }

    private void drawCards(int amount) {
        //there is a bug that happens when you loop through the deck, have fun fixing that dumbass
/*
        ArrayList<Card> playerHand = firstCombat.getPlayer().getHand();
        for(int i = 0; i < amount; i++) {
           if(playerDeck.size() > 0) {
            playerHand.add(playerDeck.get(0));
            playerDeck.remove(0);
           } else {
               playerDeck.addAll(firstCombat.getDiscardPile());
               firstCombat.getDiscardPile().removeAll(firstCombat.getDiscardPile());
               drawCards(1);
           }

        }

        int index = 0;
        int x = 150;
       /* for (Card card :playerHand) {
            JButton tempHandButton = new JButton(card.getDescription());
            myFrame.getContentPane().add(tempHandButton);
            tempHandButton.setBounds(x, 700, 180, 250);

            handButtons.add(tempHandButton);
            ImageIcon buttonIcon = null;
            switch (card.getName()) {
                case "healing bolt":
                    buttonIcon = new ImageIcon("src\\images\\test3.png");
                    break;
                case "strike":
                    buttonIcon = new ImageIcon("src\\images\\strike3.png");
                    break;
                case "shield":
                    //buttonIcon = new ImageIcon("src\\images\\strike3.png");
                    break;
                default:
                    System.out.println("drawcards icon failed");
                    break;
            }

            tempHandButton.setBorder(null);
            tempHandButton.setContentAreaFilled(false);
            if(buttonIcon != null) {
                tempHandButton.setIcon(resizeIcon(buttonIcon, tempHandButton.getWidth(), tempHandButton.getHeight()));
            }
            handButtons.get(index).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playCard(card, tempHandButton);
                }
            });
            x+= tempHandButton.getWidth()-20;
            index++;
        } */
    }

    private void playCard(Card card, JButton buttonBeingPressed) {

      /*  if(firstCombat.getPlayer().getMana() != 0) {
            this.buttonBeingPressed = buttonBeingPressed;

            for (Effect effect: card.getEffects()) {

                if(effect.isSelectTarget()) {
                    cardBeingPlayed = card;
                    targetBeingSelected = true;
                    targetLabel.setText("Choose a target");

                }else {
                    firstCombat.playEffect(effect, null);
                    updateCards();
                    update();
                }

            }

                firstCombat.getDiscardPile().add(card);
                firstCombat.getPlayer().getHand().remove(card);
                (firstCombat.getPlayer()).setMana((firstCombat.getPlayer()).getMana() - card.getCost());
            }

        else {
            System.out.println("mana is 0"); //write this out to player probs
        } */
    }
    private void endCombat() {
        System.out.println("combat ended");
    }
    private void enemyTurn() {
        /*
        //move cards to discard pile for player
        for (JButton button:handButtons) {
            myFrame.getContentPane().remove(button);
        }
        for (Card card : firstCombat.getPlayer().getHand()) {
            firstCombat.getDiscardPile().add(card);
        }
        firstCombat.getPlayer().getHand().clear();
        handButtons.clear(); */

        for (Enemy enemy : firstCombat.getEnemies()) {
            EnemyDB.doEnemyMove(firstCombat, enemy);
        }

        update();
        playerTurn();


    }
    private void playerTurn() {

      /*  firstCombat.setTurn(firstCombat.getTurn()+1);
        firstCombat.getPlayer().setMana(3); //should set to startMana variable but this works for now

        //display enemy intent
        String enemyIntentString = "<html>";
        ArrayList<Enemy> tempEnemies = firstCombat.getEnemies();
        for (int i = 0; i < tempEnemies.size(); i++) {
            Effect enemyMoveEffect = EnemyDB.getEnemyMove(firstCombat, tempEnemies.get(i), firstCombat.getTurn());

            enemyIntentString += ("<br>" + tempEnemies.get(i).getName() + " will do ");
            enemyIntentString += enemyMoveEffect.getAmount() + " " + enemyMoveEffect.getType();
        }
        enemyIntentString += "</html>";
        // enemyIntentLabel.setText(enemyIntentString);

        //display last enemy attack
        int damageDealt = 0;
        String enemyActionString = "<html>";
        for (int i = 0; i < tempEnemies.size(); i++) {
            Effect enemyMoveEffect = EnemyDB.getEnemyMove(firstCombat, tempEnemies.get(i), firstCombat.getTurn()-1);
            damageDealt = enemyMoveEffect.getAmount() - firstCombat.getPlayer().getBlock();

            enemyActionString += ("<br>" + tempEnemies.get(i).getName() + " did ");
            enemyActionString += damageDealt + " " + enemyMoveEffect.getType();
        }
        enemyActionString += "</html>";
        // enemyLastAttackLabel.setText(enemyActionString);


        drawCards(2); */

    }


    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}
