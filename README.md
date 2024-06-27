# Descrição e justificativa para a nova estrutura de classes/interfaces adotada.

## Pacotes

O projeto é divido em 3 pacotes principais: components, entity e o mainpackage. Além disso, dentro do pacote components há outros 3: enemies, handlers e player.

## Componentes

Iremos começar falando sobre uma das classes principais do projeto: a classe Entity. Ela é uma classe abstrata que é extendida pela classe Player e por todos os inimigos e como seus atributos e métodos precisam ser acessíveis a partir de suas classes derivadas, ela está em um pacote próprio e seus atributos possuem o nível de proteção protected.

Ela foi feita dessa maneira com o objetivo de generalizar todos os que podem ser considerados entidades: atiram, são desenhados na tela, morrem, tem colisão e precisam de todos os getters. 

No projeto, temos as seguintes entidades: Player, BigSquare, Circle e Square.

Além das entidades, temos outros componentes: o Background, classe que auxilia na construção do plano de fundo do jogo, Projectile, descreve os projéteis dos players e dos inimigos; e Position, que indica a posição de todos os componentes (incluindo as entidades) no mapa.

Cada componente possui detalhes de implementação, mas são tratados de uma forma geral que será explorada adiante.

Além dos componentes, é importante citar que usamos uma classe do tipo Enum para definir quais são os estados possíveis das entidades.

## Handlers

Para gerenciar os estados dos componentes, temos as classes do pacote handlers. Falaremos individualmente de cada uma.

Começando pelo ProjectileHandler: ele possui 1 lista para cada tipo de projétil possível: projéteis aliados e projéteis inimigos. Lá também estão parte dos métodos que gerenciam a inclusão e exclusão de projéteis das lista, tanto para os players quanto para os inimigos. As listas possuem o nível de proteção protected para permitir que o CollisionHandler percorra ela e teste as colisões.

Assim, o principal objetivo do ProjectileHandler é gerenciar o estado de todos os projéteis.

Agora, vamos para o EnemyHandler. A ideia é parecida com a do ProjectileHandler: nele há uma lista de IEnemy e ele recebe como parâmetro o ProjectileHandler para conseguir adicionar os projéteis dos inimigos no jogo. Ele também possui o método Add, em que há uma  lógica para a adição de cada tipo de inimigo, de acordo com o seu tempo de Spawn.

Nesse desenho, podemos tratar todos que implementam a interface IEnemy da mesma maneira. Percorremos essa lista pelo método Update e chamamos para cada IEnemy métodos que testam se ele está na tela e se deve andar ou atirar. 

O CollisionHandler é quem testa as colisões entre os inimigos, projéteis e player. Ele recebe em seu construtor o ProjetileHandler, EnemyHandler, PowerUpHandler e o player. Dentro dele há a lógica de testes de colisão entre todos. Ele precisa percorrer todas as listas, testar as colisões e chama os métodos para retirar cada entidade da tela. Para player e o inimigo, o método é kill; para os powerups, apply.

Falando sobre o PowerUpHandler, ele é bem parecido com os outros 2, possui lógica para atualizar cada um dos PowerUps do ArrayList e a lógica para adicionar os novos. A regra para adicionar cada novoPowerUp está contido dentro da classe específica de powerUp como uma constante.

O DrawHandler não possui listas dentro de si, mas sim os outros handlers, o background e o player. Para os projetéis de player, há uma lógica de desenho para cima; para os do inimigo, para baixo. Já para os inimigos e powerups, percorremos as respectivas listas usando as respectivas interfaces IEnemy e IPowerup, parecido com o que foi feito no CollisionHandler e no EnemyHandler. O Background é uma classe que contém as estrelas do plano de fundo que tem a própria lógica de desenho.

O pacote dos handlers foi feito porque queríamos que os handlers tivessem acesso mútuo entre si, podendo acessar as listas uns dos outros. A vantagem foi: cada lógica está em uma classe distinta e mudar a implementação de uma classe não vai alterar a outra.

## Inimigos

Temos 3 tipos de inimigos: BigSquare, Circle e Square. Todos eles extendem a interface IEnemy e implementam os métodos seus métodos. Isso foi necessário para que, nos handlers, pudéssemos tratar todos eles como um mesmo. Cada um possui a própria lógica e tudo é generalizado na Interface.

## Player

O pacote player também engloba os powerUps, já que eles precisam ter a possibilidade de alterar alguns pontos do player. Assim, usamos os modificadores de acesso protected e default para permitir isso. O player possui um método especial: CheckMoviment. Ele testa se alguma tecla está sendo pressionada. Além disso, em seu construtor, há um Scanner para definir a quantidade de vidas que o jogador deseja. A barra de vida foi implementada de modo a calcular a quantidade de vidas restantes e diminuir ela de acordo.

## Conclusão

A forma que a classe main ficou é a maior justificativa para a estrutura adotada. No início, temos as mesmas inicializações: running, delta e currentTime. Logo depois ja há mudança: a instanciação de um player. Na teoria, se adaptássemos um pouco o teste de movimentação, poderíamos adicionar um segundo sem muitos problemas. Logo depois inicializamos nossos handlers: ProjetileHandler com um player, EnemyHandler com um inimigo, PowerUpHandler e CollisionHandler e DrawHandler recebendo todos os anteriores.

Olhando para essa inicialização, fica claro que, se quiséssemos adicionar um novo inimigo, não seria preciso fazer modificações na classe Main, apenas na classe de implementação e no EnemyHandler para adicionar a lógica de renascimento. Essa é a grande força dessa estrutura. Essa lógica também serve para mudar características de inimigos: só seria necessário mexer na classe do inimigo específico.

Continuando o código Main entrando no loop principal: calculamos o delta, currentTime e já começamos a fazer uso dos handlers: checamos colisões, adicionamos na lista de projéteis, atualizamos e tentamos adicionar inimigos e powerups, teste se o player vai reviver, avança o background e desenha as entidades. 

Essa simplicidade ao ler o código Main, onde só precisamos chamar os métodos dos handlers, deixa o código extremamente mais legível que o outro e conseguimos entender exatamente a função de cada um dos métodos que estão sendo chamados apenas ao ler seus nomes. A vantagem ainda continua: se fosse necessário adicionar um novo powerUp, a classe desse novo simplesmente seria criada, implementaria o que fosse necessário de entity e IPowerUp e já estaria pronto para uso, sem mexer em outras partes do código.

Em resumo, a nova estrutura de classes/interfaces adotada no projeto proporciona uma organização mais clara e modular, permitindo adicionar novos componentes, inimigos e power-ups de forma simples e sem a necessidade de modificar a classe principal. Isso torna o código mais legível, facilitando a compreensão e manutenção do projeto.