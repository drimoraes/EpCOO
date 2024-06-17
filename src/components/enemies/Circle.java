package components.enemies;
import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;
import java.util.ArrayList;

public class Circle extends Entity implements IEnemy{
    private double angle;
    private double angleSpeed;
    private double projetileRadius;
    private static long nextEnemy = 0;

    public Circle(long currentTime){
        super(States.ACTIVE, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0,
                0,0.20 + Math.random() * 0.15,0,0,
                currentTime + 500, 9);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.projetileRadius = 2.0;
        Circle.nextEnemy = currentTime + 500;
        // usar na lista nextEnemy1 = currentTime + 500;
    }

    public static long getNextEnemy(){
        return nextEnemy;
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
        return this.position.getPosY() > GameLib.HEIGHT + 10;
    }

    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        ArrayList<Projectile> projectiles = new ArrayList<>();

        double projX = this.position.getPosX();
        double projY = this.position.getPosY();
        double projSpeedX = Math.cos(this.angle) * 0.45;
        double projSpeedY = Math.sin(this.angle) * 0.45 * (-1.0);
        this.next_shot = (long) (currentTime + 200 +Math.random() * 500);

        Projectile proj = new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY);
        projectiles.add(proj);
        return projectiles;
    }

    public Boolean getNextShoot(long currentTime){
        return this.next_shot > currentTime;
    }
    public double getPosY(){
        return this.position.getPosY();
    }
    public double getPosX(){
        return this.position.getPosX();
    }
    public double getRadius(){
        return this.radius;
    }
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
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(this.getPosX(), this.getPosY(), this.getRadius());
        }
    }
}
