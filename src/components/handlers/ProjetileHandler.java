package components.handlers;

import components.Projectile;
import components.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ProjetileHandler {
    protected LinkedList<Projectile> enemyProjectileList;
    protected LinkedList<Projectile> playerProjectileList;
    private Player player;

    public ProjetileHandler(Player player){
        this.enemyProjectileList = new LinkedList<>();
        this.playerProjectileList = new LinkedList<>();
        this.player = player;
    }

    public void DispararPlayer(long currentTime){
        if(currentTime > player.getNextShoot()){
            this.playerProjectileList.addAll(player.Shoot(currentTime));
        }
    }

    public void AdicionarProjectile(ArrayList<Projectile> projectiles){
        this.enemyProjectileList.addAll(projectiles);
        //System.out.println(this.enemyProjectileList.size());
    }

    public void CheckShoots(long delta){
        findSaiuTela(delta, this.playerProjectileList);
        findSaiuTela(delta, this.enemyProjectileList);
    }

    public void findSaiuTela(double delta, LinkedList<Projectile> projectileList){
        for (Iterator<Projectile> iterator = projectileList.iterator(); iterator.hasNext(); ) {
            Projectile iProjectile = iterator.next();
            if (!iProjectile.isValid()) {
                iterator.remove();
            }
            else{
                iProjectile.andar(delta);
            }
        }
    }
}
