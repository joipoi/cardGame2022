/* todo add mana cost, add enemy intents/hp/attack, add player hp + block + mana, add end turn, add show deck
*/


var cardDiv;
var cardElementsArray;

var combat;
var deck;
var hand;
var enemies;
var player;
var discardPile;
var turn;
var mana;

var targetBeingSelected;
var cardBeingPlayed;
var cardElementBeingPlayed;
var backgroundDiv;
var enemyDiv;
var playerDiv;
var enemyTarget;

function init(firstCombat) {
combat = firstCombat;

deck = combat.player.deck;
hand = combat.player.hand;
enemies = combat.enemies;
player = combat.player;
discardPile = combat.discardPile;
turn = combat.turn;
mana = player.mana;

cardDiv = document.getElementById('cardDiv');
enemyDiv = document.getElementById('enemiesDiv');
playerDiv = document.getElementById('playerDiv');

targetBeingSelected = false

document.getElementById("endTurnBtn").addEventListener("click", function() {
    enemyTurn()});

document.getElementById("playerInfo").innerHTML = "health: " + player.health + ", block:" + player.block + ", mana: " + player.mana;


document.getElementById("deckImg").addEventListener("click", function() {
                                      showDeck()});
backgroundDiv = document.getElementById('backgroundDiv');


console.log(JSON.parse(JSON.stringify(combat)))
generateEnemies();
generatePlayer();

cardElementsArray = Array.from(cardDiv.children);

turn = 1;
showEnemyIntent();
drawCards(3);
update();



}

function drawCards(amount) {

for (let i = 0; i < amount; i++) {

    var imgString = '/images/';
    var img = document.createElement('img');

    switch(deck[0].name) {
           case "healing bolt":
                             imgString+= 'healingBolt.png';
                             break;
           case "strike":
                            imgString+= 'strike.png';
                            break;
           case "shield":
                            imgString+= 'shield.png';
                            break;
           default:
                            console.log("drawcards icon failed");
                            break;
    }


         img.id = 'card' + i;
         img.src = imgString;

         hand.push(deck.shift()); //pop might me the wrong end of the deck

         img.addEventListener("click", function() {
            playCard(this);

         });


         cardDiv.appendChild(img);


    }

}

function generateEnemies() {



for (let i = 0; i < enemies.length; i++) {

    var imgString = '/images/';
    var img = document.createElement('img');
    var enemyStatsP = document.createElement('p');
    var enemyIntentP = document.createElement('p');;

    switch(enemies[i].name) {
           case "goul":
                             imgString+= 'goul.png';
                             break;
           case "harpy":
                            imgString+= 'harpy.jpg';
                            break;
           default:
                            console.log("enemy icon failed");
                            break;
    }


         img.id = 'enemy' + i;
         img.src = imgString;


         img.addEventListener("click", function() {
            if(targetBeingSelected) {
                for (var j = 0; j < cardBeingPlayed.effects.length; j++) {
                    playEffect(cardBeingPlayed.effects[j], enemies[i]);
                }
                combat.discardPile.push(cardBeingPlayed);
                      mana = mana - cardBeingPlayed.cost;
                      cardElementBeingPlayed.remove();
                      hand.splice(hand.indexOf(cardBeingPlayed), 1);
                      updateCards();
            }



         });
         enemyDiv.appendChild(enemyStatsP);
         enemyDiv.appendChild(enemyIntentP);
         enemyDiv.appendChild(img);




    }
}

function generatePlayer() {

var playerDiv = document.getElementById('playerDiv');

    var imgString = '/images/player.jpg';
      var img = document.createElement('img');

         img.id = 'player';
         img.src = imgString;


         img.addEventListener("click", function() {
         if(targetBeingSelected) {
                        for (var j = 0; j < cardBeingPlayed.effects.length; j++) {
                            playEffect(cardBeingPlayed.effects[j], player);
                        }
                        combat.discardPile.push(cardBeingPlayed);
                              mana = mana - cardBeingPlayed.cost;
                              cardElementBeingPlayed.remove();
                              hand.splice(hand.indexOf(cardBeingPlayed), 1);
                              updateCards();
                    }
         });

         playerDiv.appendChild(img);

}

function playCard(cardElement) {
cardElementBeingPlayed = cardElement;
var cardIndex = Array.prototype.indexOf.call(cardDiv.children, cardElement);
var card = hand[cardIndex];




    if(player.mana != 0) {
        for(var i = 0; i < card.effects.length; i++) {
            if(card.effects[i].selectTarget) {
                  cardBeingPlayed = card;
                  targetBeingSelected = true;
                  document.getElementById('backgroundDiv').innerHTML = "choose a target";
                  break;
            }

                playEffect(card.effects[i], null);
                  combat.discardPile.push(card);
                        mana = mana - card.cost;
                        cardElement.remove();
                        hand.splice(cardIndex, 1);
                        updateCards();

        }






    }else {
        console.log("no mana");
    }

    console.log(JSON.parse(JSON.stringify(combat)))
 update();

}
function playEffect(effect, target) {

var outputString = "";

     if(target != null) {
                    switch(effect.type) {
                        case "damage":
                            if(target.block < effect.amount) {
                            outputString = target.name + " took " + (effect.amount - target.block) + " damage";
                                target.health = (target.health - (effect.amount - target.block));
                                if(target.block - effect.amount < 0) {
                                   target.block = 0;
                                }else {
                                      target.block = (target.block - effect.amount);
                                }
                            } else {
                            target.block = (target.block - effect.amount);
                            outputString = target.name + " took " + " 0 " + " damage";

                            }


                            break;
                        case "healing":
                            target.health = (target.health + effect.amount);
                            outputString = target.name + " healed for " + effect.amount + " damage";
                            break;
                        default:
                            System.out.println("wrong effect1");
                    }

            }else {
                    switch(effect.type) {
                        case "block":
                            player.block = (player.block + effect.amount);
                            outputString = "you blocked for " + effect.amount;
                            break;
                        case "draw":

                            break;
                        default:
                            System.out.println("wrong effect2");
                    }


            }

backgroundDiv.innerHTML = outputString;
 update();

}
function updateCards() {
cardElementsArray = Array.from(cardDiv.children);
update();


}
function enemyTurn() {
        discardPile = discardPile.concat(hand);

        while(hand.length > 0) {
         hand.pop();
        }
        cardDiv.innerHTML = "";
        for (var i = 0; i < enemies.length; i++) {
            doEnemyMove(enemies[i]);
        }
        update();
        playerTurn();




}
function playerTurn() {
update();
       turn++;
       mana = 3; //should set to startMana variable but this works for now

       showEnemyIntent();

        //display last enemy attack
        var damageDealt = 0;
        var enemyActionString = "";
        for (var i = 0; i < enemies.length; i++) {
           var enemyMoveEffect = getEnemyMove(enemies[i], turn-1);

            damageDealt = enemyMoveEffect.amount - player.block;

            enemyActionString += ("<br> " + enemies[i].name + " did ");
            enemyActionString += damageDealt + " " + enemyMoveEffect.type;
        }
        backgroundDiv.innerHTML += enemyActionString;

        drawCards(2);

}
function update() {
document.querySelectorAll('#enemiesDiv p')[0].innerHTML = "Name: Goul, Health: " + enemies[0].health; //will ahve to fix later
document.querySelectorAll('#enemiesDiv p')[2].innerHTML = "Name: Harpy, Health: " + enemies[1].health;

playerDiv.children[0].innerHTML = "Health: " + player.health + ", block: " + player.block + ", mana: " + mana;

}
function showDeck() {
document.getElementById("popUpDiv").style.display = "block";


for (let i = 0; i < deck.length; i++) {

    var imgString = '/images/';
    var img = document.createElement('img');

    switch(deck[i].name) {
           case "healing bolt":
                             imgString+= 'healingBolt.png';
                             break;
           case "strike":
                            imgString+= 'strike.png';
                            break;
           case "shield":
                            imgString+= 'shield.png';
                            break;
           default:
                            console.log("drawcards icon failed");
                            break;
    }

         img.src = imgString;


         popUpDiv.appendChild(img);


    }
    document.getElementById("popUpBtn").addEventListener("click", function() {
    document.getElementById("popUpDiv").style.display = "none";
                                           });
}
function getEnemyMove(enemy, turn) {

var effectToPlay = {};
        switch (enemy.name) {
            case "harpy":
                if(turn % 2 == 1) {
                    effectToPlay =  {type:"damage", amount:5, selectTarget:true};
                    enemyTarget = player;
                }else {

                     effectToPlay =  {type:"healing", amount:5, selectTarget:true};
                    enemyTarget = enemy;
                }
                break;
            case "goul":
                if(turn < 3) {
                    effectToPlay =  {type:"damage", amount:4, selectTarget:true};
                     enemyTarget = player;
                }else {
                    effectToPlay =  {type:"damage", amount:9, selectTarget:true};
                     enemyTarget = player;
                }
                break;
            case "snake":
                if(enemy.getRandNum() == 1) {
                   effectToPlay =  {type:"damage", amount:6, selectTarget:true};
                 enemyTarget = player;
                } else if(enemy.getRandNum() == 2) {
                    effectToPlay =  {type:"damage", amount:7, selectTarget:true};
                    enemyTarget = player;
                }
                break;
            default:
                System.out.println("something went wrong in enemyDB");


        }
        return effectToPlay;



}
function doEnemyMove(enemy) {
 playEffect(getEnemyMove(enemy, turn), enemyTarget);
}
function showEnemyIntent() {
 for (var i = 0; i < enemies.length; i++) {
            var enemyMoveEffect = getEnemyMove(enemies[i], turn);
            var enemyIntentString = "";


            enemyIntentString += (enemies[i].name + " will do ");
            enemyIntentString += enemyMoveEffect.amount + " " + enemyMoveEffect.type;

            if(i == 0) {
             document.querySelectorAll('#enemiesDiv p')[1].innerHTML = enemyIntentString;
            }
            if(i == 1) {
             document.querySelectorAll('#enemiesDiv p')[3].innerHTML = enemyIntentString
            }


        }
}