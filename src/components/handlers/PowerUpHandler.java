package components.handlers;

import components.player.*;

import javax.crypto.ShortBufferException;
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
        if(currentTime > DoubleGun.getNextPowerUp()){
            this.powerups.add(new DoubleGun(currentTime));
        }
        if(currentTime > Shield.getNextPowerUp()){
            this.powerups.add(new Shield(currentTime));
        }
    }

    public void addStartGame(){
        this.powerups.add(new DefaultGun());
    }
    
}
