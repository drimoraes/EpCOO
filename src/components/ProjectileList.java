package components;

import mainpackage.GameLib;
import mainpackage.States;

import java.util.ArrayList;
import java.util.LinkedList;

public class ProjectileList {
    private LinkedList<Projectile> projectiles;

    public void addProjectile(int radius, double posX,
                              double posY, double speedX, double speedY){
        projectiles.add(new Projectile(radius, posX, posY, speedX, speedY));
    }
    public void addProjectile(ArrayList<Projectile> projectile){
        projectiles.addAll(projectile);
    }

    public void findSaiuTela(double delta){
        for(Projectile iProjectile : projectiles){
            if(!iProjectile.isValid()){
                projectiles.remove(iProjectile);
            }
            else{
                iProjectile.andar(delta);
            }
        }
    }
}
