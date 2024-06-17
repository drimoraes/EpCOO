package components;

import mainpackage.GameLib;
import mainpackage.States;

public class Projectile {
    private double radius;
    private Position position;

    public Projectile() {}
    public Projectile(double radius, double posX, double posY, double speedX, double speedY) {
        this.radius = radius;
        this.position = new Position(posX, posY, speedX, speedY);
    }
    public double getRadius(){
        return this.radius;
    }
    public Position getPosition(){
        return this.position;
    }

    public void andar(double delta){
        this.position.walkX(this.position.getSpeedX() * delta);
        this.position.walkY(this.position.getSpeedY() * delta);
    }

    public Boolean isValid(){
        return !(this.position.getPosY() < 0 || this.position.getPosY() > GameLib.HEIGHT);
    }
}
