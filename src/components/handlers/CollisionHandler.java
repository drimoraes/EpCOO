package components.handlers;

import components.Player;
import components.Projectile;

public class CollisionHandler {
    private Player player;
    private EnemyHandler enemyHandler;
    private ProjetileHandler projetileHandler;

    public CollisionHandler(Player player, EnemyHandler enemyHandler,
                            ProjetileHandler projectileHandler) {
        this.player = player;
        this.enemyHandler = enemyHandler;
        this.projetileHandler = projectileHandler;
    }
}
