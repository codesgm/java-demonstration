# java-demonstration


A documentação dos endpoints foi implementada utilizando o Swagger, para acessá-la, basta iniciar a aplicação e acessar http://localhost:8080/swagger-ui/index.html

Inicializar o projeto
$ docker compose up -d


## JAVA ALGORITIMOS - 

## PARTE 1

1- 
HasMap e TreeMap são classes que implementam a interface Map, ambas utilizadas para armazenar
pares chave-valor.

HashMap tem uma performance de O(1), já o TreeMap tem O(log n), ambos são extremamente performaticos, o que isso quer dizer?
Em O(1) o tempo é constante, com diferença insignificante quando se trata de 10 itens ou 10 mil itens.
Já em O(log n), mesmo com grande quantidade de itens, a performance ainda é aceitável, já que operações em 10 mil itens, leva
metade do tempo que levaria em uma operação com 10 milhões.

Usar HashMap quando:
Necessitamos de extrema performance
Não é necessário ordernar os dados
Chaves podem ser nulas

Usar TreeMap quando:
Necessitamos de ordenação automática
Precisamos de operações de range, como filtrar produtos dentro de uma faixa de preço

Comparativo:
  INSERÇÃO:
    HashMap:  40,3 ms (40,3 ns por operação)
    TreeMap:  152,8 ms (152,8 ns por operação)
    TreeMap é 3,8x mais lento que HashMap

  BUSCA:
    HashMap:  613,4 μs (0,6 ns por operação)
    TreeMap:  9,1 ms (9,1 ns por operação)
    TreeMap é 14,8x mais lento que HashMap

  REMOÇÃO:
    HashMap:  26,3 ms (26,3 ns por operação)
    TreeMap:  89,9 ms (89,9 ns por operação)
    TreeMap é 3,4x mais lento que HashMap




2-

O JMM é quem comanda o uso da memória, threads, sincronização do ambiente e o multi-thread, ela é quem garante que os sistemas
funcionem de forma adequanda em diferentes hardwares e plataformas, sendo essencial para evitar problemas
relacionados à segurança dos threads, condições de corrida e visibilidade em aplicativos java.


3-
Classes imutaveis no java são isntãncias que não podem ser modificadas após instânciadas, nela é preciso que todos os campos sejam final e private, não possui métodos setter
e os métodos não podem modificar o estado do objeto, um exemplo é o tipo String. Esse tipo de classe costuma ser utilizado em Value object, chaves map e aplicações multi thread.


4-
O Garbage Collection é um processo que gerencia automaticamente a memória da aplicação, removendo objetos que não possuem mais referências ativas. Apesar de resolver automaticamente problemas de vazamento de memória, o GC pode interromper momentaneamente a execução da aplicação durante sua operação, por isso existem vários coletores especializados como Serial, Parallel, G1 e ZGC, cada um adequado para diferentes cenários de performance.


5- 
A JVM executa o bytecode Java (já compilado pelo javac) e o transforma em código nativo através de interpretação e compilação JIT. Ela serve como ambiente de execução multiplataforma, sendo necessário instalá-la no sistema operacional para 
executar aplicações Java

6- 
No IoC, quem contra as dependências é o próprio framework, esse gerênciamento é apontado através das anotações, como @Componente, @Service e etc... Com isso, temos mais desacoplamento, flexibilidade e testabilidade através da injeção de dependências.


7- 
As anotattions são injeções de códigos que utilizamos para um gerênciamento específico, elas são utilizadas para facilitar o desenvolvimento, um exemplo é o @Transactional, 
que utilizamos para gerênciar as transações com o banco de dados, ou a @Getter/@Setter, utilizadas para minimizar o código escrito, criando os getters e settes automáticamente.



## PARTE 2

1- https://github.com/codesgm/java-demonstration/blob/main/src/main/java/com/demonstration/demo/cache/LRUCache.java


2- https://github.com/codesgm/java-demonstration/blob/main/src/main/java/com/demonstration/demo/services/FileCleanServiceImpl.java


3- https://github.com/codesgm/java-demonstration/blob/main/src/main/java/com/demonstration/demo/services/AccountServiceImpl.java


4- https://github.com/codesgm/java-demonstration/blob/main/src/main/java/com/demonstration/demo/resource/UserResource.java


## PARTE 4 

1 - 
Hibernate é um framework ORM (Object-Relational Mapping) para Java que facilita o mapeamento entre objetos Java e tabelas de banco de dados relacionais, eliminando a necessidade de escrever SQL manualmente na maioria dos casos.
As classes desse projeto servem como exemplo de utilziação do hibernate.



2-
Não há o que se discutir sobre testes unitários, boa parte da obrigação de manter a estabilidade e confiabilidade da aplicação, está sobre eles.
https://github.com/codesgm/java-demonstration/blob/main/src/test/java/com/demonstration/demo/cache/LRUCacheTest.java


3-
Maven me é simples e direto, porém pega um pouco no desempenho durante o build, já o Gradle é um pouco mais custoso de se utilizar, compensando por seu desempenhor maior.
Eu escolheria maven em um sistema tradicional e gradle quando eu precisar da performance oferecida por ele, o que são poucos casos, já que não é costume precisar buildar a 
aplicação várias e várias vezes com frequência.


## PARTE 5

1-
Debugging é algo que fazemos com frequência mas dificil mente descrevemos como fazer, então vamos lá;
O primeiro a se fazer é ler a mensagem de erro no console, quando apresentada, após, rastrear de onde ela surge e colocar o brak point,
utilizo o CTRL + U (atalho do eclipse) para istânciar as variáveis ou verificar o retorno de funções.
Quando é algo que não tenho o controle local, costumo utilizar os logs do @Slf4j para rastrear o erro, principalmente quando é algo que ocorre
de forma esporadica ou somente em ambiente de produção.


2. Melhoria de Performance
O primeiro passo é sabermos de onde vem o gargalo de performance, se é processador, memoria ou banco de dados. Para isso, primeiro ativamos o log do banco de dados
e os logs em nível de DEBUG. Para monitorar o consumo de recursos, tive boas experiências com o VisualVM, por lá é simples de se identificar o que consome a memoria
e o poder de processamento. Feito isso, é simples identificar de onde vem o gargalo para agir sobre ele.
