package Menus;

import Main.Main;
import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    public Levels levels;
    JLabel back, newButton, loadButton, settingsButton, rankingButton;
    public MainMenu(Levels levels) {

        setVisible(true);
        this.levels = levels;
        background();

        //Game Page specs
        setSize(860, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("icon.png").getImage());
        Sounds.backPlay(MAIN_MENU);

        Runtime.getRuntime().addShutdownHook(new Thread( () -> Levels.save(Main.loadedPlayers)));
    }

    private void background() {
        SpringLayout layout = new SpringLayout();

        back = new JLabel();
        back.setIcon(Icons.firstIcon);
        back.setBounds(0, 0, 860, 460);
        this.add(back);

        newButton = new JLabel();
        newButton.setIcon(Icons.buttonIcon);
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
                new Thread(() -> new Game(levels, muted)).start();
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
        boolean loaded = Levels.load() != null;
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
                    mute();
                    new Thread(() -> new Game(new Levels(), muted)).start();
                    dispose();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(loaded) loadButton.setIcon(Icons.buttonIcon);
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
