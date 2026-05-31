// Aguarda que o formulário seja submetido
const formLogin = document.getElementById('form-login');

if (formLogin) {
    formLogin.addEventListener('submit', async function(event) {
        // Evita que a página recarregue ao clicar no botão
        event.preventDefault();

        // 1. Captura os dados que o idoso digitou nos campos
        const email = document.getElementById('email').value;
        const senha = document.getElementById('senha').value;

        try {
            // 2. Envia um pacote de dados (JSON) para o servidor Java
            const resposta = await fetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email, senha: senha })
            });

            // 3. Verifica a resposta do servidor
            if (resposta.ok) {
                // Sucesso: O Java confirmou o login
                const dadosUsuario = await resposta.json();
                alert('Bem-vindo(a) ao VIVER+, ' + dadosUsuario.nome + '!');
                
                // No futuro, esta linha vai enviar o utilizador para o Feed:
                // window.location.href = 'feed.html';
            } else {
                // Erro: E-mail ou palavra-passe incorretos (regra do UsuarioBO)
                const mensagemErro = await resposta.text();
                alert('Aviso: ' + mensagemErro);
            }

        } catch (erro) {
            // Se o servidor Java estiver desligado
            alert('Não foi possível conectar ao VIVER+. Verifique a sua internet ou contacte o suporte.');
            console.error('Erro na API:', erro);
        }
    });
}