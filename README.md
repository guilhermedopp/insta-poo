# VIVER+ 🌟

O **VIVER+** é uma rede de partilha de momentos e socialização, desenvolvida com um propósito claro: a **inclusão digital e acessibilidade para a terceira idade**. 

Nascido da necessidade de criar ambientes digitais mais amigáveis e inspirado em iniciativas de letramento digital para seniores, o projeto elimina barreiras tecnológicas através de uma interface PWA (Progressive Web App) minimalista, de alto contraste e navegação simplificada, enquanto é sustentado por um backend robusto em Java.

Projeto desenvolvido no âmbito da disciplina de **Programação Orientada a Objetos (POO)**.

---

## 🎯 Visão e Funcionalidades

A plataforma foca-se nas necessidades essenciais de socialização digital, garantindo segurança e usabilidade extrema:

- **Autenticação Acessível:** Ecrãs de Login e Registo com botões de grande dimensão, logótipos claros de orientação e feedback visual amigável.
- **Barreira de Proteção (60+):** Validação estrita a nível de servidor que garante que a comunidade é exclusiva para pessoas com 60 anos ou mais.
- **Feed de Momentos:** Um espaço simples para partilhar textos e pensamentos de forma intuitiva.
- **Sistema de Notificações Inteligente:** Alertas em tempo real sobre novas interações (implementado através do padrão de desenho *Observer*).
- **Proteção Anti-Fraude:** Regras de negócio rigorosas que bloqueiam a partilha de links externos não verificados, protegendo o público idoso contra burlas e *phishing*.

---

## 🏗️ Arquitetura e Padrões (POO)

O projeto foi desenhado com uma separação rigorosa de responsabilidades, dividindo a aplicação em dois grandes ecossistemas (Frontend e Backend), utilizando os seguintes conceitos de POO no servidor:

* **Padrões de Desenho (Design Patterns):** Aplicação do padrão **Observer** para a gestão de seguidores e notificações.
* **Arquitetura em Camadas (Layered Pattern):**
  * **VO (Value Object):** Entidades de domínio encapsuladas (`UsuarioVO`, `PostVO`), com blindagem de dados sensíveis na serialização (`@JsonIgnore`).
  * **BO (Business Object):** Camada de regras de negócio, tratamento de datas (`LocalDate`) para cálculo de idade, e validações de segurança.
  * **DAO (Data Access Object):** Isolamento total da comunicação com a base de dados via JDBC, com proteção nativa contra *SQL Injection* (`PreparedStatement`).
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
* **Estrutura:** PWA (Progressive Web App) com `manifest.json` para instalação nativa no telemóvel.
* **Linguagem:** HTML5, CSS3 (variáveis de alto contraste).
* **Comunicação:** JavaScript (Vanilla JS / Fetch API).

---

## 🚀 Como Executar o Projeto Localmente

### 1. Preparar a Base de Dados
1. Certifique-se de que tem o MySQL e um cliente como o DBeaver instalados.
2. No seu cliente SQL, execute o ficheiro **`script.sql`** (localizado na pasta do backend). Ele irá criar automaticamente a base de dados `viver_db`, a tabela `usuarios` (com suporte à data de nascimento), a tabela `postagens` e inserir utilizadores de teste.

### 2. Configurar Credenciais (Segurança)
Para não expor palavras-passe no código, o projeto utiliza variáveis de ambiente.
1. Crie uma variável de ambiente no seu sistema operativo chamada **`DB_PASSWORD`** (ou configure diretamente no `launch.json` do VS Code).
2. O valor dessa variável deve ser a palavra-passe do seu utilizador root do MySQL.

### 3. Iniciar o Servidor (Backend)
1. Abra a pasta `viver-backend` na sua IDE (VS Code, IntelliJ ou Eclipse).
2. Deixe o Maven descarregar as dependências automaticamente a partir do `pom.xml`.
3. Execute o ficheiro `Main.java` (recomendado utilizar a configuração de execução da IDE para carregar o `.env`). O servidor Javalin iniciará na porta `8080`.

### 4. Abrir a Aplicação (Frontend)
1. Navegue até à pasta `viver-frontend`.
2. Abra o ficheiro `index.html` em qualquer navegador (Chrome, Edge, Firefox).
3. Faça login com o utilizador de teste (`maria@viver.com` / `1234`).

---
*Desenvolvido com foco na empatia e na tecnologia acessível.*