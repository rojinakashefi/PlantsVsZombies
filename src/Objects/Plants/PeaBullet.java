package Objects.Plants;

import Menus.Game;
import Objects.Zombies.Zombie;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Container;

public class PeaBullet extends JLabel {
    public PeaBullet (Container c, Plant origin, boolean isFrozen) throws InterruptedException {
        //c.add(this);
        seekForZombies(c, origin, isFrozen);
    }

    private void seekForZombies(Container c, Plant origin, boolean isFrozen) throws InterruptedException {
        if (origin.health > 0) {
            boolean enemyInSight = false;
            for (int i = 0; i < Game.objects.size(); i++) {
                if (Game.objects.get(i).type == 1)
                    if (Game.objects.get(i).coordination[1] == origin.row)
                        if (Game.objects.get(i).zombie.getX() > origin.getX()) {
                            enemyInSight = true;
                            break;
                        }
                Thread.sleep(100);
            }
            if (enemyInSight) {
                c.add(this);
                final PeaBullet This = this;
                final Timer timer = new Timer(10, e -> {
                    setBounds(getX() + 3, getY(), 28, 28);

                });
                timer.start();
                Game.timerPool.add(timer);
                return;
            }
            try {
                Thread.sleep(origin.speed * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seekForZombies(c, origin, isFrozen);
        }
    }
}
