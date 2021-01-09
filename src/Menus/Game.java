package Menus;

import Miscs.*;
import Objects.Plants.*;
import Objects.Zombies.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;


import static Main.Main.TESTING;
import static Miscs.Sounds.*;

/**
 * This class shows the In game structure:
 *
 * DONE: Background Sound Effect,
 *       Main Frame,
 *       Peashooter Spawn,
 *       Zombies Spawn,
 *       first animation,
 *       leveling system,
 *       sluts,
 *       sounds,
 *       more mobs
 *       plant menu
 *
 * TODO: landing shots,
 *       kill mobs,
 *       shoot, grunt, walk, land, mower, win, lose, click, dig sfx,
 *       load and save,
 *       options,
 *       main menu,
 *       mower,
 *       and...
 *
 */

public class Game extends JFrame {
    Zombie[] firstInRow = new Zombie[5];
    int gap = 5, suns = 25;
    boolean won = false, containsIcon = false;
    JLabel clicked = null;
    JLabel label;
    JLabel plants;
    static ArrayList<Coordination> objects = new ArrayList<>();




    public static void shoot(Plant shooterPlant, boolean isFrozen) {

    }


    private  MouseListener labelClickListener() {


            private boolean isEmptySlut(int x, int y) {

    }

    private MouseListener cardsClickListener() {

    }

    private MouseMotionListener motionListener() {

    }

    private void backgrounds() {


    }

    private void readySetPlant() {

    }

    private void readyLabel() throws InterruptedException {

    }



}
