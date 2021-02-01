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
        private final int[] walkDelay = {115, 130};
        private final int[] additionalDamage = {0, 5, 5, 5, 5};
        int difficulty, gap = 5, suns = 500;
        long gameTime = 0;
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
        public Player newLevel;
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
    private void starter(Player level, boolean mute) {
        this.setIconImage(new ImageIcon("icon.png").getImage());
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

        pauseButton();

        backgrounds(); // Creates the main and the plants menu background

        mower();

        setVisible(true);

        plants.setIcon(Icons.plantMenuIcon);
    }
    private void gameTimer() {
        new Thread( () -> {
            long startTime = System.currentTimeMillis() / 1000 - gameTime;
            do {
                if (paused) return;
                gameTime = (System.currentTimeMillis() / 1000) - startTime;
                System.out.println(gameTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gameTime == gap) {
                    waves(1);
                }
                else if (gameTime == gap + 150) {
                    waves(2);
                }
                else if (gameTime == gap + 330) {
                    waves(3);
                }
            } while (gameTime != gap + 480);
            try {
                win();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
    private void addSun(int points) {
        suns += points;
        keptSun.setText(suns + "");
    }
    public synchronized static Zombie getFirstZombieByRow(JLabel plant) {
        if (Zombie.zombies.size() != 0) {
            Zombie first = Zombie.zombies.get(0);
            for (int i = 1; i < Zombie.zombies.size(); i++) {
                if (Zombie.zombies.get(i).row == Sluts.getYSlut(plant.getBounds())) {
                    if (first.getBounds().x >= Zombie.zombies.get(i).getBounds().x)
                        first = Zombie.zombies.get(i);
                }
            }
            int zombieX = first.getBounds().x;
            if(first.getClass() == PoleVaulting.class || first.getClass() == Newspaper.class)
                zombieX += 200;
            else if (first.getClass() == ConeHead.class || first.getClass() == BucketHead.class)
                zombieX += 60;
            if(zombieX < plant.getX()) first = null;
            return first;
        }
        return null;

    }
    private void waves(int roundNumber) {
        if (roundNumber != round) {
            round = roundNumber;
            sendZombies();
        }
    }
    private void win() throws InterruptedException {
        if (!lost || !won) {
            won = true;
            Sounds.play(WIN);
            pause();
            Thread.sleep(1000);
            if (difficulty == 1) newLevel.score += 10;
            else newLevel.score += 3;
            newLevel.wins++;
            newLevel.save();
            new JOptionPane("You Lost!").createDialog("");
            Sounds.mute();
            dispose();
        }
    }
    private void lose() throws InterruptedException {
        if (!won || !lost) {
            lost = true;
            Sounds.play(LOSE);
            pause();
            Thread.sleep(1000);
            if (difficulty == 1) newLevel.score -= 3;
            else newLevel.score -= 1;
            newLevel.losses++;
            newLevel.save();
            new JOptionPane("You Lost!").createDialog("");
            Sounds.mute();
            dispose();
        }
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
    private void tripleShot(Plant shooterPlant) {
        new Thread(() -> {
            if (shooterPlant.health > 0) {
                int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
                if (pos != null) {
                    do {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (shooterPlant.health > 0 && !paused);
                } else System.out.println("Pos is null");
            }
        }).start();
    }

    private void plantsJob() {
        new Thread(() -> {

            keptSun = new JLabel();
            JLabel score = new JLabel();
            score.setIcon(Icons.scoreBoxIcon);
            keptSun.setFont(new Font(null, Font.BOLD, 26));
            keptSun.setHorizontalAlignment(SwingConstants.CENTER);
            plants.add(score);
            score.add(keptSun);
            score.setBounds(11, 60, 56, 25);

            SpringLayout layout = new SpringLayout();
            score.setLayout(layout);
            keptSun.setText(suns + "");
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, keptSun, 0, SpringLayout.HORIZONTAL_CENTER, score);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, keptSun, 0, SpringLayout.VERTICAL_CENTER, score);

            label.add(plants);
            plants.setBounds(200, 0,450, 88);
            label.repaint();

        }).start();
    }
    private void pauseButton() {
        pauseButton = new JLabel();
        label.add(pauseButton);
        pauseButton.setIcon(Icons.pauseButtonIcon);
        pauseButton.setBounds(950, 1, 40, 40);
        pauseButton.addMouseListener(pauseClickListener(pauseButton));
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
        new PauseMenu(this);
    }
    //2methodscommented
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
                            case "gfx/threepeater.pvz" -> {
                                tmp = new Threepeater(label, position);
                                i = 6;
                            }
                            case "gfx/gatPea.pvz" -> {
                                tmp = new GatlingPea(label, position);
                                i = 7;
                            }
                            case "gfx/potato_2.pvz" -> {
                                tmp = new Potato(label, position);
                                i = 8;
                            }
                            default -> throw new RuntimeException("Error in labelClickListener switch");
                        }
                        Sounds.play(PLANT);
                        tmp.setBounds(location[0], location[1], tmp.getIcon().getIconWidth(), tmp.getIcon().getIconHeight());
                        objects.add(new Coordination(tmp, position[0], position[1]));
                        clicked.setIcon(null);
                        coolDown(i, difficulty==0?coolDownN[i]:coolDownH[i]);
                        addSun(- tmp.cost);
                        if (tmp.getClass() == PeaShooter.class
                                || tmp.getClass() == Repeater.class
                                || tmp.getClass() == GatlingPea.class) shoot(tmp, false);
                        else if (tmp.getClass() == SnowPea.class) shoot(tmp, true);
                        else if (tmp.getClass() == SunFlower.class) produceSun(tmp);
                        else if (tmp.getClass() == Cherry.class) explode(tmp);
                        else if (tmp.getClass() == Potato.class) mineSet(tmp);
                       // else if (tmp.getClass() == Threepeater.class) tripleShot(tmp);
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

    private void mineSet(Plant tmp) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tmp.setIcon(Icons.potatoBIcon);
            while (tmp.health > 0) {
                Zombie aim = getFirstZombieByRow(tmp);
                if (aim != null)
                    if (aim.getX() - tmp.getX() < 20) {
                        play(CHERRY_EXPLOSION);
                        tmp.setIcon(Icons.potatoCIcon);
                        aim.kill(true);
                        new Timer(1000, e -> {
                            label.remove(tmp);
                            label.repaint();
                            ((Timer)e.getSource()).setRepeats(false);
                        }).start();
                    }
            }
        }).start();
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
    private void produceSun(Plant tmp) {
        new Thread(() -> {
            threadPool.add(Thread.currentThread());
            while (!won || !lost) {
                try {
                    Thread.sleep(sunflowerTimer[difficulty] * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (paused) break;
                if (tmp.health > 0)
                    sunLanding(new int[] {tmp.getBounds().x, tmp.getBounds().y});
                else return;
            }
        }).start();
    }

    private MouseListener cardsClickListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boolean available = false;
                Icon icon = ((JLabel)e.getSource()).getIcon();
                if (icon == Icons.sunflowerCard) {
                    if (suns >= 50 && sunAvail) {
                        clicked.setIcon(Icons.sunflowerIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.peaShooterCard) {
                    if (suns >= 100 && peaAvail) {
                        clicked.setIcon(Icons.peaIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.snowPeaCard) {
                    if (suns >= 175 && snowAvail) {
                        clicked.setIcon(Icons.frozenIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.wallCard) {
                    if (suns >= 50 && nutAvail) {
                        clicked.setIcon(Icons.walnutIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.cherryCard) {
                    if (suns >= 150 && cherAvail) {
                        clicked.setIcon(Icons.cherryIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.repeaterCard) {
                    if (suns >= 150 && repAvail) {
                        clicked.setIcon(Icons.repeaterIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.threePeaCard) {
                    if (suns >= 325 && threeAvail) {
                        clicked.setIcon(Icons.threePeaIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.gatlingCard) {
                    if (suns >= 250 && gatAvail) {
                        clicked.setIcon(Icons.gatlingIcon);
                        available = true;
                    }
                    else Sounds.play(Sounds.INSUFFICIENT);
                }
                else if (icon == Icons.potatoCard) {
                    if (suns >= 25 && potAvail) {
                        clicked.setIcon(Icons.potatoBIcon);
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
    private void coolDown(int card, float v) {
        new Thread(() -> {
            threadPool.add(Thread.currentThread());
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
                    case 6 -> {
                        threeAvail = false;
                        Thread.sleep((long) (v * 1000));
                        threeAvail = true;
                    }
                    case 7 -> {
                        potAvail = false;
                        Thread.sleep((long) (v * 1000));
                        potAvail = true;
                    }
                    case 8 -> {
                        gatAvail = false;
                        Thread.sleep((long) (v * 1000));
                        gatAvail = true;
                    }
                    case 9 -> {
                        beetAvail = false;
                        Thread.sleep((long) (v * 1000));
                        beetAvail = true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.remove(Thread.currentThread());
        }).start();
    }

    private MouseMotionListener motionListener() {
        return new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me)
            {
                if (clicked.getIcon() != null) {
                    clicked.setBounds(me.getX() - 50, me.getY() - 50, clicked.getIcon().getIconWidth(), clicked.getIcon().getIconHeight());
                    clicked.repaint();
                }
            }
        };
    }
    private void backgrounds() {
        label2 = new JLabel();// Setting the background
        label2.setIcon(Icons.background);
        label.setIcon(Icons.background);
        label.setBounds(0, 0, 1400, 600);
        plants = new JLabel();
        add(label2);
        add(label);

        clicked = new JLabel();
        label.add(clicked);
        label.addMouseListener(labelClickListener());
    }
    private void readySetPlant() {
        Thread n = new Thread(() -> {
            try {
                placeRandomZombies(label2);
                Sounds.backPlay(CHOOSE_DECK);
                label2.setBounds(-300, 0, 1400, 600);
                deck = new JLabel();
                deck.setBounds(380, 98, 325, 180);
                deck.setIcon(Icons.deckIcon);
                label2.add(deck);
                plants.setBounds(330, 0, 450, 88);
                label2.add(plants);
                JLabel[] cards = new JLabel[10];

                Sluts.setCardSluts();

                for (int i = 0; i < cards.length; i++) {
                    cards[i] = Cards.getCard(i, deck);
                    cards[i].setBounds(Sluts.getCardSlut(i));
                    cards[i].setName(String.valueOf(i));
                    cards[i].addMouseListener(deckClickListener());
                }

                Thread.sleep(5000);

                label2.remove(plants);
                label2.remove(deck);
                label2.repaint();

                //noinspection ForLoopReplaceableByForEach
                for (int i = 0; i < cards.length; i++) {
                    cards[i].removeMouseListener(cards[i].getMouseListeners()[0]);
                    cards[i].addMouseListener(cardsClickListener());
                }
                Timer t = new Timer(33, e ->
                        label2.setBounds(label2.getX() + 10, label2.getY(), 1400, 600));
                t.start();
                Thread.sleep(999);
                t.stop();
                label2.setBounds(0, 0, 1400, 600);
                Sounds.mute();
                Sounds.play(READY);// Play background music
                remove(label2);
                readyLabel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        n.start();
        try {
            n.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    ArrayList<Integer> cardsList = new ArrayList<>();

    private MouseListener deckClickListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                JLabel aim = ((JLabel)e.getSource());
                System.out.println(aim.getName());
                Icon icon = aim.getIcon();
                int index = -1;
                for (int j : cardsList) {
                    if (j == Integer.parseInt(aim.getName())) {
                        index = j;
                        break;
                    }
                }
                aim.setIcon(null);
                aim.getParent().repaint();
                if (index == -1) {
                    if (cardsList.size() < 6) {
                        plants.add(aim);
                        aim.setBounds(Sluts.getCardPos(cardsList.size()));
                        cardsList.add(Integer.parseInt(aim.getName()));
                        Sounds.play(SELECT);
                    } else Sounds.play(INSUFFICIENT);
                } else {
                    deck.add(aim);
                    cardsList.remove((Object)Integer.parseInt(aim.getName()));
                    aim.setBounds(Sluts.getCardSlut(Integer.parseInt(aim.getName())));
                    Sounds.play(SELECT);
                }
                aim.setIcon(icon);

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
    /**
     * This methods place some random number of zombies in the road. Just before the game starts.
     * @param label the container that we want to place the zombies (default is the background label.)
     */
    private void placeRandomZombies(Container label) {
        new Thread(() -> {//1017, 84
            Random random = new Random();
            int count = 5 + random.nextInt(10);
            IntStream iX = random.ints(count, 1017, 1200);
            int[] posX = iX.toArray();
            IntStream iY = random.ints(count, 84, 510);
            int[] posY = iY.toArray();
            JLabel[] randomZombie = new JLabel[count];
            for (int i = 0; i < count; i++) {
                randomZombie[i] = new JLabel();
                randomZombie[i].setIcon(Icons.constantZombieIcon);
                label.add(randomZombie[i]);
                randomZombie[i].setBounds(posX[i], posY[i], 62, 100);
            }
            try {
                Thread.sleep(3000);
                for (int i = 0; i < count; i++) {
                    remove(randomZombie[i]);
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
                    int type = random.nextInt(7);
                    Zombie zombie;
                    int rand = random.nextInt(5);
                    location = Sluts.getZombieLocation(rand);
                    switch (type) {
                        case 0, 4 -> zombie = new Normal(label, rand);
                        case 1 -> zombie = new ConeHead(label, rand);
                        case 2 -> zombie = new BucketHead(label, rand);
                        case 3 -> zombie = new Football(label, rand);
                        case 5 -> zombie = new Newspaper(label, rand);
                        case 6 -> zombie = new PoleVaulting(label, rand);
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
    private void progress() {
        gone++;
    }

    @SuppressWarnings("RedundantCast")
    private synchronized void walk(Zombie zombie) {
        int delay;
        if (difficulty == 1 && zombie.getClass() != Normal.class) delay = walkDelay[difficulty];
        else if (difficulty == 1) delay = walkDelay[0];
        else delay = walkDelay[difficulty];
        Timer t = new Timer(delay, e -> {
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
                        eatPlant(zombie, plant);
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
    private void eatPlant(Zombie zombie, Plant victim) {
        Thread t = new Thread( () -> {
            int i;
            if (zombie.getClass() == Normal.class) i = 0;
            else if (zombie.getClass() == ConeHead.class) i = 1;
            else if (zombie.getClass() == BucketHead.class) i = 2;
            else if (zombie.getClass() == Football.class) i = 3;
            else i = -1;
            int amount = zombie.damage + additionalDamage[i] * difficulty;
            threadPool.add(Thread.currentThread());
            do {
                if (zombie.health > 0)
                    victim.lossHealth(amount);
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
    public static void main(String[] args) {
        if (Levels.load() == null)
            new Game(new Levels(), false);
        else {
            ArrayList<Levels> level= Levels.load();
            assert level != null;
            new Game(level.get(0), false);
        }
    }


}