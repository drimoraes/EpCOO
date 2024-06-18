package components.enemies;

import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;
import java.util.ArrayList;

public class BigSquare extends Entity implements IEnemy {
    private double angle;
    private double angleSpeed;
    private double projetileRadius;
    protected static long nextSquare = System.currentTimeMillis();
    private ArrayList<Projectile> projectiles;
    protected static Boolean spawned = false;
    private double direction = -1;
    private double life;

    public BigSquare(long currentTime){
        super(States.ACTIVE, GameLib.WIDTH / 2, 100.0,
                0.3,0.5,0,0,
                currentTime + 500, 40);
        this.angle = 3 * Math.PI / 2;
        this.angleSpeed = 0;
        this.projetileRadius = 4.0;
        spawned = true;
        this.projectiles = new ArrayList<>();
        this.life = 100;
        // usar na lista nextEnemy1 = currentTime + 500;
    }

    public static Boolean alreadySpawned(){
        return spawned;
    }

    public static double getNextBigSquare(){
        return nextSquare;
    }

    public void Andar(long delta) { //
        if(getPosX() > GameLib.WIDTH - 40){
            this.walkY(getSpeedY() * delta * 20 * (-1));
            this.direction = -1;
            System.out.print(getPosX());
            System.out.print(" ");
            System.out.println(getPosY());
        }
        else if(getPosX() < 40){
            this.walkY(getSpeedY() * delta * 20);
            this.direction = 1;
            System.out.print(getPosX());
            System.out.print(" ");
            System.out.println(getPosY());
        }
        walkX(getSpeedX() * delta * this.direction);
    }

    public Boolean exploded(long currentTime) {
        return this.state == States.EXPLODING && currentTime > this.explosion_end;
    }

    public Boolean leaveScreen(){
        return getPosX() < -10 || getPosX() > GameLib.WIDTH + 10;
    }

    public ArrayList<Projectile> Shoot(long currentTime, long delta) {
        double [] angles = {
                Math.PI/2 - 2 * Math.PI/8,
                Math.PI/2 + Math.PI/8,
                Math.PI/2,
                Math.PI/2 - Math.PI/8,
                Math.PI/2 - 2 * Math.PI/8
        };

        ArrayList<Projectile> projectiles = new ArrayList<>();
        for(double angle : angles){
            double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
            double projX = getPosX();
            double projY = getPosY();
            double projSpeedX = Math.cos(a) * 0.30;
            double projSpeedY = Math.sin(a) * 0.80;
            projectiles.add(new Projectile(this.projetileRadius, projX, projY, projSpeedX, projSpeedY));
        }
        this.next_shot = (long) (currentTime + 200 +Math.random() * 500);
        return projectiles;
    }
    public Boolean getNextShoot(long currentTime){
        return next_shot < currentTime;
    }

    public void kill(long currenTime){
        if (life > 0){
            life--;
        }
        else{
            this.state = States.EXPLODING;
            this.explosion_start = currenTime;
            this.explosion_end = currenTime + 500;
        }
    }
    public void draw(double currentTime){
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
