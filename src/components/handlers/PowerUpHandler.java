package components.handlers;

import components.player.IPowerup;
import components.player.Player;
import components.player.Speed;

import java.util.ArrayList;
import java.util.Iterator;

public class PowerUpHandler {
    protected ArrayList<IPowerup> powerups = new ArrayList<>();

    public PowerUpHandler() {}

      public void Update(long currentTime, long delta, Player player){
        for(Iterator<IPowerup> iterator = powerups.iterator(); iterator.hasNext(); ) {
            IPowerup e = iterator.next();
            if(e.getEndPowerUp() < currentTime && (e.exploded(currentTime) || e.leaveScreen())){
                if(e.isActive())
                    e.remove(player);
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
