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
import java.util.Random;
import java.util.stream.IntStream;


import static Main.Main.TESTING;
import static Miscs.Icons.mowerIcon;
import static Miscs.Icons.okCheckMarkIcon;
import static Miscs.Sounds.*;

/**
 * This class shows the In game structure:
 *
 * DONE: Background Sound Effect,
 *       Main Frame,
 *       Plants Spawn,
 *       Zombies Spawn,
 *       first animation,
 *       leveling system,
 *       sluts,
 *       sounds,
 *       more mobs
 *       plant menu
 *
 * There are some final elements that based on the project document and have two types of difficulty.
 * @// STOPSHIP: 1/30/2021 Project Finished
 */

@SuppressWarnings({"BusyWait", "RedundantCast"})
public class Game extends JFrame {
    private final int[] skyTimer = {25, 30};
    private final int[] sunflowerTimer = {20, 25};
    private final int[] walkDelay = {115, 130};
    private final int[] additionalDamage = {0, 5, 5, 5, 5, 5};
    int difficulty, gap = 50, suns = 50;
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
    JLabel clicked;
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
    private boolean paused = false;
    int round = 0;

    /**
     * @param player gets the player properties for save it after the game
     * @param mute sets the mute option of the game whether its true or false.
     */
    public Game(Player player, boolean mute) {
        //In this Section the first animation of the game executed
        label2 = new JLabel();// Setting the background
        label2.setIcon(Icons.background);
        add(label2);

        label = new JLabel();// Setting the background
        label.setIcon(Icons.background);
        label.setBounds(0, 0, 1400, 600);
        plants = new JLabel();
        add(label);

        clicked = new JLabel();
        label.add(clicked);
        label.addMouseListener(labelClickListener());

        starter(player, mute);

        readySetPlant();

        setVisible(true);
    }

    public Game(GameSave saved, Player player, boolean mute) {
        gameTime = saved.gameTime;
        label = new JLabel();// Setting the background
        label.setIcon(Icons.background);
        label.setBounds(0, 0, 1400, 600);
        plants = new JLabel();
        add(label);

        clicked = new JLabel();
        label.add(clicked);
        label.addMouseListener(labelClickListener());
        starter(player, mute);
        for (int i = 0; i < saved.objects.size(); i++) {
            switch (saved.objects.get(i).objectName) {
                case "Cherry" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Cherry temp = new Cherry(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    explode(temp);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "GatlingPea" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    GatlingPea temp = new GatlingPea(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    shoot(temp, false);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "PeaShooter" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    PeaShooter temp = new PeaShooter(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    shoot(temp, false);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "Potato" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Potato temp = new Potato(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    mineSet(temp);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "Repeater" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Repeater temp = new Repeater(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    shoot(temp, false);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "SnowPea" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    SnowPea temp = new SnowPea(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    shoot(temp, true);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "SunFlower" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    SunFlower temp = new SunFlower(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    produceSun(temp);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "Threepeater" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Threepeater temp = new Threepeater(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    tripleShot(temp);
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "wallNut" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    wallNut temp = new wallNut(label, pos);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y,
                            temp.getIcon().getIconWidth(), temp.getIcon().getIconHeight());
                    objects.add(new Coordination(temp, pos[0], pos[1]));
                }
                case "BucketHead" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    BucketHead temp = new BucketHead(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 40, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                case "ConeHead" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    ConeHead temp = new ConeHead(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 40, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                case "Football" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Football temp = new Football(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 40, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                case "Newspaper" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Newspaper temp = new Newspaper(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 110, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                case "Normal" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    Normal temp = new Normal(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 40, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                case "PoleVaulting" -> {
                    int[] pos = Sluts.getSlut(saved.objects.get(i).position);
                    PoleVaulting temp = new PoleVaulting(label, pos[1]);
                    temp.health = saved.objects.get(i).health;
                    temp.setBounds(saved.objects.get(i).position.x,
                            saved.objects.get(i).position.y - 110, temp.sizeX, temp.sizeY);
                    walk(temp);
                    objects.add(new Coordination(temp, pos[1]));
                }
                default -> throw new RuntimeException("Loading Switch Exception: " + saved.objects.get(i).objectName);
            }
        }
        suns = saved.suns;
        loading(saved.cards);
        setVisible(true);
    }

    private void starter(Player level, boolean mute) {
        this.setIconImage(new ImageIcon("icon.png").getImage());
        muted = mute;
        Game.mute = mute;
        Sluts.setSluts(); // Defines the checkered ground as sluts and calculates their coordinates
        objects.clear();  // clears the list of last game spawned objects
        newLevel = level;
        difficulty = newLevel.difficulty;
        sky = skyTimer[difficulty];

        //Game Page specs
        setSize(1000, 635);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        pauseButton();

        mower();

        plants.setIcon(Icons.plantMenuIcon);
    }
    int sky = 0, round1 = 30, round2 = 30, round3 = 25, zombieTime = 0;
    int duration1 = 180, duration2 = 180;
    private void gameTimer() {
        new Thread( () -> {
            long startTime = System.currentTimeMillis() / 1000 - gameTime;
            do {
                if (paused) return;
                gameTime = (System.currentTimeMillis() / 1000) - startTime;
                if (TESTING) System.out.println("Game Time: " + gameTime);
                if (gameTime == sky) {
                    sky += sky;
                    sunLanding(null);
                }
                if (gameTime < gap + duration1 && gameTime >= gap) round = 1;
                else if (gameTime < gap + duration1 + duration2 && gameTime >= gap + duration1) round = 2;
                else if (gameTime < 480 && gameTime >= gap + duration1 + duration2) round = 3;
                if (gameTime == gap) Sounds.backPlay(ZOMBIES_COMING);

                if (round == 1) {
                    if (gameTime - gap == zombieTime)
                    {
                        zombieTime += round1;
                        sendZombie();
                    }
                } else if (round == 2) {
                    if (gameTime - gap == zombieTime)
                    {
                        zombieTime += round2;
                        sendZombie();
                        sendZombie();
                    }
                }
                else if (round == 3) {
                    if (gameTime - gap == zombieTime)
                    {
                        zombieTime += round3;
                        sendZombie();
                        sendZombie();
                    }
                }

                if (TESTING) System.out.println("Round: " + round);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (gameTime != gap + 480);
            try {
                win();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * places the mowers in their their positions
     */
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

    /**
     * This method is called by zombies walk method. when they reach to mowers. they turn this method on.
     * @param ySlut gets the Y coordination of the zombie.
     */
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

    /**
     * Plays the sun point falling, from the sky
     * @param pos if it is null it sends the point from the sky, but if it is not, places the point
     *            just by the origin sunflower.
     */
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

    /**
     * @param sun gets the sun point that clicked on
     * @return returns the listener answer
     */
    private MouseListener sunClickListener(SunPoint sun) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                remove(sun);
                sun.setIcon(null);
                sun.removeMouseListener(sun.getMouseListeners()[0]);
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

    /**
     * @param points adds sun point to the label.
     */
    private void addSun(int points) {
        suns += points;
        keptSun.setText(suns + "");
    }

    /**
     * @param plant gets the plant or its subclasses
     * @return returns the zombie that has the same row number as the plant
     */
    public synchronized static Zombie getFirstZombieByRow(JLabel plant) {
        if (Zombie.zombies.size() != 0) {
            Zombie first = null;
            for (int i = 0; i < Game.objects.size(); i++) {
                if (Game.objects.get(i).type == 1) {
                    if (Game.objects.get(i).zombie.row == Sluts.getYSlut(plant.getBounds())) {
                        first = Game.objects.get(i).zombie;
                        int firstPosX = first.getBounds().x;
                        if ((first.getClass() == Newspaper.class
                                || first.getClass() == ConeHead.class
                                || first.getClass() == BucketHead.class) && first.getIcon() != Icons.normalZombie)
                            firstPosX += 40;
                        if (objects.get(i).zombie.getClass() == PoleVaulting.class) firstPosX += 110;
                        int zombieX = objects.get(i).zombie.getBounds().x;
                        if ((objects.get(i).zombie.getClass() == Newspaper.class
                                || objects.get(i).zombie.getClass() == ConeHead.class
                                || objects.get(i).zombie.getClass() == BucketHead.class) && objects.get(i).zombie.getIcon() != Icons.normalZombie)
                            zombieX += 40;
                        if (first.getClass() == PoleVaulting.class) zombieX += 110;
                        if (firstPosX > zombieX) {
                            first = objects.get(i).zombie;
                        }
                    }
                }
            }
            return first;
        }
        return null;
    }

    /**
     * @param plant removes the plant from objects list and
     */
    public static void removePlant (Plant plant) {
        Plant.plants.remove(plant);
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).plant == plant) {
                objects.remove(i);
                break;
            }
        }
    }

    /**
     * Called only once, at the end the waves method. That finishes the game by a win message
     * @throws InterruptedException because of the use of sleep method.
     */
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
            new JOptionPane("You Won!").createDialog("");
            Sounds.mute();
            new MainMenu(newLevel);
            dispose();
        }
    }

    /**
     * when a zombie reaches the end line, if there is no mower, the game finishes
     * @throws InterruptedException because of the use of sleep method.
     */
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
            new MainMenu(newLevel);
            dispose();
        }
    }

    /**
     * Removes the given zombie
     * @param zombie gets the zombie to remove it
     */
    public static void removeZombie(Zombie zombie) {
        Zombie.zombies.remove(zombie);
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).zombie == zombie) {
                objects.remove(i);
                break;
            }
        }
    }

    /**
     * shoots the peaBullet from the shooter
     * @param shooterPlant the host plant
     * @param isFrozen whether the host plant shoots snowPea or not
     */
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

    /**
     * shoots three peas from the origin
     * @param shooterPlant gets the origin plant
     */
    private void tripleShot(Plant shooterPlant) {
        new Thread(() -> {
            if (shooterPlant.health > 0) {
                int[] pos = Sluts.getSlut(shooterPlant.getBounds().x, shooterPlant.getBounds().y);
                if (pos != null) {
                    do {
                        try {
                            PeaBullet pea = new PeaBullet(label, shooterPlant);
                            pea.setIcon(Icons.peaBulletIcon);
                            pea.setBounds(shooterPlant.getBounds().x + 46, shooterPlant.getBounds().y + 16, 28, 28);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (shooterPlant.health > 0 && !paused);
                } else System.out.println("Pos is null");
            }
        }).start();
    }

    /**
     * adds plant menu to the background
     */
    private void plantsJob() {
        new Thread(() -> {
            label.add(plants);
            plants.setBounds(200, 0,450, 88);
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

            label.repaint();

        }).start();
    }

    /**
     * adds pause button to the background
     */
    private void pauseButton() {
        pauseButton = new JLabel();
        label.add(pauseButton);
        pauseButton.setIcon(Icons.pauseButtonIcon);
        pauseButton.setBounds(950, 1, 40, 40);
        pauseButton.addMouseListener(pauseClickListener(pauseButton));
    }

    /**
     * @param pauseButton gets the pause button of the game page
     * @return mouse click listener
     */
    private MouseListener pauseClickListener(JLabel pauseButton) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                pauseMenu();
                pause();
                pauseButton.removeMouseListener(pauseButton.getMouseListeners()[0]);
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    /**
     * adds pause menu to the game page when called
     */
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


    /**
     * @return label click listener. label is the background picture of the game
     */
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
                        else if (tmp.getClass() == Threepeater.class) tripleShot(tmp);
                    }
                }
            }

            /**
             * @param x the horizontal location of the slut
             * @param y the vertical location of the slut
             * @return checks if the slut is empty or not
             */
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

    /**
     * called when the potato mine is used
     * @param tmp the class of the potato mine
     */
    private synchronized void mineSet(Plant tmp) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tmp.setIcon(Icons.potatoBIcon);
            while (tmp.health > 0) {
                Zombie aim = null;
                for (Coordination object: objects) {
                    if (object.zombie.row == tmp.row)
                        aim = object.zombie;
                }
                if (aim != null) {
                    int size = 0;
                    if (aim.getClass() == PoleVaulting.class)
                        size = 200;
                    else if ( aim.getClass() == BucketHead.class
                            || aim.getClass() == ConeHead.class
                            || aim.getClass() == Newspaper.class)
                        size = 80;
                    if (aim.getX() + size - tmp.getX() < 20 && aim.row == tmp.row) {
                        play(POTATO_EXPLOSION);
                        tmp.setIcon(Icons.potatoCIcon);
                        removePlant(tmp);
                        aim.kill(true);
                        new Timer(1000, e -> {
                            label.remove(tmp);
                            label.repaint();
                            ((Timer) e.getSource()).setRepeats(false);
                        }).start();
                        tmp.health = 0;
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * creates an explosion when the cherry planted
     * @param tmp the class of the cherry
     */
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
                        removePlant(tmp);
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

    /**
     * starts to produce sun point when the sunflower planted
     * @param tmp the class of the sunflower
     */
    private void produceSun(Plant tmp) {
        new Thread(() -> {
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

    /**
     * the mouse listener of the cards in the plant menu
     * @return mouse click listener
     */
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

    /**
     * sets a cooldown for the seeds to limit their usage
     * @param card the aimed seed card
     * @param v time to disable that card
     * (This method could have been done in a "for loop". but there was no time to change that)
     */
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
        }).start();
    }

    /**
     * @return returns mouse motion when the mouse cursor moves
     */
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

    /**
     * sets the main background elements of the game
     */

    private void loading(ArrayList<Integer> cardsNumber) {
        JLabel[] cards = new JLabel[6];

        Sounds.backPlay(IN_GAME);

        Sluts.setCardSluts();
        plantsJob();

        if (!cardsNumber.isEmpty())
            for (int i = 0; i < cardsNumber.size(); i++) {
                cards[i] = Cards.getCard(cardsNumber.get(i), plants);
                cards[i].setBounds(Sluts.getCardPos(i));
                cards[i].setName(String.valueOf(i));
                cards[i].addMouseListener(cardsClickListener());
                plants.add(cards[i]);
            }

        cardsList = cardsNumber;

        while (zombieTime < gameTime - gap) {
            if (gameTime < gap + duration1 && gameTime >= gap) round = 1;
            else if (gameTime < gap + duration1 + duration2 && gameTime >= gap + duration1) round = 2;
            else if (gameTime < 480 && gameTime >= gap + duration1 + duration2) round = 3;
            if (round == 1) zombieTime += round1;
            else if (round == 2) zombieTime += round2;
            else if (round == 3) {
                zombieTime += duration1 + duration2;
                while (zombieTime < gameTime - gap) {
                    zombieTime += round3;
                }
            }
        }
        while (sky < gameTime) {
            sky += sky;
        }

        System.out.println(zombieTime);

        gameTimer();
    }

    /**
     * shows the game until the ready set plant method starts
     */
    private void readySetPlant() {
        Thread n = new Thread(() -> {
            placeRandomZombies(label2);
            Sounds.backPlay(CHOOSE_DECK);
            label2.setBounds(-300, 0, 1400, 600);
            deck = new JLabel();
            deck.setBounds(380, 98, 325, 180);
            deck.setIcon(Icons.deckIcon);
            label2.add(deck);
            plants.setBounds(330, 0, 450, 88);
            label2.add(plants);
            JLabel okButton = new JLabel();
            okButton.setIcon(okCheckMarkIcon);
            okButton.setBounds(630, 300, 75, 75);
            label2.add(okButton);
            JLabel[] cards = new JLabel[9];

            Sluts.setCardSluts();

            for (int i = 0; i < cards.length; i++) {
                cards[i] = Cards.getCard(i, deck);
                cards[i].setBounds(Sluts.getCardSlut(i));
                cards[i].setName(String.valueOf(i));
                cards[i].addMouseListener(deckClickListener());
            }
            okButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {
                    new Thread(() -> {
                        label2.remove(plants);
                        label2.remove(deck);
                        label2.remove(okButton);
                        label2.repaint();
                        try {
                            //noinspection ForLoopReplaceableByForEach
                            for (int i = 0; i < cards.length; i++) {
                                cards[i].removeMouseListener(cards[i].getMouseListeners()[0]);
                                cards[i].addMouseListener(cardsClickListener());
                            }
                            Timer t = new Timer(33, ee ->
                                    label2.setBounds(label2.getX() + 10, label2.getY(), 1400, 600));
                            t.start();
                            Thread.sleep(999);
                            t.stop();
                            label2.setBounds(0, 0, 1400, 600);
                            Sounds.mute();
                            Sounds.play(READY);// Play background music
                            remove(label2);
                            readyLabel();

                            Sounds.backPlay(IN_GAME);

                            plantsJob();

                            label.repaint();

                            gameTimer();

                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }).start();
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        });
        n.start();
        try {
            n.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    ArrayList<Integer> cardsList = new ArrayList<>();

    /**
     * add mouse listener when someone clicks on the cards in the deck
     * @return deck mouse click listener
     */
    private MouseListener deckClickListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                JLabel aim = ((JLabel)e.getSource());
                if (TESTING) System.out.println("Clicked On Seed Number " + aim.getName());
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
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    /**
     * shows ready set plant label on the background just before the first round starts
     * @throws InterruptedException called when sleep method called
     */
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

    /**
     * send zombies to the field
     */
    private void sendZombie() {
        Random random = new Random();
        new Thread(() -> {
            int[] location;
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
            if (zombie.getClass() == Newspaper.class || zombie.getClass() == PoleVaulting.class)
                zombie.setBounds(location[0], location[1] - 110, zombie.sizeX, zombie.sizeY);
            else
                zombie.setBounds(location[0], location[1] - 40, zombie.sizeX, zombie.sizeY);
            objects.add(new Coordination(zombie, rand));
            walk(zombie);
        }).start();
    }

    /**
     * adds walking animation to the zombies
     * also checks whether the zombie reaches the end or the plant to execute the next method
     * @param zombie the class of the subject zombie
     */
    @SuppressWarnings("RedundantCast")
    private synchronized void walk(Zombie zombie) {
        int delay = walkDelay[difficulty];
        if (zombie.getClass() == BucketHead.class
                || zombie.getClass() == ConeHead.class) {
            delay -= 10;
        }
        Timer t = new Timer(delay, e -> {
            if (zombie.health > 0) {
                zombie.setBounds(zombie.getX() - zombie.speed, zombie.getY(), zombie.sizeX, zombie.sizeY);
                int distance = 190;
                if (zombie.getClass() == PoleVaulting.class)
                    distance = 0;
                else if (zombie.getClass() == ConeHead.class
                        || zombie.getClass() == BucketHead.class
                        || zombie.getClass() == Newspaper.class)
                    distance = 90;
                if (zombie.getX() - distance < 40) {
                    if (mowerAvailable[zombie.row])
                        runMower(zombie.row);
                    else {
                        try {
                            if (Zombie.zombies.contains(zombie) && zombie.getX() - distance < 5)
                                lose();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
                for (Plant plant : Plant.plants) {
                    if (plant.row == zombie.row) {
                        int dis = 0;
                        if (zombie.getClass() == PoleVaulting.class)
                            dis = 200;
                        else if  ( zombie.getClass() == BucketHead.class
                                || zombie.getClass() == ConeHead.class)
                            if (zombie.getIcon() != Icons.normalZombie)
                                dis = 60;
                        if (zombie.getClass() == Newspaper.class) dis = 60;
                        if (zombie.getClass() == PoleVaulting.class) {
                            if (!((PoleVaulting) zombie).isJumping) {
                                if (zombie.getX() + dis - plant.getX() < 140 && zombie.getX() + dis - plant.getX() > 0)
                                    if (!((PoleVaulting) zombie).jumped) {
                                        new Thread(() -> {
                                            try {
                                                ((PoleVaulting) zombie).jumped = true;
                                                ((PoleVaulting) zombie).isJumping = true;
                                                Sounds.play(VAULT);
                                                zombie.setIcon(((PoleVaulting) zombie).jump1);
                                                zombie.speed = 6;
                                                Thread.sleep(1250);
                                                zombie.setIcon(((PoleVaulting) zombie).jump2);
                                                zombie.setBounds(zombie.getX() - 140, zombie.getY(),
                                                        zombie.getIcon().getIconWidth(),
                                                        zombie.getIcon().getIconHeight());
                                                zombie.speed = 10;
                                                Thread.sleep(300);
                                                zombie.speed = 0;
                                                Thread.sleep(500);
                                                zombie.setIcon(((PoleVaulting) zombie).noPole);
                                                zombie.speed = 2;
                                                ((PoleVaulting) zombie).isJumping = false;
                                            } catch (InterruptedException interruptedException) {
                                                interruptedException.printStackTrace();
                                            }
                                        }).start();

                                    }
                                if (((PoleVaulting) zombie).jumped) {
                                    if (zombie.getX() + dis - plant.getX() > 0)
                                        if (zombie.getX() + dis - plant.getX() < 40) {
                                            eatPlant(zombie, plant);
                                            ((Timer) e.getSource()).stop();
                                            timerPool.remove(((Timer) e.getSource()));
                                        }
                                }
                            }
                        } else {
                            if (zombie.getX() + dis - plant.getX() < 20 && zombie.getX() + dis - plant.getX() > 0) {
                                eatPlant(zombie, plant);
                                ((Timer) e.getSource()).stop();
                                timerPool.remove(((Timer) e.getSource()));
                            }
                        }
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

    /**
     * called by walk method when the zombie reaches the plant
     * @param zombie the subject
     * @param victim the object
     */
    private void eatPlant(Zombie zombie, Plant victim) {
        Thread t = new Thread( () -> {
            int i;
            if (zombie.getClass() == Normal.class) i = 0;
            else if (zombie.getClass() == ConeHead.class) i = 1;
            else if (zombie.getClass() == BucketHead.class) i = 2;
            else if (zombie.getClass() == Football.class) i = 3;
            else if (zombie.getClass() == PoleVaulting.class) i = 4;
            else if (zombie.getClass() == Newspaper.class) i = 5;
            else i = -1;
            int amount = zombie.damage + additionalDamage[i] * difficulty;
            System.out.println(amount);
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
        });
        t.start();
    }

    /**
     * pauses the game
     * stops all of the timers
     */
    private void pause() {
        for (Timer timer: timerPool) timer.stop();
        muted = false;
        paused = true;
    }

    /**
     * resumes the game and all of its timers
     */
    void resume() {
        for (Timer timer: timerPool) timer.start();
        blackScreen.setIcon(null);
        remove(blackScreen);
        paused = false;
        gameTimer();
        pauseButton.addMouseListener(pauseClickListener(pauseButton));
    }

    public static void main(String[] args) {
        if (Player.load() == null)
            new Game(new Player(), false);
        else {
            ArrayList<Player> player= Player.load();
            assert player != null;
            new Game(player.get(0), false);
        }
    }

    public GameSave save() {
        ArrayList<GameObjects> objects = new ArrayList<>();
        for (int i = 0; i < Game.objects.size(); i++) {
            String name;
            Point position;
            int health;
            if (Game.objects.get(i).type == 0) {
                name = Game.objects.get(i).plant.getClass().getName();
                name = name.substring(name.lastIndexOf(".") + 1);
                position = new Point(Game.objects.get(i).plant.getBounds().x, Game.objects.get(i).plant.getBounds().y);
                health = Game.objects.get(i).plant.health;
            } else {
                name = Game.objects.get(i).zombie.getClass().getName();
                name = name.substring(name.lastIndexOf(".") + 1);
                position = new Point(Game.objects.get(i).zombie.getBounds().x, Game.objects.get(i).zombie.getBounds().y);
                health = Game.objects.get(i).zombie.health;
                if (Game.objects.get(i).zombie.getClass() == Newspaper.class
                        || Game.objects.get(i).zombie.getClass() == PoleVaulting.class)
                    position.y += 110;
                else
                    position.y += 40;
            }
            GameObjects temp = new GameObjects(name, position, health);
            objects.add(temp);
        }
        return new GameSave(objects, gameTime, cardsList, suns);
    }
}