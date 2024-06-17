package components;

import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.util.ArrayList;

public class Player extends Entity {
    private double projectile_radius = 2;
    public Player(States state, double entityPosX, double entityPosY, double entitySpeedX, double entitySpeedY,
                  double explosion_start, double explosion_end, long next_shot, double radius){
        super(state, entityPosX, entityPosY, entitySpeedX, entitySpeedY,
        explosion_start, explosion_end, next_shot, radius);
    }

    public double getYPosition(){
        return position.getPosY();
    }

    public double getNextShoot(){
        return next_shot;
    }

    public void CheckMoviment(long delta){
        if(GameLib.iskeyPressed(GameLib.KEY_UP) && this.position.getPosY() > 25)
            this.position.walkY(delta*this.position.getSpeedY()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_DOWN) && this.position.getPosY() < GameLib.HEIGHT - 10)
            this.position.walkY(delta*this.position.getSpeedY());
        if(GameLib.iskeyPressed(GameLib.KEY_LEFT) && this.position.getPosX() > 5)
            this.position.walkX(delta*this.position.getSpeedX()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_RIGHT) && this.position.getPosX() < GameLib.WIDTH - 10)
            this.position.walkX(delta*this.position.getSpeedX());
    }

    public ArrayList<Projectile> Shoot(long currentTime){
        this.next_shot = currentTime + 100;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        var projectile = new Projectile(projectile_radius, this.position.getPosX(),
                this.position.getPosY() - 2 * this.radius, 0, -1);
        projectiles.add(projectile);
        return projectiles;
    }
}
