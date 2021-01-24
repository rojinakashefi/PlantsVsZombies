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
    int difficulty, gap = 50, suns = 50;
    boolean[] mowerAvailable = new boolean[5];
    JLabel[] mowers = new JLabel[5];
    boolean sunAvail = true, peaAvail = true, nutAvail = true, snowAvail = true, cherAvail = true, repAvail = true;
    float[] coolDownN = {7.5f, 7.5f, 7.5f, 30f, 30f, 15f};
    float[] coolDownH = {7.5f, 7.5f, 30f, 30f, 45f, 25f};
    boolean won = false,lost = false, containsIcon = false;
    JLabel clicked = null;
    JLabel label, label2;
    JLabel plants;
    JLabel keptSun;
    ImageIcon mowerIcon = new ImageIcon("gfx/mower.pvz");
    public static ArrayList<Coordination> objects = new ArrayList<>();
    Levels newLevel;
    private boolean mute;
    public static ArrayList<Timer> timerPool = new ArrayList<>();

    public Game(Levels level) {
        muted = mute;
        this.mute = mute;
        setVisible(true);
        Sluts.setSluts(); // Defines the checkered ground as sluts and calculates their coordinates
        objects.clear();  // clears the list of last game spawned objects
        newLevel = level;
        difficulty = newLevel.difficulty;

        //Game Page specs
        setSize(1000, 635);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        label = new JLabel();// Setting the background

        backgrounds(); // Creates the main and the plants menu background

        //mower();

        //In this Section the first animation of the game executed
        readySetPlant();

        plants.setIcon(new ImageIcon("gfx/pm.pvz"));
        // plantsJob();

        new Thread(() -> {
            try {
                while (!won || !lost) {
                    Thread.sleep(20000);
                    //sunLanding(null);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        //xwaves();
    }

    private void mower() {
        for (int i = 0; i < 5; i++) {
            final int[] ii = {i};
            new Thread(() -> {
                int posY = Sluts.getMowerLocation(ii[0]);
                int posX = 175;
                mowers[ii[0]] = new JLabel();
                mowers[ii[0]].setIcon(mowerIcon);
                mowers[ii[0]].setBounds(posX, posY, 82, 70);
                label.add(mowers[ii[0]]);
                mowerAvailable[ii[0]] = true;
            }).start();
        }
    }

    private void runMower(int ySlut) {
        mowerAvailable[ySlut] = false;
        Sounds.backPlay(MOWER);
        Timer timer = new Timer(5, e -> {
            mowers[ySlut].setBounds(mowers[ySlut].getX() + 1, mowers[ySlut].getY(), 87, 70);
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).zombie != null && objects.get(i).zombie.row == ySlut) {
                    if (objects.get(i).zombie.getX() - mowers[ySlut].getX() < 20)
                        objects.get(i).zombie.kill(false);
                }
            }
            if (mowers[ySlut].getBounds().x > 1000) {
                timerPool.remove(((Timer) e.getSource()));
                ((Timer) e.getSource()).stop();
            }
        });
        timerPool.add(timer);
        timer.start();
    }

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

    private void readySetPlant() {
        try {
            placeRandomZombies(label);
            new Thread(() -> Sounds.play(STARTING)).start();// Play starting music
            label.setBounds(-300, 0, 1400, 600);
            Thread.sleep(5000);
            Timer t = new Timer(33, e ->
                    label.setBounds(label.getX() + 10, label.getY(), 1400, 600));
            t.start();
            Thread.sleep(999);
            t.stop();
            new Thread(() -> Sounds.play(READY)).start();// Play background music
            readyLabel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                int[] location;
                for (int i = 0; i < ss; i++) {
                    int type = random.nextInt(5);
                    Zombie zombie;
                    int rand = random.nextInt(5);
                    location = Sluts.getZombieLocation(rand);
                    switch (type) {
                        case 0, 4 -> zombie = new Normal(label, rand);
                        case 1 -> zombie = new ConeHead(label, rand);
                        case 2 -> zombie = new BucketHead(label, rand);
                        case 3 -> zombie = new Football(label, rand);
                        default -> throw new RuntimeException("sendZombies Switch");
                    }
                    if (zombie.getClass() == BucketHead.class)
                        zombie.setBounds(location[0], location[1] + 10, zombie.sizeX, zombie.sizeY);
                    else
                        zombie.setBounds(location[0], location[1] - 40, zombie.sizeX, zombie.sizeY);
                    objects.add(new Coordination(zombie, rand));
                    //walk(zombie);
                    if (round == 1) Thread.sleep(30000);
                    else if (round == 3) Thread.sleep(25000);
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

    public static void removeZombie(Zombie zombie) {
        Zombie.zombies.remove(zombie);
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).zombie == zombie) {
                objects.remove(i);
                break;
            }
        }
    }
}