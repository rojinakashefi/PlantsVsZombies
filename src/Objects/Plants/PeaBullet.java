package Objects.Plants;

import Menus.Game;
import Miscs.Sluts;
import Objects.Zombies.Zombie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Main.Main.TESTING;

@SuppressWarnings("ALL")
public class PeaBullet extends JLabel {
    boolean isMoving = false;
    public PeaBullet (Container c, Plant origin, boolean isFrozen) {
        //c.add(this);
        seekForZombies(c, origin, isFrozen);
    }

    private void seekForZombies(Container c, Plant origin, boolean isFrozen) {


    }
}
