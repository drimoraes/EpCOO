package components.handlers;

import components.Player;
import components.player.IPowerup;
import components.player.Speed;

import java.util.ArrayList;
import java.util.Iterator;

public class PowerUpHandler {
    protected ArrayList<IPowerup> powerups = new ArrayList<>();

    public PowerUpHandler() {}

      public void Update(long currentTime, long delta, double playerYPosition){
        for(Iterator<IPowerup> iterator = powerups.iterator(); iterator.hasNext(); ) {
            IPowerup e = iterator.next();
            if(e.exploded(currentTime) || e.leaveScreen()){
                iterator.remove();
            }
            else{
                e.Andar(delta);
            }
        }
    }

      public void Add(long currentTime){
        if(currentTime > Speed.getNextPowerUp()) {
            this.powerups.add(new Speed(currentTime));
        }
    }

    
}
