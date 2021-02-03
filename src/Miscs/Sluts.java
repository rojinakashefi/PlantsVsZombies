package Miscs;

import java.awt.Rectangle;
import java.awt.Point;


/**
 * Sluts class sets the position of the sluts in the checkered ground of the game.
 * @author Hashemipour
 * @since 2021
 */

public class Sluts {
    static final int FIRST_X = 260, FIRST_Y = 80, X = 10, Y = 5;
    static int[][][] sluts = new int[X][Y][2];

    /**
     *This method sets the position of each point(sluts) of the game
     */
    public static void setSluts() {
        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++) {
                sluts[i][j][0] = FIRST_X + i * 80;
                sluts[i][j][1] = FIRST_Y + j * 100;
            }
    }

    /**
     * This method gets the plant slut by the given location.
     * @param X Horizontal slut of the plant.
     * @param Y Vertical slut of the plant.
     * @return the location.
     */
    public static int[] getPlantLocation(int X, int Y) {
        return sluts[X][Y];
    }

    /**
     * This method gets the zombie slut by the given location.
     * @param Y  y position of zombie.
     * @return   the location of zombie.
     */
    public static int[] getZombieLocation(int Y) {
        return sluts[9][Y];
    }

    /**
     * This method get the right position of the object.
     * @param posX x position of object.
     * @param posY y position of object.
     * @return     the position or null if there is nothing.
     */
    public static int[] getSlut(int posX, int posY) {
        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++) {
                if (posX >= sluts[i][j][0] && posX < sluts[i][j][0] + 80
                        && posY >= sluts[i][j][1] && posY < sluts[i][j][1] + 100) {
                    return new int[]{i, j};
                }
            }
        return null;
    }

    /**
     * This method get the sluts of each object that we clicked.
     * @param p the point.
     * @return the x and y position .
     */
    public static int[] getSlut(Point p) {
        return getSlut(p.x, p.y);
    }

    /**
     * This method get the sluts of each object.
     * @param R the rectangle.
     * @return  the y position or -1 if there is nothing.
     */
    public static int getYSlut(Rectangle R) {
        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++) {
                if (R.y >= sluts[i][j][1] && R.y < sluts[i][j][1] + 100) {
                    return j;
                }
            }
        return -1;
    }

    /**
     * This method get the location of each mower.
     * @param YSlut y position of mower.
     * @return position of mower.
     */
    public static int getMowerLocation(int YSlut) {
        return sluts[0][YSlut][1];
    }

    static int[][][] cardSluts = new int[6][2][2];
    static final int FIRST_CARD_X = 4, FIRST_CARD_Y = 8;//position of each card

    /**
     * This method sets the position of each card in the label.
     */
    public static void setCardSluts() {
        for (int i = 0; i < cardSluts.length; i++) {
            for (int j = 0; j < cardSluts[i].length; j++) {
                cardSluts[i][j][0] = FIRST_CARD_X + i * 53;
                cardSluts[i][j][1] = FIRST_CARD_Y + j * 75;
            }
        }
    }

    /**
     * This method get the sluts of cards ( rectangle ).
     * @param i the index of the card.
     * @return the card .
     */
    public static Rectangle getCardSlut(int i) {
        int j = 0;
        if (i >= 6) {
            i -= 6;
            j++;
        }
        return new Rectangle(cardSluts[i][j][0], cardSluts[i][j][1], 50, 72);
    }

    /**
     * This method get the position of cards ( rectangle ).
     * @param i the index of the card.
     * @return the card position.
     */
    public static Rectangle getCardPos(int i) {
        int x = 85 + i * 60;
        return new Rectangle(x, 8, 50, 72);
    }
}
