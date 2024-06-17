package mainpackage;

import components.Player;
import components.handlers.CollisionHandler;
import components.handlers.DrawHandler;
import components.handlers.EnemyHandler;
import components.handlers.ProjetileHandler;

import java.awt.*;

public class novoMain {
	public static void busyWait(long time){

		while(System.currentTimeMillis() < time) Thread.yield();
	}
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		long delta;
		long currentTime = System.currentTimeMillis();

		Player player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90,
				0.25,0.25, 0, 0,currentTime, 12);


		ProjetileHandler projetileHandler = new ProjetileHandler(player);
		EnemyHandler enemyHandler = new EnemyHandler(projetileHandler);
		CollisionHandler collisionHandler = new CollisionHandler(player, enemyHandler, projetileHandler);

		DrawHandler drawHandler = new DrawHandler(player, enemyHandler, projetileHandler);

		GameLib.initGraphics();

		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();

			/***************************/
			/* Verificação de colisões */
			/***************************/

			collisionHandler.checarColisoes(currentTime, player.getState());

			/***************************/
			/* Atualizações de estados */
			/***************************/

			projetileHandler.CheckShoots(delta);

			enemyHandler.Update(currentTime, delta, player.getPosY());

			//projetileHandler.DispararInimigos(currentTime, delta);

			enemyHandler.Add(currentTime);

			player.checkRevive(currentTime);

			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/

			if(player.getState() == States.ACTIVE){
				// Método movimentação
				player.CheckMoviment(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					projetileHandler.DispararPlayer(currentTime);
				}
			}
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/*******************/
			/* Desenho da cena */
			/*******************/

			drawHandler.continueBackground(delta);

			drawHandler.drawEntities(currentTime);
			
			/* chamama a display() da classe mainpackage.GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
