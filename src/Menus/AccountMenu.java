package Menus;

import javax.swing.*;
import java.awt.*;

public class AccountMenu extends JFrame implements Runnable  {
    JLabel SecLabel = new JLabel("Enter Game");
    JButton SignInButton = new JButton("Sign In");
    JButton SignUpButton = new JButton("Sign Up");
    JButton ChangeUpButton = new JButton("Sign Up Now!");
    JButton ChangeInButton = new JButton("Sign In Now!");
    JLabel NameLBL = new JLabel("Name");
    JTextField NameTxt = new JTextField("", 10);
    JLabel LastLBL = new JLabel("New To Game?");
    SpringLayout Layout = new SpringLayout();
    Container This = this.getContentPane();

    public AccountMenu(){
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        SignInButton.setFont(font);
        SignUpButton.setFont(font);
        SecLabel.setFont(font);
        LastLBL.setFont(font);
        NameLBL.setFont(font);
        ChangeUpButton.setFont(font);
        ChangeInButton.setFont(font);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(420, 280);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override
    public void run() {

    }
    private void Up() {
        while (this.getHeight() != 300) {
            this.setSize(this.getWidth(), this.getHeight() + 2);
            this.setLocation(getX(), getY() - 1);
        }
        this.remove(ChangeUpButton);
        this.remove(SignInButton);
        LastLBL.setText("Have Account?");
        NameTxt.setText("");
        this.add(NameTxt);
        this.add(NameLBL);
        this.add(SignUpButton);
        this.add(ChangeInButton);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, ChangeInButton, 45, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.SOUTH, ChangeInButton, -20, SpringLayout.SOUTH, This);
        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, SignUpButton, 0, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.SOUTH, SignUpButton, -40, SpringLayout.NORTH, ChangeInButton);
        Layout.putConstraint(SpringLayout.EAST, LastLBL, -15, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.VERTICAL_CENTER, LastLBL, 0, SpringLayout.VERTICAL_CENTER, ChangeInButton);

        Layout.putConstraint(SpringLayout.EAST, NameLBL, -30, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.SOUTH, NameLBL, -45, SpringLayout.NORTH, SignUpButton);
        Layout.putConstraint(SpringLayout.WEST, NameTxt, -20, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.VERTICAL_CENTER, NameTxt, 0, SpringLayout.VERTICAL_CENTER, NameLBL);
    }

}
