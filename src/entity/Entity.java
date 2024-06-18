package entity;

import components.Position;
import mainpackage.States;

public class Entity {
    protected States state;
    protected Position position;
    protected double explosion_start;
    protected double explosion_end;
    protected long next_shot;
    protected double radius;

    public Entity(States state, double entityPosX, double entityPosY, double entitySpeedX, double entitySpeedY,
                  double explosion_start, double explosion_end, long next_shot, double radius){
        this.state = state;
        this.position = new Position(entityPosX, entityPosY, entitySpeedX, entitySpeedY);
        this.explosion_start = explosion_start;
        this.explosion_end = explosion_end;
        this.next_shot = next_shot;
        this.radius = radius;
    }

    public double getRadius(){
        return this.radius;
    };
    public States getState() {
        return this.state;
    }
    public double getPosY(){
        return this.position.getPosY();
    }
    public double getPosX(){ return this.position.getPosX(); }
    public double getExplosionEnd(){
        return this.explosion_end;
    }
    public double getExplosionStart(){
        return this.explosion_start;
    }
}
