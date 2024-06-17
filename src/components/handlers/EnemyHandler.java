package components.handlers;

import components.enemies.Circle;
import components.enemies.IEnemy;
import components.enemies.Square;

import java.util.LinkedList;

public class EnemyHandler {
    protected LinkedList<IEnemy> enemies = new LinkedList<>();

    public void CheckEnemies(long currentTime){
        for(IEnemy e : this.enemies){
            if(e.isExploding(currentTime) || e.leaveScreen()){
                enemies.remove(e);
            }
        }
    }

    public void AddEnemies(long currentTime){
        if(currentTime > Circle.getNextEnemy()) {
            this.enemies.add(new Circle(currentTime));
        }
        if(currentTime > Square.getNextSquare()) {
            this.enemies.add(Square.CreateSquare(currentTime));
        }
    }
}
