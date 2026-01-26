# Um Pouco De Tudo - Full Stack
![GitHub License](https://img.shields.io/github/license/tiagocitrangulodasilva/UmPoucoDeTudo_FullStack)

O objetivo do projeto é ser um diário, onde o usuário pode escrever suas histórias e catalogá-las por meio de etiquetas personalizáveis, porém não precisa necessariamente ser usado para contar histórias, o usuário pode usar o software como um bloco de notas também caso queira.

## Acessar
Este repositório contém apenas a versão full-stack do projeto, no qual todos os dados são salvos em um banco de dados MySQL. Para rodar o projeto é necessário baixá-lo e configurá-lo.

Caso queira ver apenas a versão front-end, que roda diretamente pelo GitHub Pages, este é o link:
[Um Pouco De Tudo](https://github.com/TiagoCitranguloDaSilva/UmPoucoDeTudo)

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
