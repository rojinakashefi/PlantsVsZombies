package Menus;

import Main.Main;
import Miscs.*;
import Miscs.Socket.Client;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static Main.Main.*;
import static Miscs.Sounds.*;

/**
 * This class shows the main menu of the game
 *
 * <p>
 * DONE: Background Sound Effect
 *       Main.Main Frame
 * </p>
 *
 */

public class MainMenu extends JFrame {
    public Player player;
    JLabel back, newButton, loadButton, settingsButton, rankingButton;
    Client main;
    public MainMenu(Player player) {
        this.player = player;

        background();

        //Game Page specs
        setSize(860, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("icon.png").getImage());

        Sounds.backPlay(MAIN_MENU);
        setVisible(true);

        if (!addedShotDownHook) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                main.send("Main");
                main.send("Scoreboard");
                if (loadedPlayers != null) {
                    int saveCount = loadedPlayers.size() * 5 + 3;
                    if (!saves.isEmpty()) {
                        int cardsCount = 0, objectsCount = 0;
                        for (GameSave save: saves) {
                            cardsCount += save.cards.size();
                            objectsCount += save.objects.size() * 4;
                        }
                        saveCount += (saves.size() * 4 + (cardsCount + objectsCount));
                    }
                    main.send(String.valueOf(saveCount));
                    main.send(String.valueOf(loadedPlayers.size()));
                    for (Player value : loadedPlayers) {
                        main.send(value.username);
                        main.send(String.valueOf(value.score));
                        main.send(String.valueOf(value.wins));
                        main.send(String.valueOf(value.losses));
                        main.send(String.valueOf(value.difficulty));
                    }
                }
                if (!saves.isEmpty()) {
                    main.send(String.valueOf(saves.size()));
                    for (GameSave save : saves) {
                        main.send(String.valueOf(save.cards.size()));
                        for (Integer card : save.cards) {
                            main.send(String.valueOf(card));
                        }
                        main.send(String.valueOf(save.objects.size()));
                        for (GameObjects object : save.objects) {
                            main.send(object.objectName);
                            main.send(String.valueOf(object.health));
                            main.send(String.valueOf(object.position.x));
                            main.send(String.valueOf(object.position.y));
                        }
                        main.send(String.valueOf(save.gameTime));
                        main.send(String.valueOf(save.suns));
                    }
                } else main.send("0");
                main.send(player.username);
                if (TESTING) System.out.println("Saving Files Before Exit");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
            addedShotDownHook = true;
        }
    }
    private void communicate(Client main) {
        main.send("Main");
        main.send("Scoreboard");
        main.send("1");
        main.send(player.username);
        String[] received = main.receive();
        if (received[0].equals("Scoreboard") && received.length > 1) {
            int i = 1, j = 0;
            int gamesCount = Integer.parseInt(received[i++]);
            for (;j < gamesCount; j++) {
                ArrayList<Integer> cards = new ArrayList<>();
                ArrayList<GameObjects> objects = new ArrayList<>();
                int cardsCount = Integer.parseInt(received[i++]);
                for (int k = 0; k < cardsCount; k++) {
                    cards.add(Integer.valueOf(received[i++]));
                }
                int objectsCount = Integer.parseInt(received[i++]);
                for (int x = 0; x < objectsCount; x++) {
                    String name = received[i++];
                    int health = Integer.parseInt(received[i++]);
                    Point point = new Point(Integer.parseInt(received[i++]), Integer.parseInt(received[i++]));
                    GameObjects temp = new GameObjects(name, point, health);
                    objects.add(temp);
                }
                saves.add(new GameSave(objects, Integer.parseInt(received[i++]), cards,
                        Integer.parseInt(received[i++])));
            }
        }
    }
    private void background() {
        back = new JLabel();
        back.setIcon(Icons.firstIcon);
        back.setBounds(0, 0, 860, 460);
        this.add(back);
        newButton = new JLabel();
        newButton.setIcon(Icons.buttonIcon);
        SpringLayout layout = new SpringLayout();
        JLabel n = new JLabel();
        newButton.setLayout(layout);
        n.setText("New Game");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, n, 0, SpringLayout.HORIZONTAL_CENTER, newButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, n, 0, SpringLayout.VERTICAL_CENTER, newButton);

        back.add(newButton);
        newButton.add(n);
        newButton.setBounds(360, 340, 100, 45);
        newButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                newButton.setIcon(new ImageIcon("gfx/buttonHover.pvz"));
                mute();
                new Thread(() -> new Game(player, muted)).start();
                dispose();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                newButton.setIcon(Icons.buttonIcon);
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        loadButton = new JLabel();
        boolean loaded = Player.load() != null;
        if (loaded)
            loadButton.setIcon(Icons.buttonIcon);
        else
            loadButton.setIcon(new ImageIcon("gfx/buttonOff.pvz"));
        loadButton.setBounds(360, 390, 100, 45);
        JLabel m = new JLabel();
        loadButton.setLayout(layout);
        m.setText("Load Game");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, m, 0, SpringLayout.HORIZONTAL_CENTER, loadButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, m, 0, SpringLayout.VERTICAL_CENTER, loadButton);

        back.add(loadButton);
        loadButton.add(m);
        loadButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if (loaded) {
                    loadButton.setIcon(new ImageIcon("gfx/buttonHover.pvz"));
                    m.setText("Loading...");
                    repaint();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(loaded) loadButton.setIcon(Icons.buttonIcon);
                mute();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                new Thread(() -> new Game(player, muted)).start();
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        settingsButton = new JLabel();
        settingsButton.setIcon(Icons.buttonIcon);
        settingsButton.setBounds(743, 415, 100, 45);
        settingsButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                showSettings();
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        JLabel z = new JLabel();
        settingsButton.setLayout(layout);
        z.setText("Settings");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, z, 0, SpringLayout.HORIZONTAL_CENTER, settingsButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, z, 0, SpringLayout.VERTICAL_CENTER, settingsButton);

        back.add(settingsButton);
        settingsButton.add(z);

        rankingButton = new JLabel();
        rankingButton.setIcon(Icons.buttonIcon);
        rankingButton.setBounds(1, 415, 100, 45);
        rankingButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                new Thread(RankingMenu::new).start();
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        JLabel w = new JLabel();
        rankingButton.setLayout(layout);
        w.setText("Ranking");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, w, 0, SpringLayout.HORIZONTAL_CENTER, rankingButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, w, 0, SpringLayout.VERTICAL_CENTER, rankingButton);

        back.add(rankingButton);
        rankingButton.add(w);
    }

    private void showSettings() {
        new SettingMenu(this);
    }
}
