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
    int difficulty;
    boolean won = false, containsIcon = false;
    JLabel clicked = null;
    JLabel label;
    JLabel plants;
    static ArrayList<Coordination> objects = new ArrayList<>();



    public static void shoot(Plant shooterPlant, boolean isFrozen) {
        int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
        if (pos != null) {
            JLabel pea = new JLabel();
            pea.setIcon(new ImageIcon("gfx/bullet.pvz"));
            pea.setBounds(pos[0], pos[1], 28, 28);
            //TODO:label.add()
        } else System.out.println("goto bokhor");
    }


    private  MouseListener labelClickListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if (containsIcon) {
                    int[] position = Sluts.getSlut(getMousePosition(true));
                    int[] location = Sluts.getPlantLocation(position[0], position[1]);
                    if (isEmptySlut(position[0], position[1])) {
                        label.removeMouseMotionListener(motionListener());
                        containsIcon = false;
                        if (TESTING) System.out.println("Clicked Slut " + Arrays.toString(position));
                        //if (TESTING) System.out.println("Cursor Icon: " + clicked.getIcon().toString());
                        Plant tmp;
                        int i;
                        switch (clicked.getIcon().toString()) {
                            case "gfx/sunflower.pvz" -> {
                                tmp = new SunFlower(label, position);
                                i = 0;
                            }
                            case "gfx/pea.pvz" -> {
                                tmp = new PeaShooter(label, position);
                                i = 1;
                            }
                            case "gfx/snowPea.pvz" -> {
                                tmp = new SnowPea(label, position);
                                i = 2;
                            }
                            case "gfx/nut_1.pvz" -> {
                                tmp = new wallNut(label, position);
                                i = 3;
                            }
                            case "gfx/cherry.pvz" -> {
                                tmp = new Cherry(label, position);
                                i = 4;
                            }
                            case "gfx/repeater.pvz" -> {
                                tmp = new Repeater(label, position);
                                i = 5;
                            }
                            default -> throw new RuntimeException("Error in labelClickListener switch");
                        }

                        tmp.setBounds(location[0], location[1], 100, 100);
                        objects.add(new Coordination(tmp, position[0], position[1]));
                        clicked.setIcon(null);

                        if (tmp.getClass() == PeaShooter.class) shoot(tmp, false);
                        else if (tmp.getClass() == SnowPea.class) shoot(tmp, true);

                    }
                }
            }

            private boolean isEmptySlut(int x, int y) {
                boolean isEmpty = true;
                for (Coordination object : objects) {
                    if (object.coordination[0] == x)
                        if (object.coordination[1] == y) {
                            isEmpty = false;
                            break;
                        }
                }
                return isEmpty;
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    private MouseListener cardsClickListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object source = e.getSource();
                if (source.toString().contains("cardSun.pvz")) clicked.setIcon(new ImageIcon("gfx/sunflower.pvz"));
                else if (source.toString().contains("cardPea.pvz")) clicked.setIcon(new ImageIcon("gfx/pea.pvz"));
                else if (source.toString().contains("cardFreeze.pvz")) clicked.setIcon(new ImageIcon("gfx/snowPea.pvz"));
                else if (source.toString().contains("cardWallnut.pvz")) clicked.setIcon(new ImageIcon("gfx/nut_1.pvz"));
                else if (source.toString().contains("cardCherry.pvz")) clicked.setIcon(new ImageIcon("gfx/cherry.pvz"));
                else System.out.println("Error In cardsClickListener");

                if (!containsIcon) {
                    containsIcon = true;
                    label.addMouseMotionListener(motionListener());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    private MouseMotionListener motionListener() {
        return new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me)
            {
                clicked.setBounds( me.getX() - 50, me.getY() - 50, 100, 100);
                clicked.repaint();
            }
        };
    }

    private void backgrounds() {
        label = new JLabel();// Setting the background
        label.setIcon(new ImageIcon("gfx/back.pvz"));
        label.setBounds(0, 0, 1400, 600);
        add(label);
        plants = new JLabel();
        plants.setBounds(200, 0,542, 106);
        clicked = new JLabel();
        label.add(clicked);
        label.add(plants);
        label.addMouseListener(labelClickListener());
    }

    private void readySetPlant() {

    }
    /**
     * This methods place some random number of zombies in the road. Just before the game starts.
     * @param label the container that we want to place the zombies (default is the background label.)
     */
    private void placeRandomZombies(Container label) {
        new Thread(() -> {//1017, 84
            Random random = new Random();
            int count = random.nextInt(10 * difficulty + 1);
            IntStream iX = random.ints(count, 1017, 1400);
            int[] posX = iX.toArray();
            IntStream iY = random.ints(count, 84, 600);
            int[] posY = iY.toArray();
            JLabel[] l1 = new JLabel[count];
            for (int i = 0; i < count; i++) {
                l1[i] = new JLabel();
                l1[i].setIcon(new ImageIcon("gfx/zombies.pvz"));
                label.add(l1[i]);
                l1[i].setBounds(posX[i], posY[i], 62, 100);
            }
            try {
                Thread.sleep(3000);
                for (int i = 0; i < count; i++) {
                    remove(l1[i]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
    private void sendZombies(Container label, int round) {
        int ss;
        if (round == 1) ss = 5;
        else ss = 12;
        Random random = new Random();
        new Thread(() -> {
            try {
                if (round == 1) Thread.sleep(gap * 1000L);
                int[] location;
                for (int i = 0; i < ss; i++) {
                    Normal normal;
                    normal = new Normal(label);
                    location = Sluts.getZombieLocation(random.nextInt(5) + 1);
                    normal.setBounds(location[0], location[1] - 40, 31, 180);
                    Timer t = new Timer(120, e ->
                            normal.setBounds(normal.getX() - 1, normal.getY(), 81, 130));
                    t.start();
                    if (round == 1)  Thread.sleep(30000);
                    else if (round == 3)  Thread.sleep(25000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void readyLabel() throws InterruptedException {
        JLabel start = new JLabel();
        //noinspection SpellCheckingInspection
        start.setFont(new Font("Segoe Script", Font.BOLD, 70));
        start.setForeground(Color.red);
        start.setHorizontalAlignment(SwingConstants.CENTER);
        start.setBounds(360, 120, 300, 300);
        label.add(start);
        start.setText("Ready?");
        Thread.sleep(600);
        start.setText("Set");
        Thread.sleep(600);
        start.setText("Plant!");
        Thread.sleep(800);
        start.setText("");
    }



}
