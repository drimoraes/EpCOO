package mainpackage;

import components.enemies.DeactivateEnemies;
import components.handlers.*;
import components.player.Player;

public class Main {
	public static void busyWait(long time){

		while(System.currentTimeMillis() < time) Thread.yield();
	}
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		long delta;
		long currentTime = System.currentTimeMillis();

		Player player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.80,
				currentTime, 12);


		ProjetileHandler projetileHandler = new ProjetileHandler(player);
		EnemyHandler enemyHandler = new EnemyHandler(projetileHandler);
		PowerUpHandler powerUpHandler = new PowerUpHandler();
		CollisionHandler collisionHandler = new CollisionHandler(player, enemyHandler,
				projetileHandler, powerUpHandler);

		DrawHandler drawHandler = new DrawHandler(player, enemyHandler, projetileHandler, powerUpHandler);

		GameLib.initGraphics();

		//DeactivateEnemies.Deactivate();

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
			powerUpHandler.Update(currentTime, delta, player);
			//projetileHandler.DispararInimigos(currentTime, delta);

			enemyHandler.Add(currentTime);
			powerUpHandler.Add(currentTime);
			player.checkRevive(currentTime);

			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/

			if(player.getState() == States.ACTIVE || player.getState() == States.DAMAGED){
				// Método movimentação
				player.CheckMoviment(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.getState() == States.ACTIVE){
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
