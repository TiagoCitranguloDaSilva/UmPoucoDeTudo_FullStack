# Um Pouco De Tudo - Full Stack
![GitHub License](https://img.shields.io/github/license/tiagocitrangulodasilva/UmPoucoDeTudo_FullStack)

O objetivo do projeto é ser um diário, onde o usuário pode escrever suas histórias e catalogá-las por meio de etiquetas personalizáveis, porém não precisa ser usado para contar histórias, o usuário pode usar o software como um bloco de notas também caso queira.

## Versão Front-end
Este repositório contém apenas a versão full-stack do projeto, no qual todos os dados são salvos em um banco de dados MySQL. Para rodar o projeto é necessário baixá-lo e configurá-lo, caso queira ver apenas a versão front-end, que roda diretamente pelo GitHub Pages, este é o link:
[Um Pouco De Tudo](https://github.com/TiagoCitranguloDaSilva/UmPoucoDeTudo)

## Como rodar o projeto
Para rodar, é necessário ter instalado em sua máquina:
<ul>
  <li>Java JDK (A partir da versão 24) -> Link oficial para <a href="https://www.oracle.com/java/technologies/downloads">download</a> </li>
  <li>Maven -> Link oficial para <a href="https://maven.apache.org">download</a></li>
  <li>Node (A partir da versão 6.9.0) -> Link oficial para <a href="https://nodejs.org/pt-br/download">download</a></li>
  <li>MySQL (instalado e configurado na sua máquina, com um banco já criado)</li>
  <li>Um editor de arquivos de texto, pode ser uma IDE(Visual Studio Code, Intellij Idea e etc) ou o próprio bloco de notas do computador</li>
</ul>

Para confirmar a instalação correta das dependências (java, maven e node), vá no Prompt de Comando do seu computador e digite um de cada vez:

- ```java --version```
- ```node -v```
- ```mvn -v```
 
Em cada um dos três comandos, caso a instalação esteja correta, será mostrado a versão da dependência

Após garantir que esteja tudo instalado, baixe o arquivo .zip do projeto, para isso, vá até a página do repositório, clique no botão "Code" e escolha baixar o ZIP e o download começará.
Agora, descompacte o arquivo e abra-o.

Nos arquivos do projeto, irá ter 2 pastas, uma chamada "front-end" e outra chamada "back-end", na pasta front-end temos o React e no back-end o Spring Boot. Precisamos rodar o servidor de ambos

### Back-end
Para rodar o servidor do back-end (Spring Boot), abra o arquivo `ApplicationExample.yaml` em um editor de arquivos de texto, o arquivo está localizado em `back-end\UmPoucoDeTudo\src\main\resources`e o edite seguindo as instruções:

#### Configuração de conexão com o banco de dados
Na parte `datasource`:
  
- url: é link de conexão do banco de dados, deve começar com `jdbc:mysql://` e em seguida o link, no final tem que ter o nome do banco. (exemplo: `"jdbc:mysql://localhost:3306/UmPoucoDeTudo"` )(O banco PRECISA já estar criado ao rodar o projeto, o Spring Boot NÃO cria o banco)
- driver-class-name: nome do driver do banco de dados (recomendado não alterar)
- username: nome de usuário para acesso do banco de dados
- password: senha de acesso do banco de dados

Na parte `jpa -> hibernate`:
  
- ddl-auto: é a forma que o Spring irá agir ao notar uma modificação nos arquivos de banco dentro do projeto (model) (Recomendado não alterar)

Na parte `api -> security`:

- token: é a palavra de encriptação para a geração de tokens, os tokens são usados para autenticação de usuário. É NECESSÁRIO colocar um texto neste campo (ex: "segredo", "senha", "palavra". Qualquer tipo de texto funciona).

Após configurar os campos, renomeie o arquivo para `Application.yaml` e salve-o antes de fechar

Por último, abra a pasta "back-end/UmPoucoDeTudo" no Prompt de Comando do seu computador e digite o seguinte comando:

```mvn spring-boot:run```

O servidor irá iniciar (Mantenha este terminal aberto, pois caso o feche, o servidor Spring Boot irá cair)

### Front-end
Para rodar o servidor do front-end (React), abra a pasta "front-end" no Prompt de Comando do seu computador e digite o seguinte comando:

```npm install```

Este comando irá instalar todas as dependências que o React vai precisar


Quando o `npm install` já estiver finalizado, digite o segundo comando:


```npm run dev```

O servidor irá iniciar (Mantenha este terminal aberto, pois caso o feche, o servidor React irá cair)

Para abrir o navegador, com o terminal do servidor React aberto, digite `Ctrl + o`

## Tecnologias utilizadas:
<div>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/html5/html5-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/css3/css3-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/react/react-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/javascript/javascript-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/git/git-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg" height="40px"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postman/postman-original.svg" height="40px" />
          
          
</div>
Ícones das tecnologias obtidos em:

[Devicon](https://devicon.dev/)

## Características técnicas
O projeto é uma aplicação web stateless, ou seja, o back-end não guarda dados de sessão, todo o sistema de login e validação de usuário é feito por meio de tokens, foi utilizado:
### Back-end
<ul>
  <li>Spring Security para a segurança</li>
  <li>JWT (Json Web Token) para a geração e validação de tokens de autenticação</li>
  <li>BDDMockito para a criação de mocks de classes durante os testes unitários</li>
  <li>Assertions para validação de respostas dos testes unitários e de integração</li>
  <li>DataJPA para conexão, abstração e operações no banco de dados (Foi utilizado MySQL)</li>
  <li>DTOs para padronização de requests e responses</li>
  <li>Aplicativo Postman para simulação de requisições</li>
</ul>

### Front-end

<ul>
  <li>React Router para o sistema de rotas</li>
  <li>Fetch para realização das requisições</li>
  <li>LocalStorage para armazenar o token de validação (o token tem duração de 1 hora)</li>
  <li>SessionStorage para armazenar mensagens de fim de processo (sucesso ou falha) a serem mostradas em outra tela</li>
  <li>Biblioteca Chart.js para criação do gráfico</li>
</ul>

## Gráfico
No canto superior direito da página inicial há um botão que permite ver um gráfico de pizza mostrando a quantidade de histórias em cada etiqueta, para que esse gráfico funcione é necessário ter no mínimo uma etiqueta contendo ao menos uma história.
