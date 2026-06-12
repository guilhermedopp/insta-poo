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
- **Instalação Nativa e Offline (PWA):** Graças à implementação de um *Service Worker*, a aplicação pode ser instalada diretamente no ecrã inicial do telemóvel e tem os seus recursos em cache.
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
* **Estrutura:** PWA (Progressive Web App) com `manifest.json` e `sw.js` (Service Worker) para funcionamento otimizado.
* **Linguagem:** HTML5, CSS3 (variáveis de alto contraste).
* **Comunicação:** JavaScript (Vanilla JS / Fetch API).

### Ferramentas de Desenvolvimento e Automação
* **Node.js & NPM:** Utilizados como *Task Runners* (`package.json`) para orquestrar o ambiente de desenvolvimento.
* **Nodemon:** Configurado para observar alterações no código Java e recompilar o servidor automaticamente (*Hot Reload*).
* **Serve:** Servidor HTTP local para contornar bloqueios de CORS e simular o PWA em ambiente real de produção.

---

## 🚀 Como Executar o Projeto Localmente

Graças à arquitetura de automação implementada, iniciar o ecossistema completo é um processo rápido e unificado.

### 1. Preparar a Base de Dados
1. Certifique-se de que tem o MySQL e um cliente SQL (como o DBeaver) instalados.
2. Execute o ficheiro **`script.sql`** (na pasta do backend). Ele irá criar a base de dados `viver_db`, a tabela `usuarios` (com suporte atualizado à data de nascimento) e inserir a utilizadora de teste.

### 2. Configurar Credenciais (Segurança)
1. Crie uma variável de ambiente no seu sistema operativo chamada **`DB_PASSWORD`** contendo a palavra-passe do seu utilizador root do MySQL.
*(Em alternativa, pode injetar a senha descomentando a linha apropriada no ficheiro `iniciar.bat`).*

### 3. Iniciar o Sistema (A Mágica da Automação)
Certifique-se de que tem o **Node.js** e o **Maven** instalados na sua máquina.

1. Abra o terminal na raiz do projeto (`viver-mais`).
2. Execute o seguinte comando:
   ```bash
   npm run dev
---
*Desenvolvido com foco na empatia e na tecnologia acessível.*