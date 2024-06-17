package components.handlers;

import components.Player;
import components.Projectile;
import components.ProjectileList;
import components.enemies.IEnemy;

import java.util.LinkedList;
import java.util.List;

public class ProjetileHandler {
    protected LinkedList<Projectile> enemyProjectileList;
    protected LinkedList<Projectile> playerProjectileList;
    private Player player;
    private EnemyHandler enemyHandler;

    public ProjetileHandler(Player player, EnemyHandler enemyHandler){
        this.enemyProjectileList = new LinkedList<>();
        this.playerProjectileList = new LinkedList<>();
        this.player = player;
        this.enemyHandler = enemyHandler;
    }

    public void DispararInimigos(long currentTime, long delta){
        for(IEnemy e : enemyHandler.enemies){
            if (e.getNextShoot(currentTime) && e.getPosY() < player.getPosY())
                enemyProjectileList.addAll(e.Shoot(currentTime, delta));
        }
    }

    public void DispararPlayer(long currentTime){
        // CHECAR NO CODIGO MAIN E DEPOIS CHAMAR O DISPAROSPLAYER if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
        if(currentTime > player.getNextShoot()){
            playerProjectileList.addAll(player.Shoot(currentTime));
        }
    }

    public void CheckShoots(long delta){
        findSaiuTela(delta, this.playerProjectileList);
        findSaiuTela(delta, this.enemyProjectileList);
    }

    public void findSaiuTela(double delta, LinkedList<Projectile> projectileList){
        for(Projectile iProjectile : projectileList){
            if(!iProjectile.isValid()){
                projectileList.remove(iProjectile);
            }
            else{
                iProjectile.andar(delta);
            }
        }
    }
}
