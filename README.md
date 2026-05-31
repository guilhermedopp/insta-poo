# VIVER+ 🌟

O **VIVER+** é uma rede de partilha de momentos e socialização, desenvolvida com um propósito claro: a **inclusão digital e acessibilidade para a terceira idade**. 

Nascido da necessidade de criar ambientes digitais mais amigáveis, o projeto elimina barreiras tecnológicas através de uma interface PWA (Progressive Web App) minimalista, de alto contraste e navegação simplificada, enquanto é sustentado por um backend robusto em Java.

Projeto desenvolvido no âmbito da disciplina de **Programação Orientada a Objetos (POO)**.

---

## 🎯 Visão e Funcionalidades (Roadmap)

A plataforma foca-se nas necessidades essenciais de socialização digital, garantindo segurança e usabilidade:

- **Autenticação Acessível:** Ecrãs de Login e Registo com botões de grande dimensão e feedback claro.
- **Feed de Momentos:** Um espaço simples para partilhar textos e pensamentos de forma intuitiva.
- **Rede de Contactos:** Capacidade de seguir outros utilizadores e construir uma rede de amigos.
- **Sistema de Notificações Inteligente:** Alertas em tempo real sobre novas interações (implementado através do padrão de desenho *Observer*).
- **Proteção Anti-Fraude:** Regras de negócio rigorosas que bloqueiam a partilha de links externos não verificados, protegendo o público idoso contra burlas e phishing.

---

## 🏗️ Arquitetura e Padrões (POO)

O projeto foi desenhado com uma separação rigorosa de responsabilidades, dividindo a aplicação em dois grandes ecossistemas (Frontend e Backend), utilizando os seguintes conceitos de POO no servidor:

* **Padrões de Desenho (Design Patterns):** Aplicação do padrão **Observer** para a gestão de seguidores e notificações.
* **Arquitetura em Camadas (Layered Pattern):**
  * **VO (Value Object):** Entidades de domínio encapsuladas (`UsuarioVO`, `PostVO`).
  * **BO (Business Object):** Camada de regras de negócio, tratamento de exceções e validações de segurança.
  * **DAO (Data Access Object):** Isolamento total da comunicação com a base de dados via JDBC.
  * **Controller:** Exposição da API REST utilizando o Javalin.

---

## 🛠️ Stack Tecnológica

### Backend (Servidor e API REST)
* **Linguagem:** Java 17
* **Gestão de Dependências:** Maven
* **Micro-Framework Web:** Javalin (Roteamento e Servidor HTTP)
* **Base de Dados:** MySQL 8.x
* **Processamento de Dados:** Jackson (Manipulação de JSON)

### Frontend (Interface Mobile/Web)
* **Estrutura:** PWA (Progressive Web App) para instalação nativa no telemóvel.
* **Linguagem:** HTML5, CSS3 (variáveis de alto contraste).
* **Comunicação:** JavaScript (Vanilla JS / Fetch API).

---

## 🚀 Como Executar o Projeto Localmente

### 1. Preparar a Base de Dados
1. Certifique-se de que tem o MySQL instalado.
2. Crie uma base de dados chamada `viver_db`.
3. A aplicação encarrega-se da criação de dados ou utilize o script SQL (a ser disponibilizado) para a tabela de `usuarios`.

### 2. Iniciar o Servidor (Backend)
1. Abra a pasta `viver-backend` na sua IDE (VS Code, IntelliJ ou Eclipse).
2. Deixe o **Maven** descarregar as dependências automaticamente a partir do `pom.xml`.
3. Execute o ficheiro `Main.java`. O servidor Javalin iniciará na porta `8080`.

### 3. Abrir a Aplicação (Frontend)
1. Navegue até à pasta `viver-frontend`.
2. Abra o ficheiro `index.html` em qualquer navegador (Chrome, Edge, Firefox).
3. (Opcional) Utilize uma extensão como o *Live Server* no VS Code para simular o ambiente web completo.

---
*Desenvolvido com foco na empatia e na tecnologia acessível.*