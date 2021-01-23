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





    private void shoot(Plant shooterPlant, boolean isFrozen) {
        new Thread( () -> {
            if (shooterPlant.health > 0) {
                if (TESTING)
                    System.out.println("Shooter Position For pea: " + shooterPlant.getBounds().x + " " + shooterPlant.getBounds().y);
                int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
                if (pos != null) {
                    do {
                        try {
                            PeaBullet pea = new PeaBullet(label, shooterPlant, isFrozen);
                            if (isFrozen) pea.setIcon(new ImageIcon("gfx/snowBullet.pvz"));
                            else pea.setIcon(new ImageIcon("gfx/bullet.pvz"));
                            pea.setBounds(shooterPlant.getBounds().x + 46, shooterPlant.getBounds().y + 16, 28, 28);
                            Thread.sleep(shooterPlant.speed * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (shooterPlant.health > 0);
                    while (true) shooterPlant.health--;
                } else System.out.println("Pos is null");
            }
        }).start();
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
