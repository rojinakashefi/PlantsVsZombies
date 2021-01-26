package Menus;

import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel quitButton = new JLabel();
    JLabel saveButton = new JLabel();
    Game mainGame;
}
