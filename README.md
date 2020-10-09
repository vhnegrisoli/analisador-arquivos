# Projeto: Analisador de Arquivos

[![Build Status](https://travis-ci.org/vhnegrisoli/analisador-arquivos.svg?branch=master)](https://travis-ci.org/vhnegrisoli/analisador-arquivos)

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

### Por que utilizei essas tecnologias?

O Spring Boot foi escolhido devido a toda a possibilidade de desenvolvimento
escolhido pelo ecossistema Spring, definindo bons padrões para o projeto,
facilidade de configuração e desenvolvimento, e sua interação com bibliotecas
e classes de testes.

O projeto é compilado com o Maven e seus plugins, como por exemplo, o Maven Surefire Plugin
para inicialização de testes com JUnit5, para ganho de performance ao rodar testes.

### Como o sistema funciona?

Quando abrir o projeto, na raiz, terá o diretório:

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

![Demonstração](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Demo%20Aplica%C3%A7%C3%A3o.gif)

### Exemplo de arquivos

O arquivo de entrada conterá linhas separadas pelo caractere `ç` e 
terá um indicador inicial para cada tipo de dados iniciado em: `001`, `002` e `003`.

* Linhas que iniciam com `001` serão referentes aos dados do vendedor. 
* Linhas que iniciam com `002` serão referentes aos dados do cliente. 
* Linhas que iniciam com `003` serão referentes aos dados da venda. 

O arquivo de entrada será neste formato:

```
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```

O arquivo de saída é um arquivo analítico que, ao processar as informações,
exibirá:

* O arquivo de referência
* O total de vendedores no arquivo de entrada
* O total de vendedores distintos no arquivo de entrada
* O total de clientes no arquivo de entrada
* O total de clientes distintos no arquivo de entrada
* O ID da venda mais cara
* O pior vendedor

O arquivo analítico de saída será neste formato:

```
Arquivo de referência:                 exemplo 1.txt
Total de vendedores na entrada:        2
Total de vendedores distintos:         2
Total de clientes na entrada:          2
Total de clientes distintos:           2
ID da venda mais cara:                 10
Pior vendedor:                         Paulo
```

### Pré-requisitos

É necessário ter as seguintes ferramentas para inicializar o projeto localmente:

```
Java 11
Maven
```

### Code Style

O projeto utiliza o checkstyle da Google e o plugin PMD, sendo assim, cada comando do maven irá rodar o checkstyle.

### Continuous Integration (CI)

Foi feito um deploy da aplicação no Travis-CI para validar o build da aplicação e a execução dos testes.

A aplicação passou com 38 testes rodados com sucesso, foi adicionada a badge de `build passing` ao início deste README.

O resultado do build pode ser visto [aqui](https://travis-ci.org/github/vhnegrisoli/analisador-arquivos).

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

## Executando testes unitários e de integração

Foram escritos testes de integração e testes unitários utilizando o JUnit5 e o Mockito.

Para rodar apenas os testes:

```
mvn test
```

### Cobertura de testes

Utilizando a IDE IntelliJ IDEA da JetBrains, foram rodados os testes unitários
e de integração com a opção de coverage para verificar qual foi a cobertura total de testes.

#### Cobertura geral

![Cobertura geral](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Coverage%20Geral.png)

A cobertura geral do projeto ficou em 95% de classes testadas (21 de 22 classes), 86% em métodos testados (80 de 92 métodos) 
e 87% de llinhas testadas (280 de 319).

#### Cobertura dos módulos específicos da aplicação 

![Cobertura módulos](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Coverage%20Modulos.png)

A aplicação é divida em dois módulos principais, o módulo de arquivo e de comum, contendo classes utilitárias.
O módulo de arquivo ficou com 100%"de cobertura de classes (12 de 12 classes testadas), 88% de cobertura de métodos
(64 de 72 métodos) e 92% de cobertura de linhas (225 de 244 linhas).

O módulo comum ficou com 100% de cobertura de classes (6 de 6 classes), 100% de métodos (10 de 10 métodos) e 100% de linhas (47 de 47 linhas).
 
#### Cobertura das classes de DTOs

As classes de DTOs são responsáveis por processar as linhas em dados para os objetos Java.

![Cobertura DTOs](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Coverage%20DTOs.png)

A DTO ArquivoSaida teve 100% de cobertura de classes (2 de 2 classes), 95% de cobertura de métodos (19 de 20 métodos) e 98% de cobertura
de linhas (70 de 71 linhas).

A DTO Cliente teve 100% de cobertura de classes (2 de 2 classes), 77% de cobertura de métodos (7 de 9 métodos) e 86% de cobertura
de linhas (19 de 22 linhas).

A DTO ItemVenda teve 100% de cobertura de classes (2 de 2 classes), 90% de cobertura de métodos (10 de 11 métodos) e 93% de cobertura
de linhas (28 de 30 linhas).

A DTO Venda teve 100% de cobertura de classes (2 de 2 classes), 81% de cobertura de métodos (9 de 11 métodos) e 89% de cobertura
de linhas (26 de 29 linhas).

A DTO Vendedor teve 100% de cobertura de classes (2 de 2 classes), 77% de cobertura de métodos (7 de 9 métodos) e 86% de cobertura
de linhas (19 de 22 linhas).

#### Cobertura das classes de Service

Existem duas classes service, a de processamento e tratamento de arquivos.

![Cobertura Services](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Coverage%20Services.png)

A classe ArquivoProcessamentoService teve 100% de cobertura de classes (1 de 1 classes), 100% de cobertura de métodos (9 de 9 métodos) e 86% de cobertura
de linhas (45 de 52 linhas).

A classe ArquivoTratamentoService teve 100% de cobertura de classes (1 de 1 classes), 100% de cobertura de métodos (3 de 3 métodos) e 100% de cobertura
de linhas (18 de 18 linhas).

#### Cobertura das classes utilitárias

Existem três classes utilitárias, uma de String, uma de Exception e uma de Arquivo.

![Cobertura Utils](https://github.com/vhnegrisoli/analisador-arquivos/blob/master/imagens%20e%20gifs/Coverage%20Utils.png)

A classe ArquivoUtil teve 100% de cobertura de classes (1 de 1 classes), 100% de cobertura de métodos (3 de 3 métodos) e 100% de cobertura
de linhas (11 de 11 linhas).

A classe ExceptionUtil teve 100% de cobertura de classes (1 de 1 classes), 100% de cobertura de métodos (1 de 1 métodos) e 100% de cobertura
de linhas (1 de 1 linhas).

A classe StringUtil teve 100% de cobertura de classes (1 de 1 classes), 100% de cobertura de métodos (2 de 2 métodos) e 100% de cobertura
de linhas (9 de 9 linhas).

## Autores

* **Victor Hugo Negrisoli** - *Desenvolvedor Back-End* - [vhnegrisoli](https://github.com/vhnegrisoli)
* [![Linkedin Badge](https://img.shields.io/badge/-victorhugonegrisoli-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/victorhugonegrisoli//)](https://www.linkedin.com/in/victorhugonegrisoli/) 
* [![Gmail Badge](https://img.shields.io/badge/-victorhugonegrisoli.ccs@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:sakshamtaneja7861@gmail.com)](mailto:victorhugonegrisoli.ccs@gmail.com)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE.txt](LICENSE.txt)
