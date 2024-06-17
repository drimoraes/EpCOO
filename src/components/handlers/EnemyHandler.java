package components.handlers;

import components.enemies.Circle;
import components.enemies.IEnemy;
import components.enemies.Square;
import mainpackage.States;

import java.util.LinkedList;

public class EnemyHandler {
    protected LinkedList<IEnemy> enemies = new LinkedList<>();

    public void Update(long currentTime, long delta){
        for(IEnemy e : this.enemies){
            if(e.exploded(currentTime) || e.leaveScreen()){
                enemies.remove(e);
            }
            else{
                e.Andar(delta);
            }
        }
    }

    public void Add(long currentTime){
        if(currentTime > Circle.getNextEnemy()) {
            this.enemies.add(new Circle(currentTime));
        }
        if(currentTime > Square.getNextSquare()) {
            this.enemies.add(Square.CreateSquare(currentTime));
        }
    }

//    public void RemoveEnemies(){
//        for(IEnemy enemy : this.enemies){
//            if(enemy.getState() == States.INACTIVE){
//                enemies.remove(enemy);
//            }
//        }
//    }
}
