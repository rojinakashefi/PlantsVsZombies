package Menus;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
