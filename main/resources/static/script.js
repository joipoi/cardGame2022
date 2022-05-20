var cardDiv;
var cardElementsArray;

var combat;
var deck;
var hand;
var enemies;
var player;
var targetBeingSelected;
var cardBeingPlayed;
var cardElementBeingPlayed;

function init(firstCombat) {
combat = firstCombat;

deck = combat.player.deck;
hand = combat.player.hand;
enemies = combat.enemies;
player = combat.player;
targetBeingSelected = false

drawCards(3);
console.log(JSON.parse(JSON.stringify(combat)))
generateEnemies();
generatePlayer();

cardElementsArray = Array.from(cardDiv.children);

}

function drawCards(amount) {

cardDiv = document.getElementById('cardDiv');

for (let i = 0; i < amount; i++) {

    var imgString = '/images/';
    var img = document.createElement('img');

    switch(deck[0].name) {
           case "healing bolt":
                             imgString+= 'heal.jpg';
                             break;
           case "strike":
                            imgString+= 'sword.png';
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

var enemyDiv = document.getElementById('enemiesDiv');

for (let i = 0; i < enemies.length; i++) {

    var imgString = '/images/';
    var img = document.createElement('img');

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
                    playEffect(cardBeingPlayed.effects[i], enemies[j]);
                }
                combat.discardPile.push(cardBeingPlayed);
                      player.mana = player.mana - cardBeingPlayed.cost;
                      cardElementBeingPlayed.remove();
                      hand.splice(hand.indexOf(cardBeingPlayed), 1);
                       document.getElementById('backgroundDiv').innerHTML = " ";
                      updateCards();
            }



         });

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
         var test = img.id;
           document.getElementById("backgroundDiv").innerHTML = "player was clicked with health : " + player.health;  ;
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
                        player.mana = player.mana - card.cost;
                        cardElement.remove();
                        hand.splice(cardIndex, 1);
                        updateCards();

        }






    }else {
        console.log("no mana");
    }

    console.log(JSON.parse(JSON.stringify(combat)))


}
function playEffect(effect, target) {


}
function updateCards() {
cardElementsArray = Array.from(cardDiv.children);


}


//use addevent listener over onclick
//maybe use create elemnt and then add src if you can
//maybe java just sends firstCombat object and then everything is done in javascript
//look back at the old java spring web tutorial for sending objectts