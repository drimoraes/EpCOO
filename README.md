# Descrição e justificativa para a nova estrutura de classes/interfaces adotada.

## Pacotes

O projeto é divido em 3 pacotes principais: components, 
entity e o mainpackage. Além disso, dentro do pacote 
components há outros 3: enemies, handlers e player.

## Componentes

Iremos começar falando sobre uma das classes principais do projeto:
a classe Entity. Ela é uma classe abstrata que é extendida pela classe
Player e por todos os inimigos e como seus atributos e métodos precisam
ser acessíveis a partir de suas classes derivadas, ela está em um
pacote próprio e seus atributos possuem o nível de proteção protected.

Ela foi feita dessa maneira com o objetivo de generalizar todos os que
podem ser considerados entidades: atiram, são desenhados na tela, morrem,
tem colisão e precisam de todos os getters.

No projeto, temos as seguintes entidades: Player, BigSquare, Circle e
Square.

Além das entidades, temos outros componentes: o Background, classe que
auxilia na construção do plano de fundo do jogo, Projectile, descreve os projéteis
dos players e dos inimigos; e Position, que indica a posição de todos os componentes
(incluindo as entidades) no mapa.

Cada componente possui detalhes de implementação, mas são tratados de uma forma
geral que será explorada adiante.

## Handlers

Para gerenciar os estados dos componentes, temos as classes do pacote handlers.
Falaremos individualmente de cada uma.

Começando pelo ProjectileHandler: ele possui 1 lista para cada tipo de projétil possível:
projéteis aliados e projéteis inimigos. Lá também estão parte dos métodos que gerenciam
a inclusão e exclusão de projéteis das lista, tanto para os players quanto para os inimigos.
As listas possuem o nível de proteção protected para permitir que o CollisionHandler percorra ela
e teste as colisões.

Assim, o principal objetivo do ProjectileHandler é gerenciar o estado
de todos os projéteis.

Agora, vamos para o EnemyHandler. A ideia é parecida com a do ProjectileHandler: nele
há uma lista de IEnemy e ele recebe como parâmetro o ProjectileHandler para conseguir
adicionar os projéteis dos inimigos no jogo. Ele também possui o método Add, em que há uma 
lógica para a adição de cada tipo de inimigo, de acordo com o seu tempo de Spawn.

Nesse desenho, podemos tratar todos que implementam a interface IEnemy da mesma maneira.
Percorremos essa lista pelo método Update e chamamos para cada IEnemy métodos que testam se 
ele está na tela e se deve andar ou atirar. 

O CollisionHandler é quem testa as colisões entre os inimigos, projéteis e player.
Ele recebe em seu construtor o ProjetileHandler, EnemyHandler, PowerUpHandler e o player, e dentro dele
há a lógica de testes de colisão entre todos eles. Ele precisa percorrer todas as listas e
testar as colisões e chama os métodos   