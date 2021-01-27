package Menus;

import javax.swing.*;
import java.awt.*;

public class SettingMenu extends JFrame{
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel modeHButton = new JLabel();
    JLabel modeNButton = new JLabel();
    JLabel nameLBL = new JLabel("UserName :");
    JTextField nameTXT;
    MainMenu menu;
}
