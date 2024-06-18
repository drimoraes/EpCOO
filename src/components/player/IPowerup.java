package components.player;

import mainpackage.States;

import java.util.ArrayList;

public interface IPowerup {
    void Andar(long delta);
    Boolean exploded(long currentTime);
    Boolean leaveScreen();
    double getPosY();
    double getRadius();
    double getPosX();
    void apply(long currentTime, Player player);
    void draw(double currentTime);
}