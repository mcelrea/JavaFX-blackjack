package blackjack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application {

    ArrayList<Card> deck = new ArrayList<Card>();
    Font defaultFont;
    ArrayList<Card> playerHand = new ArrayList<Card>();

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
        deal();
        drawHands(gc);

        //last line of code in start method
        primaryStage.show();
    }

    private void drawHands(GraphicsContext gc) {
        playerHand.get(0).draw(gc,300,50);
        playerHand.get(1).draw(gc,400,50);
    }

    private void deal() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
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
