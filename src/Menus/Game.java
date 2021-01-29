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
import static Miscs.Cards.*;
import static Miscs.Icons.mowerIcon;
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
    private final int[] skyTimer = {25, 30};
    private final int[] sunflowerTimer = {20, 25};
    private final int[] walkDelay = {130, 115};
    private final int[] additionalDamage = {0, 10, 10, 15, 5};
    int difficulty, gap = 5, suns = 2500; //TODO
    boolean[] mowerAvailable = new boolean[5];
    JLabel[] mowers = new JLabel[5];
    boolean sunAvail = true, peaAvail = true,
            nutAvail = true, snowAvail = true,
            cherAvail = true, repAvail = true,
            threeAvail = true, potAvail = true,
            gatAvail = true, beetAvail = true;
    float[] coolDownN = {7.5f, 7.5f, 7.5f, 30f, 30f, 15f, 30f, 15f, 30f, 25f};
    float[] coolDownH = {7.5f, 7.5f, 30f, 30f, 45f, 25f, 30f, 25f, 45f, 30f};
    boolean won = false,lost = false, containsIcon = false;
    JLabel clicked = null;
    JLabel label, label2;
    JLabel pauseButton;
    JLabel plants;
    JLabel keptSun;
    JLabel blackScreen;
    JLabel deck;
    public static ArrayList<Coordination> objects = new ArrayList<>();
    public Levels newLevel;
    public static boolean mute;
    public static ArrayList<Timer> timerPool = new ArrayList<>();
    public static ArrayList<Thread> threadPool = new ArrayList<>();
    private boolean paused = false;
    int gone = 0, round = 0;

    public Game(Levels level, boolean mute) {
        muted = mute;
        Game.mute = mute;
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

        mower();

        setVisible(true);
        //In this Section the first animation of the game executed
        readySetPlant();

        plants.setIcon(Icons.plantMenuIcon);
        plantsJob();

        new Thread(() -> {
            threadPool.add(Thread.currentThread());
            try {
                while (!won || !lost) {
                    Thread.sleep(20000);
                    sunLanding(null);
                    if (paused) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.remove(Thread.currentThread());
        }).start();

        waves();

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
                    if (objects.get(i).zombie.getX() - mowers[ySlut].getX() < 20) {
                        objects.get(i).zombie.kill(false);
                    }
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
    private void eatPlant(Zombie zombie, Plant victim) {
        Thread t = new Thread( () -> {
            threadPool.add(Thread.currentThread());
            do {
                if (zombie.health > 0)
                    if (zombie.getClass() != Normal.class)
                        victim.lossHealth(15);
                    else victim.lossHealth(10);
                else return;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (zombie.health > 0 && victim.health > 0 && !paused);
            if (zombie.health > 0)
                walk(zombie);
            threadPool.remove(Thread.currentThread());
        });
        t.start();
    }

    private void shoot(Plant shooterPlant, boolean isFrozen) {
        new Thread(() -> {
            if (shooterPlant.health > 0) {
                if (TESTING)
                    System.out.println("Shooter Position For pea: " + shooterPlant.getBounds().x + " " + shooterPlant.getBounds().y);
                int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
                if (pos != null) {
                    do {
                        try {
                            PeaBullet pea = new PeaBullet(label, shooterPlant, isFrozen);
                            if (isFrozen) pea.setIcon(Icons.snowBulletIcon);
                            else pea.setIcon(Icons.peaBulletIcon);
                            pea.setBounds(shooterPlant.getBounds().x + 46, shooterPlant.getBounds().y + 16, 28, 28);
                            long sleep;
                            switch (shooterPlant.speed) {
                                case 1 -> sleep = 1000L;
                                case 2 -> sleep = 500L;
                                case 3 -> sleep = 333L;
                                default -> throw new IllegalStateException("Unexpected value: " + shooterPlant.speed);
                            }
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (shooterPlant.health > 0 && !paused);
                } else System.out.println("Pos is null");
            }
        }).start();
    }

    private MouseListener pauseClickListener(JLabel pauseButton) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                //pauseMenu();
                pause();
                pauseButton.removeMouseListener(pauseButton.getMouseListeners()[0]);
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
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
                        Sounds.play(PLANT);
                        tmp.setBounds(location[0], location[1], 100, 100);
                        objects.add(new Coordination(tmp, position[0], position[1]));
                        clicked.setIcon(null);
                        coolDown(i, difficulty==0?coolDownN[i]:coolDownH[i]);
                        addSun(- tmp.cost);
                        if (tmp.getClass() == PeaShooter.class) shoot(tmp, false);
                        else if (tmp.getClass() == SnowPea.class) shoot(tmp, true);
                        else if (tmp.getClass() == SunFlower.class) produceSun(tmp);
                        else if (tmp.getClass() == Cherry.class) explode(tmp);
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
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boolean available = false;
                Object source = e.getSource();
                if (source.toString().contains("cardSun.pvz")) {
                    if (suns >= 50 && sunAvail) {
                        clicked.setIcon(Icons.sunflowerIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (source.toString().contains("cardPea.pvz")) {
                    if (suns >= 100 && peaAvail) {
                        clicked.setIcon(Icons.peaIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (source.toString().contains("cardFreeze.pvz")) {
                    if (suns >= 175 && snowAvail) {
                        clicked.setIcon(Icons.frozenIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (source.toString().contains("cardWallnut.pvz")) {
                    if (suns >= 50 && nutAvail) {
                        clicked.setIcon(Icons.walnutIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (source.toString().contains("cardCherry.pvz")) {
                    if (suns >= 150 && cherAvail) {
                        clicked.setIcon(Icons.cherryIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else System.out.println("Error In cardsClickListener");

                if (!containsIcon && available) {
                    containsIcon = true;
                    label.addMouseMotionListener(motionListener());
                    Sounds.play(SELECT);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
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
    private void sendZombies() {
        int zombies;
        if (round == 1) zombies = 5;
        else zombies = 12;
        Random random = new Random();
        new Thread(() -> {
            byte count = 1;
            try {
                int[] location;
                threadPool.add(Thread.currentThread());
                for (int i = 0; i < zombies; i++) {
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
                    walk(zombie);
                    progress();
                    if (round == 1) Thread.sleep(30000);
                    else if (round == 3) {
                        if (count == 2) {
                            Thread.sleep(25000);
                            count--;
                        }
                        else count++;
                    }
                    else {
                        if (count == 2) {
                            Thread.sleep(30000);
                            count--;
                        }
                        else count++;
                    }
                }
                threadPool.remove(Thread.currentThread());
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
    private void produceSun(Plant tmp) {

    }
    private void addSun(int points) {
        suns += points;
        keptSun.setText(suns + "");
    }
    private void sunLanding(int[] pos) {
        SunPoint sun = new SunPoint(label);

        Random random = new Random();
        int posX, posY;
        final int[] i;
        if (pos == null) {
            posY = 80;
            posX = 200 + random.nextInt(700);
            i = new int[]{0};
        } else {
            posX = pos[0];
            posY = pos[1];
            i = new int[]{380};
        }
        sun.setBounds(posX + 30, posY, 80, 80);
        sun.addMouseListener(sunClickListener(sun));

        Timer t = new Timer(20, e -> {
            sun.setBounds(sun.getX(), sun.getY() + 1, 80, 80);
            i[0]++;
            if (i[0] == 400 || sun.getIcon() == null) {
                ((Timer) e.getSource()).stop();
                new Timer(5000, ee -> {
                    sun.setIcon(null);
                    remove(sun);
                    sun.remove();
                    sun.removeMouseListener(null);
                    ((Timer) ee.getSource()).setRepeats(false);
                }).start();
            }
        });
        t.start();
        timerPool.add(t);
    }
    private MouseListener sunClickListener(SunPoint sun) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                remove(sun);
                sun.setIcon(null);
                sun.removeMouseListener(this);
                addSun(sun.points);
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    private void explode(Plant tmp) {
        Timer t = new Timer(2000, e -> {
            Sounds.play(CHERRY_EXPLOSION);
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).zombie != null)
                    if (tmp.getX() - objects.get(i).zombie.getX() < 100 &&
                            tmp.getX() - objects.get(i).zombie.getX() > 0 ||
                            -tmp.getX() + objects.get(i).zombie.getX() < 100 &&
                                    -tmp.getX() + objects.get(i).zombie.getX() > 0 ||
                            tmp.getY() - objects.get(i).zombie.getY() < 100 &&
                                    tmp.getY() - objects.get(i).zombie.getY() > 0 ||
                            -tmp.getY() + objects.get(i).zombie.getY() < 100 &&
                                    -tmp.getY() + objects.get(i).zombie.getY() > 0) {
                        objects.get(i).zombie.kill(true);
                    }
            }
            tmp.setIcon(null);
            remove(tmp);
            removePlant(tmp);
        });
        t.start();
        t.setRepeats(false);
        timerPool.add(t);
    }

    private void coolDown(int card, float v) {
        new Thread(() -> {
            try {
                switch (card) {
                    case 0 -> {
                        sunAvail = false;
                        Thread.sleep((long) (v * 1000));
                        sunAvail = true;
                    }
                    case 1 -> {
                        peaAvail = false;
                        Thread.sleep((long) (v * 1000));
                        peaAvail = true;
                    }
                    case 2 -> {
                        snowAvail = false;
                        Thread.sleep((long) (v * 1000));
                        snowAvail = true;
                    }
                    case 3 -> {
                        nutAvail = false;
                        Thread.sleep((long) (v * 1000));
                        nutAvail = true;
                    }
                    case 4 -> {
                        cherAvail = false;
                        Thread.sleep((long) (v * 1000));
                        cherAvail = true;
                    }
                    case 5 -> {
                        repAvail = false;
                        Thread.sleep((long) (v * 1000));
                        repAvail = true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void plantsJob() {
        new Thread(() -> {
            JLabel[] cards = new JLabel[5];

            keptSun = new JLabel();
            JLabel score = new JLabel();
            score.setIcon(Icons.scoreBoxIcon);
            keptSun.setFont(new Font(null, Font.BOLD, 26));
            keptSun.setHorizontalAlignment(SwingConstants.CENTER);
            plants.add(score);
            score.add(keptSun);
            score.setBounds(11, 72, 73, 30);

            SpringLayout layout = new SpringLayout();
            score.setLayout(layout);
            keptSun.setText(suns + "");
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, keptSun, 0, SpringLayout.HORIZONTAL_CENTER, score);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, keptSun, 0, SpringLayout.VERTICAL_CENTER, score);


            cards[0] = Cards.getCard(SUNFLOWER, plants);
            cards[0].setBounds(93, 7, 64, 90);

            cards[1] = Cards.getCard(PEA_SHOOTER, plants);
            cards[1].setBounds(158, 7, 64, 90);

            cards[2] = Cards.getCard(SNOW_PEA, plants);
            cards[2].setBounds(223, 7, 64, 90);

            cards[3] = Cards.getCard(CHERRY, plants);
            cards[3].setBounds(288, 7, 64, 90);

            cards[4] = Cards.getCard(WALL_NUT, plants);
            cards[4].setBounds(353, 7, 64, 90);

            for (int i = 0; i < 5; i++) {
                cards[i].addMouseListener(cardsClickListener());
            }
        }).start();
    }
    public static void removePlant (Plant plant) {
        Plant.plants.remove(plant);
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).plant == plant) {
                objects.remove(i);
                break;
            }
        }
    }

    @SuppressWarnings("RedundantCast")
    private synchronized void walk(Zombie zombie) {
        Timer t = new Timer(120, e -> {
            if (zombie.health > 0) {
                zombie.setBounds(zombie.getX() - zombie.speed, zombie.getY(), zombie.sizeX, zombie.sizeY);
                int ySlut = zombie.row;
                if (zombie.getX() - 205 < 20) {
                    if (mowerAvailable[ySlut])
                        runMower(ySlut);
                    else {
                        try {
                            if (Zombie.zombies.contains(zombie) && zombie.getX() - 205 < 5)
                                lose();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
                for (Plant plant : Plant.plants) {
                    if (-plant.getBounds().x + zombie.getBounds().x < 20 && plant.row == zombie.row
                            && zombie.getBounds().x - plant.getBounds().x > 0) {
                        //eatPlant(zombie, plant);
                        ((Timer) e.getSource()).stop();
                        timerPool.remove(((Timer) e.getSource()));
                    }
                }
            }else {
                ((Timer) e.getSource()).stop();
                timerPool.remove(((Timer) e.getSource()));
            }
        });
        t.start();
        timerPool.add(t);
    }
    private void progress() {
        gone++;
    }

    private void pause() {
        for (Timer timer: timerPool) timer.stop();
        muted = true;
        paused = true;
    }
    void resume() {
        for (Timer timer: timerPool) timer.start();
        if(!mute) muted = true;
        blackScreen.setIcon(null);
        remove(blackScreen);
        paused = false;
        pauseButton.addMouseListener(pauseClickListener(pauseButton));
    }
    private void pauseMenu() {
        blackScreen = new JLabel();
        blackScreen.setIcon(Icons.blackScreen);
        label.add(blackScreen);
        blackScreen.setBounds(0, 0, 1000, 645);
        label.remove(plants);
        label.add(plants);
        for (JLabel mower : mowers) {
            label.remove(mower);
            label.add(mower);
        }
        //new PauseMenu(this);
    }
    private void waves() {
        new Thread(() -> {
            Sounds.backPlay(IN_GAME); // Play before-game background music
            try {
                Thread.sleep(gap * 1000L - 500);
                Sounds.backPlay(ZOMBIES_COMING); // Play The Zombies are coming sound effect
                Thread.sleep(500);
                while (paused) Thread.sleep(1000);
                ++round;
                sendZombies(); // Send Zombies to the field. wave 1
                Thread.sleep(150000);
                while (paused) Thread.sleep(1000);
                ++round;
                sendZombies(); // Send Zombies to the field. wave 2
                Thread.sleep(180000);
                while (paused) Thread.sleep(1000);
                ++round;
                sendZombies(); // Send Zombies to the field. wave 3
                Thread.sleep(150000);
                //noinspection StatementWithEmptyBody
                while (!Zombie.zombies.isEmpty()) ;
                win();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void win() throws InterruptedException {
        if (!lost || !won) {
            won = true;
            Sounds.play(WIN);
            pause();
            Thread.sleep(1000);
            newLevel.save();
        }
    }

    private void lose() throws InterruptedException{
        if (!won || !lost) {
            lost = true;
            Sounds.play(LOSE);
            pause();
            Thread.sleep(1000);

        }
    }

}