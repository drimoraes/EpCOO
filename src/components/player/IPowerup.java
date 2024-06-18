package components.player;

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