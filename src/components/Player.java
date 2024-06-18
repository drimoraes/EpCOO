package components;

import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {
    private double projectile_radius = 2;
    public Player(double entityPosX, double entityPosY, double entitySpeedX, double entitySpeedY,
                  double explosion_start, double explosion_end, long next_shot, double radius){
        super(States.ACTIVE, entityPosX, entityPosY, entitySpeedX, entitySpeedY,
        explosion_start, explosion_end, next_shot, radius);
    }

    public double getNextShoot(){
        return next_shot;
    }

    public void CheckMoviment(long delta){
        if(GameLib.iskeyPressed(GameLib.KEY_UP) && getPosY() > 25)
            walkY(delta* getSpeedY()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_DOWN) && getPosY() < GameLib.HEIGHT - 10)
            walkY(delta*getSpeedY());
        if(GameLib.iskeyPressed(GameLib.KEY_LEFT) && getPosX() > 5)
            walkX(delta*getSpeedX()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_RIGHT) && getPosX() < GameLib.WIDTH - 10)
            walkX(delta* getSpeedX());
        //System.out.print(" ");
        //System.out.println(getPosY());
        //System.out.print(getPosX());
    }

    public ArrayList<Projectile> Shoot(long currentTime){
        this.next_shot = currentTime + 100;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        Projectile projectile = new Projectile(projectile_radius, getPosX(),
                getPosY() - 2 * this.radius, 0, -1);
        projectiles.add(projectile);
        return projectiles;
    }

    public void kill(long currentTime){
        this.state = States.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + 2000;
    }

    public void draw(double currentTime){
        if(this.getState() == States.EXPLODING){
            double alpha = (currentTime - this.getExplosionStart())
                    / (this.getExplosionEnd() - this.getExplosionStart());
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else{
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }

    public void checkRevive(long currentTime){
        if(this.state == States.EXPLODING){
            if(currentTime > this.explosion_end){
                this.state = States.ACTIVE;
            }
        }
    }
}
