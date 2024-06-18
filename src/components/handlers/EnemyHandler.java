package components.handlers;

import components.Player;
import components.Projectile;
import components.enemies.Circle;
import components.enemies.IEnemy;
import components.enemies.Square;
import mainpackage.States;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class EnemyHandler {
    protected ArrayList<IEnemy> enemies = new ArrayList<>();
    private ProjetileHandler projetileHandler;

    public EnemyHandler(ProjetileHandler projetileHandler) {
        this.projetileHandler = projetileHandler;
    }

    public void Update(long currentTime, long delta, double playerYPosition){
        for(Iterator<IEnemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            IEnemy e = iterator.next();
            if(e.exploded(currentTime) || e.leaveScreen()){
                iterator.remove();
            }
            else{
                e.Andar(delta);
                if (e.getNextShoot(currentTime) && e.getPosY() < playerYPosition)
                    this.projetileHandler.AdicionarProjectile(e.Shoot(currentTime, delta));
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
}
