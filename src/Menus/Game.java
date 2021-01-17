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
import static Miscs.Sounds.PLANT;

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
        int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
        if (pos != null) {
            JLabel pea = new JLabel();
            pea.setIcon(new ImageIcon("gfx/bullet.pvz"));
            pea.setBounds(pos[0], pos[1], 28, 28);
            //TODO:label.add()
        } else System.out.println("goto bokhor");
    }


    private  MouseListener labelClickListener() {
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
