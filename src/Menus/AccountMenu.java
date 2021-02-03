package Menus;
import Main.Main;
import Miscs.Player;
import Miscs.Socket.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static Main.Main.*;

/**
 * AccountMenu as first Menu appears when we login in game
 * Contains Gui and logic For sign in/signup/create account/have account.
 * @author Hashemipour
 * @version 1.0
 * @since 2021
 */
public class AccountMenu extends JFrame implements Runnable {
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
    Client account;



    /**
     * Constructor starts from receiving data of players.
     * Setting font of buttons
     */
    public AccountMenu() {
        new Thread(this::receive).start();
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        SignInButton.setFont(font);
        SignUpButton.setFont(font);
        SecLabel.setFont(font);
        LastLBL.setFont(font);
        NameLBL.setFont(font);
        ChangeUpButton.setFont(font);
        ChangeInButton.setFont(font);
        //checking look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(420, 280);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //adding window listener for accountmenu window
        //using adapter class
        //implementing account.close
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                account.close();
            }
            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
    /**
     * Receives players data (name,score,wins,losses,difficulty)from server
     * adding the player to loadedPlayers.
     */
    private void receive() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account = new Client("Accounts");
        account.send("Accounts");
        account.send("Scoreboard");
        account.send("0");
        String[] request = account.receive();
        if (request.length > 1) {
            for (int i = 1; i < request.length;) {
                String name = request[i++];
                int score = Integer.parseInt(request[i++]);
                int wins = Integer.parseInt(request[i++]);
                int losses = Integer.parseInt(request[i++]);
                int difficulty = Integer.parseInt(request[i++]);
                Player level = new Player(difficulty, wins, losses, score, name);
                Main.loadedPlayers.add(level);
            }
        }
    }

    /**
     * Adding signup here button gui which contains haveAccount,Signup,Sign in Here.
     */
    private void Up() {
        while (this.getHeight() != 300) {
            this.setSize(this.getWidth(), this.getHeight() + 2);
            this.setLocation(getX(), getY() - 1);
        }
        this.remove(ChangeUpButton);
        this.remove(SignInButton);
        SecLabel.setText("Sign Up");
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

    /**
     * Signing in logic after clicking sign in button.
     */
    private void signIn() {
        if (!NameTxt.getText().equals("")) {
            int index = findPlayerIndex(NameTxt.getText());
            if (index != -1) {
                //after siging in mainmenu opens
                new MainMenu(loadedPlayers.get(index));
                dispose();
            } else new JOptionPane("User Not Found!", JOptionPane.INFORMATION_MESSAGE)
                    .createDialog("").setVisible(true);
        } else {
            new JOptionPane("Enter a name!", JOptionPane.ERROR_MESSAGE)
                    .createDialog("Error!").setVisible(true);
        }
    }

    /**
     * signing up logic as a new player after clicking sign up button.
     */
    private void signUp() {
        int index = findPlayerIndex(NameTxt.getText());
        if (index == -1) {
            Player level = new Player(0,0,0,0, NameTxt.getText());
            loadedPlayers.add(level);
            //after signing up,mainmenu will open
            new MainMenu(level);
            dispose();
        } else {
            new JOptionPane("Username is already in use!", JOptionPane.INFORMATION_MESSAGE)
                    .createDialog("").setVisible(true);
        }
    }

    /**
     * Sign in panel(first panel when we open account menu)
     * contains sign in button,dont have account,sign up here button
     */
    private void In() {
        while (this.getHeight() != 280) {
            this.setSize(this.getWidth(), this.getHeight() - 2);
            this.setLocation(getX(), getY() + 1);
        }
        SecLabel.setText("Sign In");
        LastLBL.setText("New To Game?");
        this.add(SecLabel);
        this.add(NameLBL);
        this.add(NameTxt);
        this.add(SignInButton);
        this.add(ChangeUpButton);
        this.add(LastLBL);
        this.remove(ChangeInButton);
        this.remove(SignUpButton);
        this.remove(ChangeInButton);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, SecLabel, 0, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.NORTH, SecLabel, 10, SpringLayout.SOUTH, This);
        Layout.putConstraint(SpringLayout.SOUTH, ChangeUpButton, -20, SpringLayout.SOUTH, This);
        Layout.putConstraint(SpringLayout.WEST, ChangeUpButton, 5, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.SOUTH, SignInButton, -50, SpringLayout.NORTH, ChangeUpButton);
        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, SignInButton, 0, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.VERTICAL_CENTER, LastLBL, 0, SpringLayout.VERTICAL_CENTER, ChangeUpButton);
        Layout.putConstraint(SpringLayout.EAST, LastLBL, -5, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.WEST, NameTxt, -20, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.VERTICAL_CENTER, NameTxt, 0, SpringLayout.VERTICAL_CENTER, NameLBL);
        Layout.putConstraint(SpringLayout.EAST, NameLBL, -30, SpringLayout.HORIZONTAL_CENTER, This);
        Layout.putConstraint(SpringLayout.SOUTH, NameLBL, -45, SpringLayout.NORTH, SignInButton);
    }

    /**
     * Runs the new Thread to show the account page.
     */
    @Override
    public void run() {
        this.setLayout(Layout);
        //first gui which we see
        In();
        this.setVisible(true);
        System.out.println("Account Page");
        if (TESTING) System.out.println("Active Threads InPage: " + Thread.activeCount());
        SignInButton.addActionListener(e -> signIn());
        ChangeUpButton.addActionListener(e -> Up());
        ChangeInButton.addActionListener(e -> In());
        SignUpButton.addActionListener(e -> signUp());
    }
}
