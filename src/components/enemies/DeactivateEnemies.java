package components.enemies;

public class DeactivateEnemies {
    public static void Deactivate() {
        Circle.nextEnemy = Long.MAX_VALUE;
        Square.nextSquare = Long.MAX_VALUE;
    }
}
