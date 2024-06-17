package components.enemies;

import components.Projectile;
import mainpackage.States;

import java.util.ArrayList;

public interface IEnemy {
    void Andar(long delta);
    Boolean exploded(long currentTime);
    Boolean leaveScreen();
    ArrayList<Projectile> Shoot(long currentTime, long delta);
    Boolean getNextShoot(long currentTime);
    double getPosY();
    double getPosX();
    double getRadius();
    States getState();
    double getExplosionEnd();
    void kill(long currentTime);
    void drawEnemy(double currentTime);
}