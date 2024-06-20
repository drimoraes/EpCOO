package components.player;

import components.Projectile;
import mainpackage.States;

import java.util.ArrayList;

interface IGun {
    ArrayList<Projectile> shoot(long currentTime, double player_x,
                                double player_y, double player_radius);
    long getNextShoot();
    void remove(Player player);
}
