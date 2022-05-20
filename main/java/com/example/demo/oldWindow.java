package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class oldWindow {

    private JFrame myFrame;

    private ArrayList<JButton> handButtons;
    private ArrayList<JButton> enemieButtons;
    private ArrayList<JLabel> enemieLabels;

    private JLabel targetLabel;
    private JLabel enemyIntentLabel;
    private JLabel playerInfoLabel;
    private JLabel enemyLastAttackLabel;

    private JButton playerButton;
    private JButton buttonBeingPressed;
    private JButton deckButton;
    private JButton discardPileButton;

    JPanel combatPanel;
    JPanel menuPanel;
    CardLayout cl;

    private Combat firstCombat;
    boolean targetBeingSelected = false;
    private Card cardBeingPlayed;
    private ArrayList<Card> playerDeck;

    private final int maxHandSize = 8;


    public static void main(String[] args) {

        (new oldWindow()).go();
    }

    private void init() {
        firstCombat = new Combat(new String[] { "goul", "harpy"} );
        firstCombat.setTurn(1);
    }


    private void go() {
        this.init();

        myFrame = new JFrame();

        //making targetLabel
        targetLabel = new JLabel(" ");
        targetLabel.setBounds(500, 11, 200, 147);
        myFrame.getContentPane().add(targetLabel);


        //making enemy intent label
        enemyIntentLabel = new JLabel("blah");
        enemyIntentLabel.setBounds( 1100, 100, 200, 147);
        myFrame.getContentPane().add(enemyIntentLabel);

        //making enemy last attack label
        enemyLastAttackLabel = new JLabel("bleh");
        enemyLastAttackLabel.setBounds(500, 110, 200, 147);
        myFrame.getContentPane().add(enemyLastAttackLabel);


        //making deck button
        deckButton = new JButton("deck");
        deckButton.setBounds(10, 700, 100, 87);
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstCombat.getPlayer().printDeck();
            }
        });
        myFrame.getContentPane().add(deckButton);

        //making discard pile button
        discardPileButton = new JButton("discard pile");
        discardPileButton.setBounds(1500, 700, 100, 87);
        discardPileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstCombat.printDiscardPile();
            }
        });

        myFrame.getContentPane().add(discardPileButton);

        //making idk and handbuttons
        playerDeck = firstCombat.getPlayer().getDeck();
        handButtons = new ArrayList<>();


        //creating button for the player

        playerButton = new JButton();
        playerButton.setBounds(10, 200, 250, 300);

        playerInfoLabel = new JLabel("Player. HP = " + Integer.toString(firstCombat.getPlayer().getHealth())
        + " Mana = " + firstCombat.getPlayer().getMana());
        playerInfoLabel.setBounds(10, 10, 250, 300);

        ImageIcon buttonIcon = new ImageIcon("src\\images\\test5.jpg");;
        playerButton.setIcon(resizeIcon(buttonIcon, playerButton.getWidth(), playerButton.getHeight()));
        
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(targetBeingSelected) {
                    for (Effect effect : cardBeingPlayed.getEffects()) {
                        firstCombat.playEffect(effect, firstCombat.getPlayer());
                    }
                    updateCards();
                    targetBeingSelected = false;
                    targetLabel.setText(" ");
                    update();
                }

            }
        });
        
         myFrame.getContentPane().add(playerButton);
         myFrame.getContentPane().add(playerInfoLabel);
        //creating buttons for the enemies
       enemieButtons = new ArrayList<>();
       enemieLabels = new ArrayList<>();
        int i = 0;
        int y = 80;
       for (Enemy enemy : firstCombat.getEnemies()) {
           JButton tempButton = new JButton();
           JLabel tempLabel = new JLabel(enemy.getName() + ". HP =" + enemy.getHealth());

           tempLabel.setBounds(1300, y -80, 200, 147);
           tempButton.setBounds(1300, y, 200, 147);

           ImageIcon enemyIcon = new ImageIcon("src\\images\\" + enemy.getName() + ".jpg");
           tempButton.setIcon(resizeIcon(enemyIcon, tempButton.getWidth(), tempButton.getHeight()));

           tempButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   if(targetBeingSelected) {
                       for (Effect effect : cardBeingPlayed.getEffects()) {
                           firstCombat.playEffect(effect, enemy);
                       }
                       updateCards();
                       targetBeingSelected = false;
                       targetLabel.setText(" ");
                       update();
                   }

               }
           });

           enemieButtons.add(tempButton);
           enemieLabels.add(tempLabel);
           myFrame.getContentPane().add(tempLabel);
           myFrame.getContentPane().add(tempButton);


        y+=160;
        i++;
       }
       JButton endTurnButton = new JButton("End Turn");
       endTurnButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               enemyTurn();
           }
       });
       endTurnButton.setBounds(1450, 800, 200, 147);
        myFrame.getContentPane().add(endTurnButton);



        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.getContentPane().setLayout(null);



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
        enemyIntentLabel.setText(enemyIntentString);




        myFrame.setVisible(true);
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


        playerInfoLabel.setText (playerInfoString);
        updateEnemies();
       
    }
    private void updateCards() {
     myFrame.getContentPane().remove(buttonBeingPressed);
     handButtons.remove(buttonBeingPressed);
     myFrame.repaint();
    }
    private void updateEnemies() {
       
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
        myFrame.repaint();
    }
    private void drawCards(int amount) {
        //there is a bug that happens when you loop through the deck, have fun fixing that dumbass

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
        for (Card card :playerHand) {
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
        }
    }

    private void playCard(Card card, JButton buttonBeingPressed) {

        if(firstCombat.getPlayer().getMana() != 0) {
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
        }
    }
    private void endCombat() {
        System.out.println("combat ended");
    }
    private void enemyTurn() {


        //move cards to discard pile for player
        for (JButton button:handButtons) {
            myFrame.getContentPane().remove(button);
        }
        for (Card card : firstCombat.getPlayer().getHand()) {
            firstCombat.getDiscardPile().add(card);
        }
        firstCombat.getPlayer().getHand().clear();
        handButtons.clear();

        for (Enemy enemy : firstCombat.getEnemies()) {
            EnemyDB.doEnemyMove(firstCombat, enemy);
        }



        
        update();
        playerTurn();
        

    }
    private void playerTurn() {

        firstCombat.setTurn(firstCombat.getTurn()+1);
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
        enemyIntentLabel.setText(enemyIntentString);

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
        enemyLastAttackLabel.setText(enemyActionString);


        drawCards(2);

    }


    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
/*
coming up:
1.) add more cards & add enemies with movesets
2.) add mechanics: draw, block, strength
42) Should mana + hp + block +... be one label or many?
6.) add shuffle deck
11). there is still a bug with not being able to play a card dont know when somewhere when drawn many cards
12). seperate and clean up functions yeah right
13.) bug where block becomes 10 second time i use +5 block card might be related to the other bug, i think it gets called twice
14.)change playcard in combat class to castEffects
//todo fix the bug above
 */