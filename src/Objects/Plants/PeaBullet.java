package Objects.Plants;

import Menus.Game;
import Miscs.Icons;
import Miscs.Sluts;
import Objects.Zombies.*;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.*;

import static Menus.Game.timerPool;

public class PeaBullet extends JLabel {

    public PeaBullet (Container c, Plant origin, boolean isFrozen) throws InterruptedException {
        seekForZombies(c, origin, isFrozen, false);
    }

    public PeaBullet(JLabel label, Plant shooterPlant) throws InterruptedException {
        seekForZombies(label, shooterPlant, false, true);
    }

    private void seekForZombies(Container c, Plant origin, boolean isFrozen, boolean isTriple) throws InterruptedException {
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
                if (isTriple) {
                    JLabel[] obliques = new JLabel[2];
                    for (int i = 0; i < 2; i++) {
                        obliques[i] = new JLabel();
                        obliques[i].setIcon(Icons.peaBulletIcon);
                        obliques[i].setBounds(origin.getBounds().x + 46, origin.getBounds().y + 16, 28, 28);
                        c.add(obliques[i]);
                        int dir = i==0?-2:2;
                        final JLabel oblique = obliques[i];
                        Timer t = new Timer(9, e -> {
                            oblique.setBounds(oblique.getX() + 2, oblique.getY() + dir, 28, 28);
                            Zombie firstZombie = Game.getFirstZombieByRow(oblique);
                            if (firstZombie != null) {
                                int zombieX = firstZombie.getBounds().x;
                                if(firstZombie.getClass() == PoleVaulting.class)
                                    zombieX += 200;
                                else if (firstZombie.getClass() == ConeHead.class
                                        || firstZombie.getClass() == BucketHead.class
                                        || firstZombie.getClass() == Newspaper.class)
                                    zombieX += 60;
                                if (zombieX - oblique.getBounds().x < 20
                                        && firstZombie.row == Sluts.getYSlut(oblique.getBounds())) {
                                    c.remove(oblique);
                                    firstZombie.lossHealth(30, false);
                                    if (firstZombie.health <= 0) return;
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        });
                        t.start();
                        timerPool.add(t);
                    }
                }
                c.add(this);
                final JLabel This = this;
                final Timer timer = new Timer(5, e -> {
                    setBounds(getX() + 2, getY(), 28, 28);
                    Zombie firstZombie = Game.getFirstZombieByRow(origin);
                    if (firstZombie != null) {
                        int zombieX = firstZombie.getBounds().x;
                        if(firstZombie.getClass() == PoleVaulting.class)
                            zombieX += 220;
                        else if (firstZombie.getClass() == ConeHead.class
                                || firstZombie.getClass() == BucketHead.class
                                || firstZombie.getClass() == Newspaper.class)
                            zombieX += 60;
                        if (zombieX - This.getBounds().x < 20) {
                            c.remove(This);
                            firstZombie.lossHealth(isFrozen ? 35 : 30, isFrozen);
                            if (firstZombie.health <= 0) return;
                            ((Timer) e.getSource()).stop();
                        }
                    }
                });
                timer.start();
                timerPool.add(timer);
                return;
            }
            try {
                Thread.sleep(origin.speed * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seekForZombies(c, origin, isFrozen, isTriple);
        }
    }
}
