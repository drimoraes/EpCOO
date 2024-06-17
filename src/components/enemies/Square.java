package components.enemies;
import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;
import java.util.ArrayList;

public class Square extends Entity implements IEnemy{
    private double angle;
    private double angleSpeed;
    private double projetileRadius;
    private static long nextSquare = 0;
    private static double squareCounter = 0;

    public Square(long currentTime, double spawnX){
        super(States.ACTIVE, spawnX, -10.0,
                0,0.42,0,0,
                currentTime + 500, 12);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.projetileRadius = 2.0;
        // usar na lista nextEnemy1 = currentTime + 500;
    }

    public static long getNextSquare(){
        return nextSquare;
    }

    public static Square CreateSquare(long currentTime){
        if (squareCounter < 10){
            squareCounter++;
            nextSquare = currentTime + 120;
            return new Square(currentTime, GameLib.WIDTH * 0.20);
        }
        squareCounter = 0;
        nextSquare = (long) (currentTime + 3000 + Math.random() * 3000);
        return new Square(currentTime, Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
    }


    public void Andar(long delta) {
        position.walkX(position.getSpeedX() * Math.cos(this.angle) * delta);
        position.walkY(position.getSpeedY() * Math.sin(this.angle) * delta * (-1.0));
        this.angle += this.angleSpeed * delta;
    }

    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    public Boolean leaveScreen(){
        return this.position.getPosX() < -10 || this.position.getPosX() > GameLib.WIDTH + 10;
    }

    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

        ArrayList<Projectile> projectiles = new ArrayList<>();

        for(double angle : angles){
            double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
            double projX = this.position.getPosX();
            double projY = this.position.getPosY();
            double projSpeedX = Math.cos(a) * 0.30;
            double projSpeedY = Math.sin(a) * 0.30;
            projectiles.add(new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY));
        }
        this.next_shot = (long) (currentTime + 200 +Math.random() * 500);
        return projectiles;
    }

    public Boolean getNextShoot(long currentTime){
        if(this.angleSpeed > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05){
            this.angleSpeed = 0.0;
            this.angle = 3 * Math.PI;
            return true;
        }
        if(this.angleSpeed < 0 && Math.abs(this.angle) < 0.05){
            this.angleSpeed = 0.0;
            this.angle = 0.0;
            return true;
        }
        return false;
    }

    public double getPosY(){
        return this.position.getPosY();
    }
    public double getPosX(){ return this.position.getPosX(); }
    public double getRadius(){ return this.radius; }
    public States getState(){
        return this.state;
    }
    public double getExplosionEnd(){
        return this.explosion_end;
    }
    public void kill(long currenTime){
        this.state = States.EXPLODING;
        this.explosion_start = currenTime;
        this.explosion_end = currenTime + 500;
    }
    public void drawEnemy(double currentTime){
        if(this.state == States.EXPLODING){
            double alpha = (currentTime - this.explosion_start)
                    / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else{
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
