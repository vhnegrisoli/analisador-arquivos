# Projeto: Analisador de Arquivos

Projeto para ler, analisar, processar e tratar arquivos conforme um padrão específico.

## Resumo

O projeto Analisador Arquivos é responsável por criar um listener que irá monitorar um diretório.
A cada novo arquivo inserido, o listener será capaz de identificar, processar o arquivo e salvar em outro.

O diretório de entrada é o `/data/in`, e o diretório de saída é o `/data/out`.

## Padrões de projeto

O projeto é subdividido em módulos, utiliza padrões de projeto DTO e Builder, divide-se em classes
utilitárias, de serviços e de DTOs para armazenamento dos dados.

### Tecnologias

* **Java 11**
* **Spring Boot 2**
* **Java Watch Service**
* **Testes de Integração e Unitários**
* **JUnit5**
* **Mockito**
* **AssertJ**

### Por quê do uso dessas tecnologias?

O Spring Boot foi escolhido devido a toda a possibilidade de desenvolvimento
escolhido pelo ecossistema Spring, definindo bons padrões para o projeto,
facilidade de configuração e desenvolvimento, e sua interação com bibliotecas
e classes de testes.

O projeto é compilado com o Maven e seus plugins, como por exemplo, o Maven Surefire Plugin
para inicialização de testes com JUnit5, para ganho de performance ao rodar testes.

### Como o sistema funciona?

Quando abrir o projeto, na raíz, terá o diretório:

```
analisador-arquivos
|
|---data
      |
      |--exemplos
```

No diretório `/data/exemplos`, deixei dois arquivos de testes para serem utilizados.

Ao inicializar a aplicação, automaticamente será criado o diretório `/data/in`, e a estrutura será:

```
analisador-arquivos
|
|---data
      |
      |--exemplos
      |
      |--in
```

Ao ser criado o diretório `/data/in`, a aplicação inicializará um listener com a biblioteca
`Java Watch Service` que irá sempre escutar inserções de arquivos neste diretório. Ao inserir qualquer
arquivo no diretório `/data/in`, a aplicação irá processá-lo, e criar outros diretórios.

* Cada arquivo inserido no diretório `/data/in` será removido deste diretório após processado.
* Caso dê sucesso, será criado o diretório `/data/out` com o arquivo de saída com os dados analíticos e o diretório `/data/processado` com os arquivos originais.
* Caso dê algum erro, será criado o diretório `/data/falha`, e o arquivo original será movido para lá.

Estrutura final:

```
analisador-arquivos
|
|---data
      |
      |--exemplos
      |
      |--in
      |
      |--out (caso dê sucesso)
      |
      |--processado (caso dê sucesso)
      |
      |--falha (caso falhe o processamento)
```

### Breve demonstração

Abaixo será possível visualizar no GIF como funciona a execução do programa:

### Pré-requisitos

É necessário ter as seguintes ferramentas para inicializar o projeto localmente:

```
Java 11
Maven
```

### Code Style

O projeto utiliza o checkstyle da Google e o plugin PMD, sendo assim, cada comando do maven irá rodar o checkstyle.

### Instalação

Primeiramente, rode a instalação através da mvn, sem os testes:

```
mvn clean install -DskipTests
```

Para realizar a instalação das dependências com os testes, execute apenas:

```
mvn clean install
```

Para construir o jar de execução, execute:

```
mvn package
```

## Iniciando a aplicação

Após instalar a aplicação, dar o build e gerar o jar, basta, na raiz, executar:

```
mvn spring-boot:run
```

Ou então:

```
cd target/java -jar analisador-arquivos-1.0.0.jar
```

A aplicação inicializará em:

```
http://localhost:8080
```

## Executando testes automatizados (unitários e de integração)

Foram escritos testes de integração e testes unitários utilizando o JUnit5 e o Mockito.

Para rodar apenas os testes:

```
mvn test
```

## Autores

* **Victor Hugo Negrisoli** - *Desenvolvedor Back-End* - [vhnegrisoli](https://github.com/vhnegrisoli)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE.txt](LICENSE.txt)
