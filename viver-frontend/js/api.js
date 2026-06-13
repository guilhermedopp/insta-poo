const API_URL = 'http://localhost:8080/api';

// Função auxiliar de Validação de CPF (Algoritmo Oficial)
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]+/g,'');
    if(cpf == '' || cpf.length != 11 || /^(\d)\1{10}$/.test(cpf)) return false;
    let add = 0;
    for (let i=0; i < 9; i ++) add += parseInt(cpf.charAt(i)) * (10 - i);
    let rev = 11 - (add % 11);
    if (rev == 10 || rev == 11) rev = 0;
    if (rev != parseInt(cpf.charAt(9))) return false;
    add = 0;
    for (let i = 0; i < 10; i ++) add += parseInt(cpf.charAt(i)) * (11 - i);
    rev = 11 - (add % 11);
    if (rev == 10 || rev == 11) rev = 0;
    if (rev != parseInt(cpf.charAt(10))) return false;
    return true;
}

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
        const confirmarSenha = document.getElementById('confirmarSenha').value;
        const dataNascimento = document.getElementById('dataNascimento').value;
        const cpf = document.getElementById('cpf') ? document.getElementById('cpf').value : '';

        const mensagem = document.getElementById('mensagem-cadastro');

        // 1. Validar se as senhas são iguais
        if (senha !== confirmarSenha) {
            mensagem.textContent = 'As senhas não coincidem. Verifique e tente novamente.';
            mensagem.className = 'mensagem mensagem-erro';
            return;
        }

        // 2. Validar CPF
        if (!validarCPF(cpf)) {
            mensagem.textContent = 'O CPF inserido é inválido. Por favor, verifique os números.';
            mensagem.className = 'mensagem mensagem-erro';
            return;
        }

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
                    // Nota: O CPF foi validado no frontend. Poderia ser enviado ao backend se tivesse criado a coluna no MySQL.
                })
            });

            if (resposta.ok) {
                const usuario = await resposta.json();
                if (mensagem) {
                    mensagem.textContent = 'Conta criada com sucesso, ' + usuario.nome + '!';
                    mensagem.className = 'mensagem mensagem-sucesso';
                }
                
                // Grava o e-mail temporariamente para autocompletar na página de login
                localStorage.setItem('emailRecenteCadastro', email);

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