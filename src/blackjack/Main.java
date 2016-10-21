package blackjack;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    ArrayList<Card> deck = new ArrayList<Card>();
    Font defaultFont;

    ArrayList<Card> playerHand = new ArrayList<Card>();
    int playerHandValue;
    boolean playerHandDone = false;
    boolean playerWon = false;
    int playerMoney = 50;

    ArrayList<Card> bot1Hand = new ArrayList<Card>();
    int bot1HandValue;
    boolean bot1HandDone = false;
    boolean bot1Won = false;
    int bot1Money = 50;

    ArrayList<Card> bot2Hand = new ArrayList<Card>();
    int bot2HandValue;
    boolean bot2HandDone = false;
    boolean bot2Won = false;
    int bot2Money = 50;

    ArrayList<Card> bot3Hand = new ArrayList<Card>();
    int bot3HandValue;
    boolean bot3HandDone = false;
    boolean bot3Won = false;
    int bot3Money = 50;

    ArrayList<Card> bot4Hand = new ArrayList<Card>();
    int bot4HandValue;
    boolean bot4HandDone = false;
    boolean bot4Won = false;
    int bot4Money = 50;

    long winnerDuration = 4000; //1000 = 1 second
    long winnerTimeStamp;

    ArrayList<String> input = new ArrayList<String>();
    public static final int FIRSTDEAL=1, PLAYING=2;
    int gameState = FIRSTDEAL;
    String status = "Press 'H' for hit, 'S' for stay";
    Font cardFont, textFont;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Blackjack");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(800,600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        cardFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        textFont = Font.font("Times New Roman", FontWeight.BOLD, 24);
        gc.setFont(cardFont);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                if(!input.contains(code))
                    input.add(code);
                System.out.println(input);
            }
        });

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                //clear screen
                gc.setFill(Color.DARKGREEN);
                gc.fillRect(0,0,800,600);

                if(gameState == FIRSTDEAL) {
                    createDeck();
                    shuffle();
                    resetHands();
                    firstDeal();
                    drawHands(gc);
                    drawWinners(gc);
                    gameState = PLAYING;
                }
                else if(gameState == PLAYING) {
                    processUserInput();
                    drawHands(gc);
                    if(playerHandDone && bot1HandDone && bot2HandDone &&
                            bot3HandDone && bot4HandDone) {
                        gameState = FIRSTDEAL;
                        determineWinners();
                    }
                    drawWinners(gc);
                }
            }
        }.start();

        //last line of code in start method
        primaryStage.show();
    }

    private void determineWinners() {
        if (playerHandValue >= bot4HandValue && playerHandValue <= 21) {
            playerWon = true;
            playerMoney += 6;
        }
        if (bot1HandValue >= bot4HandValue && bot1HandValue <= 21) {
            bot1Won = true;
            bot1Money += 6;
        }
        if (bot2HandValue >= bot4HandValue && bot2HandValue <= 21) {
            bot2Won = true;
            bot2Money += 6;
        }
        if (bot3HandValue >= bot4HandValue && bot3HandValue <= 21) {
            bot3Won = true;
            bot3Money += 6;
        }
        if(bot4HandValue > 21) {
            if(playerHandValue <= 21) {
                playerWon = true;
                playerMoney += 6;
            }
            if(bot1HandValue <= 21) {
                bot1Won = true;
                bot1Money += 6;
            }
            if(bot2HandValue <= 21) {
                bot2Won = true;
                bot2Money += 6;
            }
            if(bot3HandValue <= 21) {
                bot3Won = true;
                bot3Money += 6;
            }
        }
        winnerTimeStamp = System.currentTimeMillis();
    }


    private void drawWinners(GraphicsContext gc) {
        if(System.currentTimeMillis() < winnerTimeStamp + winnerDuration) {
            gc.setFill(Color.RED);
            if (playerWon) {
                gc.fillText("Winner", 350, 150);
            }
            if (bot1Won) {
                gc.fillText("Winner", 650, 350);
            }
            if (bot2Won) {
                gc.fillText("Winner", 500, 600);
            }
            if (bot3Won) {
                gc.fillText("Winner", 200, 600);
            }
        }
    }


    private void processUserInput() {
        for(int i=0; i < input.size(); i++) {
            if(input.get(i).equals("SPACE")) {
                gameState = FIRSTDEAL;
                //clear out input, I processed it
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("H")) {
                playerHit();
                //clear out input, I processed it
                input.remove(i);
                i--;
                robotsTurn();
            }
            else if(input.get(i).equals("S")) {
                playerHandDone = true;
                //clear out input, I processed it
                input.remove(i);
                i--;
                robotsTurn();
            }
        }
    }

    private void robotsTurn() {
        if(bot1HandValue <= 16) {
            bot1Hand.add(deck.remove(0));
            bot1HandValue = computeHandValue(bot1Hand);
        }
        else {
            bot1HandDone = true;
        }
        if(bot2HandValue <= 16) {
            bot2Hand.add(deck.remove(0));
            bot2HandValue = computeHandValue(bot2Hand);
        }
        else {
            bot2HandDone = true;
        }
        if(bot3HandValue <= 16) {
            bot3Hand.add(deck.remove(0));
            bot3HandValue = computeHandValue(bot3Hand);
        }
        else {
            bot3HandDone = true;
        }
        if(bot4HandValue <= 16) {
            bot4Hand.add(deck.remove(0));
            bot4HandValue = computeHandValue(bot4Hand);
        }
        else {
            bot4HandDone = true;
        }
    }

    private void playerHit() {
        playerHand.add(deck.remove(0));
        playerHandValue = computeHandValue(playerHand);
    }

    private void resetHands() {
        playerHand.clear();
        playerHandValue=0;
        playerHandDone = false;
        bot1Hand.clear();
        bot1HandValue=0;
        bot1HandDone = false;
        bot2Hand.clear();
        bot2HandValue=0;
        bot2HandDone = false;
        bot3Hand.clear();
        bot3HandValue=0;
        bot3HandDone = false;
        bot4Hand.clear();
        bot4HandValue=0;
        bot4HandDone = false;
    }

    public void shuffle() {
        for(int i=0; i < deck.size(); i++) {
            int randomLoc = (int) (Math.random() * 52);
            Card temp = deck.get(randomLoc);

            //swap
            deck.set(randomLoc,deck.get(i));
            deck.set(i, temp);
        }
    }

    private void drawHands(GraphicsContext gc) {
        gc.setFont(cardFont);

        //player hand
        int xoffset = 250;
        int yoffset = 50;
        int buffer = 65;
        for(int i=0; i < playerHand.size(); i++) {
            playerHand.get(i).draw(gc,xoffset+(i*buffer),yoffset);
        }
        playerHandValue = computeHandValue(playerHand);
        gc.setFill(Color.GOLD);
        gc.fillText("" + playerMoney, 350,100);

        //bot1 hand
        xoffset = 550;
        yoffset = 250;
        for(int i=0; i < bot1Hand.size(); i++) {
            bot1Hand.get(i).draw(gc,xoffset+(i*buffer),yoffset);
        }
        bot1HandValue = computeHandValue(bot1Hand);
        gc.setFill(Color.GOLD);
        gc.fillText("" + bot1Money, 650,300);

        //bot2 hand
        xoffset = 400;
        yoffset = 500;
        for(int i=0; i < bot2Hand.size(); i++) {
            bot2Hand.get(i).draw(gc,xoffset+(i*buffer),yoffset);
        }
        bot2HandValue = computeHandValue(bot2Hand);
        gc.setFill(Color.GOLD);
        gc.fillText("" + bot2Money, 500,550);

        //bot3 hand
        xoffset = 100;
        yoffset = 500;
        for(int i=0; i < bot3Hand.size(); i++) {
            bot3Hand.get(i).draw(gc,xoffset+(i*buffer),yoffset);
        }
        bot3HandValue = computeHandValue(bot3Hand);
        gc.setFill(Color.GOLD);
        gc.fillText("" + bot3Money, 200,550);

        //bot4 hand
        xoffset = 50;
        yoffset = 250;
        for(int i=0; i < bot4Hand.size(); i++) {
            bot4Hand.get(i).draw(gc,xoffset+(i*buffer),yoffset);
        }
        bot4HandValue = computeHandValue(bot4Hand);
        gc.setFill(Color.GOLD);
        //gc.fillText("" + bot4Money, 100,300);

        gc.setFont(textFont);
        gc.setFill(Color.BLACK);
        gc.fillText(status,250,300);
    }

    public int computeHandValue(ArrayList<Card> hand) {
        int value = 0;
        boolean haveAce = false;
        for(int i=0; i < hand.size(); i++) {
            Card currentCard = hand.get(i);
            if(currentCard.getValue() == Card.JACK ||
                    currentCard.getValue() == Card.QUEEN ||
                    currentCard.getValue() == Card.KING) {
                value += 10;
            }
            else if(currentCard.getValue() == Card.ACE) {
                value += 11;
                haveAce = true;
            }
            else {
                value += currentCard.getValue();
            }
        }

        if(value > 21 && haveAce) {
            value -= 10;
        }

        return value;
    }

    private void firstDeal() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        playerMoney -= 2;

        bot1Hand.add(deck.remove(0));
        bot1Hand.add(deck.remove(0));
        bot1Money -= 2;

        bot2Hand.add(deck.remove(0));
        bot2Hand.add(deck.remove(0));
        bot2Money -= 2;

        bot3Hand.add(deck.remove(0));
        bot3Hand.add(deck.remove(0));
        bot3Money -= 2;

        bot4Hand.add(deck.remove(0));
        bot4Hand.add(deck.remove(0));
        bot4Hand.get(0).setHidden(true);
    }

    private void createDeck() {
        int counter = 0;
        for(int suit=1; suit <= 4; suit++) {
            for(int value=2; value <= 14; value++) {
                deck.add(new Card(suit, value));
                //deck[counter] = new Card(suit,value);
                counter++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
