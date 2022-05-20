package com.example.demo;
public class EnemyDB {
    String name;
    int startingHealth;
    Card enemyMove;
    private static Character target = new Enemy("bleh", 0);;



    public static Effect getEnemyMove(Combat firstCombat, Enemy enemy, int turn) {
        Effect effectToPlay = new Effect("temp",5);
        switch (enemy.getName()) {
            case "harpy":
                if(turn % 2 == 1) {
                    effectToPlay = new Effect("damage", 5);
                    target = firstCombat.getPlayer();
                }else {

                    effectToPlay = new Effect("healing", 5);
                    target = enemy;
                }
                break;
            case "goul":
                if(turn < 3) {
                    effectToPlay = new Effect("damage", 4);
                    target = firstCombat.getPlayer();
                }else {
                    effectToPlay = new Effect("damage", 9);
                    target = firstCombat.getPlayer();
                }
                break;
            case "snake":
                if(enemy.getRandNum() == 1) {
                    effectToPlay = new Effect("damage", 6);
                    target = firstCombat.getPlayer();
                } else if(enemy.getRandNum() == 2) {
                    effectToPlay = new Effect("damage", 7);
                    target = firstCombat.getPlayer();
                }
                break;
            default:
                System.out.println("something went wrong in enemyDB");


        }
        return effectToPlay;


    }
    public static void doEnemyMove(Combat firstCombat, Enemy enemy) {
        firstCombat.playEffect(getEnemyMove(firstCombat, enemy, firstCombat.getTurn()), target);
    }

}
