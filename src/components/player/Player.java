package components.player;

import components.Projectile;
import entity.Entity;
import mainpackage.GameLib;
import mainpackage.States;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import components.Projectile;

public class Player extends Entity {
    private int lives;
    private int livesTemp;
    private double flash;
    private double damage;
    private double projectile_radius;
    Scanner scanner = new Scanner(System.in);

    public Player(double entityPosX, double entityPosY, double entitySpeedX, double entitySpeedY, long next_shot, double radius){
        super(States.ACTIVE, entityPosX, entityPosY, entitySpeedX, entitySpeedY, 0, 0, next_shot, radius);
        System.out.println("Digite a quantidade de vidas: ");
        this.lives = scanner.nextInt();
        this.livesTemp = this.lives;
        flash = 0;
        damage = 0;
        projectile_radius = 2;
    }

    public double getNextShoot(){
        return next_shot;
    }

    public void CheckMoviment(long delta){
        if(GameLib.iskeyPressed(GameLib.KEY_UP) && getPosY() > 25)
            walkY(delta*getSpeedY()*-1);
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
        if(this.livesTemp > 1){
            this.livesTemp--;
            this.state = States.DAMAGED;
            this.damage = currentTime + 500;
        }
        else{
            this.livesTemp--;
            this.state = States.EXPLODING;
            this.explosion_start = currentTime;
            this.explosion_end = currentTime + 2000;
        }
    }

    public void draw(double currentTime){
        // Desenha o player
        if(this.getState() == States.EXPLODING){
            double alpha = (currentTime - this.getExplosionStart())
                    / (this.getExplosionEnd() - this.getExplosionStart());
            GameLib.drawExplosion(this.getPosX(), this.getPosY(), alpha);
        }
        else if(this.getState() == States.DAMAGED){
            if(flash > currentTime){
                GameLib.setColor(Color.WHITE);
            }
            else{
                GameLib.setColor(Color.BLUE);
                flash = currentTime + 20;
            }
            GameLib.drawPlayer(this.getPosX(), this.getPosY(), this.getRadius());
        }
        else{
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.getPosX(), this.getPosY(), this.getRadius());
        }
        // Tamanho da barra
        double barLenght = 200;
        double barHeight = 25;
        // Desenha as vidas
        GameLib.setColor(Color.RED);
        GameLib.fillRect(GameLib.WIDTH*0.5-(barLenght*((double)lives-livesTemp)/(2*lives)), GameLib.HEIGHT-(50+barHeight/2), barLenght*((double)livesTemp/lives), 25);
        // Desenha a barra de vidas
        GameLib.setColor(Color.WHITE);
        GameLib.drawLine(GameLib.WIDTH*0.5-barLenght/2, GameLib.HEIGHT-50, GameLib.WIDTH*0.5+barLenght/2, GameLib.HEIGHT-50);
        GameLib.drawLine(GameLib.WIDTH*0.5-barLenght/2, GameLib.HEIGHT-50, GameLib.WIDTH*0.5-barLenght/2, GameLib.HEIGHT-(50+barHeight));
        GameLib.drawLine(GameLib.WIDTH*0.5-barLenght/2, GameLib.HEIGHT-(50+barHeight), GameLib.WIDTH*0.5+barLenght/2, GameLib.HEIGHT-(50+barHeight));
        GameLib.drawLine(GameLib.WIDTH*0.5+barLenght/2, GameLib.HEIGHT-(50+barHeight), GameLib.WIDTH*0.5+barLenght/2, GameLib.HEIGHT-50);
    }

    public void checkRevive(long currentTime){
        if(this.state == States.DAMAGED){
            if(currentTime > this.damage){
                this.state = States.ACTIVE;
            }
        }
        if(this.state == States.EXPLODING){
            if(currentTime > this.explosion_end){
                this.livesTemp = this.lives;
                this.state = States.ACTIVE;
            }
        }
    }
}
