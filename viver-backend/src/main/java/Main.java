import com.bo.UsuarioBO;
import com.dao.PostDAO;
import com.vo.PostVO;
import com.vo.UsuarioVO;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        
        // 1. Inicia o servidor Javalin na porta 8080
        Javalin app = Javalin.create(config -> {
            // Permite que o nosso Frontend (HTML/JS) consiga comunicar com o Java sem ser bloqueado (CORS)
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(8080);

        System.out.println("Servidor Backend VIVER+ iniciado com sucesso na porta 8080!");

        // Instâncias das camadas de Negócio e Dados
        UsuarioBO usuarioBO = new UsuarioBO();
        PostDAO postDAO = new PostDAO();

        // 2. Rota que recebe os pedidos de Login do Frontend
        app.post("/api/login", ctx -> {
            try {
                DadosLogin dados = ctx.bodyAsClass(DadosLogin.class);
                UsuarioVO usuarioLogado = usuarioBO.login(dados.email, dados.senha);
                ctx.status(200).json(usuarioLogado);
            } catch (Exception e) {
                ctx.status(401).result(e.getMessage());
            }
        });

        // 3. Rota que recebe os pedidos de Cadastro do Frontend
        app.post("/api/cadastro", ctx -> {
            try {
                DadosCadastro dados = ctx.bodyAsClass(DadosCadastro.class);
                UsuarioVO novoUsuario = new UsuarioVO(0, dados.nome, dados.email, dados.senha);
                UsuarioVO usuarioCadastrado = usuarioBO.cadastrar(novoUsuario);
                ctx.status(201).json(usuarioCadastrado);
            } catch (Exception e) {
                ctx.status(400).result(e.getMessage());
            }
        });

        // 4. Rota para ir buscar todos os momentos publicados (Feed)
        app.get("/api/posts", ctx -> {
            try {
                ctx.status(200).json(postDAO.listarTodos());
            } catch (Exception e) {
                ctx.status(500).result(e.getMessage());
            }
        });

        // 5. Rota para publicar um novo momento
        app.post("/api/posts", ctx -> {
            try {
                DadosPost dados = ctx.bodyAsClass(DadosPost.class);
                PostVO postCriado = usuarioBO.criarPostagem(dados.autor, dados.texto);
                ctx.status(201).json(postCriado);
            } catch (Exception e) {
                ctx.status(400).result(e.getMessage());
            }
        });

    } // <-- O método main AGORA fecha aqui, englobando todas as variáveis e rotas!

    // Classes auxiliares estáticas para mapeamento de JSON
    public static class DadosLogin {
        public String email;
        public String senha;
    }

    public static class DadosCadastro {
        public String nome;
        public String email;
        public String senha;
    }

    public static class DadosPost {
        public String texto;
        public UsuarioVO autor;
    }
}