package blackjack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    ArrayList<Card> deck = new ArrayList<Card>();
    Font defaultFont;
    ArrayList<Card> playerHand = new ArrayList<Card>();
    int playerHandValue;
    ArrayList<Card> bot1Hand = new ArrayList<Card>();
    int bot1HandValue;
    ArrayList<Card> bot2Hand = new ArrayList<Card>();
    int bot2HandValue;
    ArrayList<Card> bot3Hand = new ArrayList<Card>();
    int bot3HandValue;
    ArrayList<Card> bot4Hand = new ArrayList<Card>();
    int bot4HandValue;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Blackjack");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(800,600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font myFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(myFont);

        createDeck();
        shuffle();
        firstDeal();
        drawHands(gc);

        //last line of code in start method
        primaryStage.show();
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
        playerHand.get(0).draw(gc,300,50);
        playerHand.get(1).draw(gc,400,50);

        bot1Hand.get(0).draw(gc,600,250);
        bot1Hand.get(1).draw(gc,700,250);

        bot2Hand.get(0).draw(gc,450,500);
        bot2Hand.get(1).draw(gc,550,500);

        bot3Hand.get(0).draw(gc,150,500);
        bot3Hand.get(1).draw(gc,250,500);

        bot4Hand.get(0).draw(gc,50,250);
        bot4Hand.get(1).draw(gc,150,250);
    }

    private void firstDeal() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        Card card1 = playerHand.get(0);
        Card card2 = playerHand.get(1);
        playerHandValue = card1.getValue() + card2.getValue();

        bot1Hand.add(deck.remove(0));
        bot1Hand.add(deck.remove(0));
        card1 = bot1Hand.get(0);
        card2 = bot1Hand.get(1);
        bot1HandValue = card1.getValue() + card2.getValue();

        bot2Hand.add(deck.remove(0));
        bot2Hand.add(deck.remove(0));
        card1 = bot2Hand.get(0);
        card2 = bot2Hand.get(1);
        bot2HandValue = card1.getValue() + card2.getValue();

        bot3Hand.add(deck.remove(0));
        bot3Hand.add(deck.remove(0));
        card1 = bot3Hand.get(0);
        card2 = bot3Hand.get(1);
        bot3HandValue = card1.getValue() + card2.getValue();

        bot4Hand.add(deck.remove(0));
        bot4Hand.add(deck.remove(0));
        card1 = bot4Hand.get(0);
        card2 = bot4Hand.get(1);
        bot4HandValue = card1.getValue() + card2.getValue();
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
