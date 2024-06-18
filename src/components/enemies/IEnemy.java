package components.enemies;

import components.Projectile;

import java.util.ArrayList;

public interface IEnemy {
    void Andar(long delta);
    Boolean exploded(long currentTime);
    Boolean leaveScreen();
    ArrayList<Projectile> Shoot(long currentTime, long delta);
    Boolean getNextShoot(long currentTime);
    double getPosY();
    double getRadius();
    double getPosX();
    void kill(long currentTime);
    void draw(double currentTime);
}