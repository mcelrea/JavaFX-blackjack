package blackjack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Card {
    public static final int HEARTS=1,SPADES=2,CLUBS=3,DIAMONDS=4;
    private int suit;
    public static final int TWO=2,THREE=3,FOUR=4,FIVE=5,SIX=6,
                            SEVEN=7, EIGHT=8, NINE=9, TEN=10,
                            JACK=11, QUEEN=12, KING=13, ACE=14;
    private int value;
    private boolean hidden = false;

    //constructor
    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void draw(GraphicsContext gc, int x, int y) {
        String output = "";

        if(value == 11) {
            output += "J";
        }
        else if(value == 12) {
            output += "Q";
        }
        else if(value == 13) {
            output += "K";
        }
        else if(value == 14) {
            output += "A";
        }
        else {
            output += value;
        }


        if(suit == HEARTS) {
            output += "♥";
        }
        else if(suit == SPADES) {
            output += "♠";
        }
        else if(suit == DIAMONDS) {
            output += "♦";
        }
        else if(suit == CLUBS) {
            output += "♣";
        }


        //card graphic
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(x-5,y-40,85,100,10,10);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x-5,y-40,85,100,10,10);

        //drawing the value of the card
        if(suit == DIAMONDS || suit == HEARTS) {
            gc.setFill(Color.RED);
        }
        else {
            gc.setFill(Color.BLACK);
        }
        gc.fillText(output,x,y);

        if(hidden == true) {
            gc.setFill(Color.BURLYWOOD);
            gc.fillRoundRect(x-5,y-40,85,100,10,10);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
