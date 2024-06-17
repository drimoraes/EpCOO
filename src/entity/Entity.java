package entity;

import components.Position;
import components.ProjectileList;
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
}
