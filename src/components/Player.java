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

    public void verificaPosicao(){
        if(this.position.getPosX() < 0.0) this.position.setPosX(0.0);
        if(this.position.getPosX() >= GameLib.WIDTH) this.position.setPosX(GameLib.WIDTH - 1);
        if(this.position.getPosY() < 25.0) this.position.setPosY(25.0);
        if(this.position.getPosY() >= GameLib.HEIGHT) this.position.setPosY(GameLib.HEIGHT - 1);
    }

    public double getYPosition(){
        return position.getPosY();
    }

    public double getNextShoot(){
        return next_shot;
    }

    public void CheckMoviment(long delta){
        if(GameLib.iskeyPressed(GameLib.KEY_UP)) this.position.walkY(delta*this.position.getSpeedY()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) this.position.walkY(delta*this.position.getSpeedY());
        if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) this.position.walkX(delta*this.position.getSpeedX()*-1);
        if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) this.position.walkX(delta*this.position.getSpeedX());

//        if(player_X < 0.0) player_X = 0.0;
//        if(player_X >= GameLib.WIDTH) player_X = GameLib.WIDTH - 1;
//        if(player_Y < 25.0) player_Y = 25.0;
//        if(player_Y >= GameLib.HEIGHT) player_Y = GameLib.HEIGHT - 1;
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
