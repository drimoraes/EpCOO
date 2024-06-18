package components.player;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;

public class Speed extends Entity implements IPowerup {

    private double angleSpeed;
    private double angle;
    private static long nextPowerUp = System.currentTimeMillis() + 4000;
    
    public Speed (Long currentTime) {
        super (States.ACTIVE, (GameLib.WIDTH)/Math.random(), (GameLib.HEIGHT)/Math.random(),
        0, 0.20 + Math.random() * 0.15,0,0,
        0, 5);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        Speed.nextPowerUp = currentTime + 1000;
    }

    public void Andar(long delta) {
        walkX(getSpeedX() * Math.cos(this.angle) * delta);
        walkY(getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;
    }

    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    public Boolean leaveScreen(){
        return getPosY() > GameLib.HEIGHT + 10;
    }

    public static Long getNextPowerUp(){
        return nextPowerUp;
    }

    public void apply(long currenTime, Player player){
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 500;
        //player.setSpeedX += player.getSpeedX() * 0.3;
        //player.setSpeedY += player.getSpeedY() * 0.3;

    }

    public void draw(double currentTime){
        if(this.state == States.EXPLODING){
            double alpha = (currentTime - this.explosion_start)
                    / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else{
            GameLib.setColor(Color.pink);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
