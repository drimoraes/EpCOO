package components.handlers;

import components.Player;
import components.ProjectileList;
import components.enemies.IEnemy;

import java.util.List;

public class ProjetileHandler {
    private ProjectileList enemyProjectileList;
    private ProjectileList playerProjectileList;
    private Player player;
    private EnemyHandler enemyHandler;

    public ProjetileHandler(Player player, EnemyHandler enemyHandler){
        this.player = player;
        this.enemyHandler = enemyHandler;
    }

    public void DispararInimigos(long currentTime, long delta){
        for(IEnemy e : enemyHandler.enemies){
            if (e.getNextShoot(currentTime) && e.getPosY() < player.getYPosition())
                enemyProjectileList.addProjectile(e.Shoot(currentTime, delta));
        }
    }

    public void DispararPlayer(long currentTime){
        // CHECAR NO CODIGO MAIN E DEPOIS CHAMAR O DISPAROSPLAYER if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
        if(currentTime > player.getNextShoot()){
            playerProjectileList.addProjectile(player.Shoot(currentTime));
        }
    }

    public void CheckShoots(long delta){
        playerProjectileList.findSaiuTela(delta);
        enemyProjectileList.findSaiuTela(delta);
    }
}
