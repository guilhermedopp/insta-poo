const API_URL = 'http://localhost:8080/api';

// ===================== LOGIN =====================
const formLogin = document.getElementById('form-login');

if (formLogin) {
    formLogin.addEventListener('submit', async function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value.trim();
        const senha = document.getElementById('senha').value;

        try {
            const resposta = await fetch(`${API_URL}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email: email, senha: senha })
            });

            if (resposta.ok) {
                const dadosUsuario = await resposta.json();
                localStorage.setItem('usuarioLogado', JSON.stringify(dadosUsuario));
                alert('Bem-vindo(a) ao VIVER+, ' + dadosUsuario.nome + '!');
                window.location.href = 'feed.html';
            } else {
                const mensagemErro = await resposta.text();
                alert('Aviso: ' + mensagemErro);
            }
        } catch (erro) {
            alert('Não foi possível conectar ao VIVER+. Verifique a sua internet ou contacte o suporte.');
            console.error('Erro na API:', erro);
        }
    });
}

// ===================== CADASTRO =====================
const formCadastro = document.getElementById('form-cadastro');

if (formCadastro) {
    formCadastro.addEventListener('submit', async function(event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value.trim();
        const email = document.getElementById('email').value.trim();
        const senha = document.getElementById('senha').value;
        const dataNascimento = document.getElementById('dataNascimento').value;

        const mensagem = document.getElementById('mensagem-cadastro');

        if (mensagem) {
            mensagem.textContent = 'A validar dados...';
            mensagem.className = 'mensagem';
        }

        try {
            const resposta = await fetch(`${API_URL}/cadastro`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 
                    nome: nome, 
                    email: email, 
                    senha: senha, 
                    dataNascimento: dataNascimento 
                })
            });

            if (resposta.ok) {
                const usuario = await resposta.json();
                if (mensagem) {
                    mensagem.textContent = 'Conta criada com sucesso, ' + usuario.nome + '!';
                    mensagem.className = 'mensagem mensagem-sucesso';
                }
                setTimeout(function() {
                    window.location.href = 'index.html';
                }, 1500);
            } else {
                const mensagemErro = await resposta.text();
                if (mensagem) {
                    mensagem.textContent = mensagemErro;
                    mensagem.className = 'mensagem mensagem-erro';
                } else {
                    alert('Aviso: ' + mensagemErro);
                }
            }
        } catch (erro) {
            if (mensagem) {
                mensagem.textContent = 'Não foi possível ligar ao servidor.';
                mensagem.className = 'mensagem mensagem-erro';
            }
            console.error('Erro na API:', erro);
        }
    });
}