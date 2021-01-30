package Menus;


import Main.Main;

import javax.swing.*;
import java.awt.*;

public class RankingMenu extends JFrame {

    public RankingMenu() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        this.setSize(480, 465);
        this.setLocationRelativeTo(null);
        Container This = getContentPane();
        if (!Main.loadedPlayers.isEmpty()) {
            String[] columns = new String[] {
                    "Row", "Name", "Score", "Wins", "Losses"
            };

            Object[][] data = new Object[Main.loadedPlayers.size()][5];
            for (int i = 0; i < data.length; i++) {
                data[i][0] = i + 1;
                data[i][1] = Main.loadedPlayers.get(i).username;
                data[i][2] = Main.loadedPlayers.get(i).score;
                data[i][3] = Main.loadedPlayers.get(i).wins;
                data[i][4] = Main.loadedPlayers.get(i).losses;
            }

            JTable table = new JTable(data, columns);

            this.add(new JScrollPane(table));


        } else {
            JLabel label = new JLabel("No Data Available");
            this.add(label);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, This);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, label, 0, SpringLayout.VERTICAL_CENTER, This);
        }

    }
}
