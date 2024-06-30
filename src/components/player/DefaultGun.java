package components.player;

import java.awt.*;
import java.util.ArrayList;
import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

public class DefaultGun extends Entity implements IPowerup, IGun {
    private double projectile_radius;
    private double end_powerup;
    private Boolean ativo;

    public DefaultGun(){
        super(States.ACTIVE, GameLib.WIDTH / 2, GameLib.HEIGHT / 3,
                0, 0, 0,
                0, 100, 10);
        projectile_radius = 2;
        this.ativo = true;
        this.end_powerup = Double.MAX_VALUE;
    }

    public ArrayList<Projectile> shoot(long currentTime, double player_x,
                                   double player_y, double player_radius){
        this.next_shot = currentTime + 100;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        Projectile projectile = new Projectile(this.projectile_radius, player_x,
         player_y - 2 * player_radius, 0, -1);
        projectiles.add(projectile);
        return projectiles;
    }

    public long getNextShoot() {
        return this.next_shot;
    }

    public void walk(long delta) {
        
    }

    public Boolean exploded(long currentTime) {
        return true;
    }

    public Boolean leaveScreen() {
        return getPosY() > GameLib.HEIGHT + 10;
    }

    public double getEndPowerUp() {
        return this.end_powerup;
    }

    public Boolean isActive() {
        return ativo;
    }

    public void apply(long currentTime, Player player) {
        this.ativo = true;
        this.state = States.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + 100;
        this.walkX(2000);
        this.walkY(2000);
        player.setGun(this);
        System.out.println("CONSEGUI UMA ARMA!!!!!!!!");
    }

    public void remove(Player player) {
        this.ativo = false;
        this.end_powerup = 0;
        System.out.println("perdi a arma...");
    }

    public void draw(double currentTime) {
        if(this.state == States.EXPLODING){
            if(this.explosion_end < currentTime){
                this.state = States.INACTIVE;
                return;
            }
            double alpha = (currentTime - this.explosion_start)
                    / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else if(this.state != States.INACTIVE){
            GameLib.setColor(Color.GREEN);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
