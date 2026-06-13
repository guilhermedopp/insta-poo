import com.bo.UsuarioBO;
import com.dao.PostDAO;
import com.vo.PostVO;
import com.vo.UsuarioVO;
import io.javalin.Javalin;
import java.time.LocalDate; 

public class Main {
    public static void main(String[] args) {
        
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(8080);

        System.out.println("🔥 Servidor Backend VIVER+ online! API a correr em: http://localhost:8080");

        UsuarioBO usuarioBO = new UsuarioBO();
        PostDAO postDAO = new PostDAO();

        app.post("/api/login", ctx -> {
            try {
                DadosLogin dados = ctx.bodyAsClass(DadosLogin.class);
                UsuarioVO usuarioLogado = usuarioBO.login(dados.email, dados.senha);
                ctx.status(200).json(usuarioLogado);
            } catch (Exception e) { ctx.status(401).result(e.getMessage()); }
        });

        app.post("/api/cadastro", ctx -> {
            try {
                DadosCadastro dados = ctx.bodyAsClass(DadosCadastro.class);
                UsuarioVO novoUsuario = new UsuarioVO(0, dados.nome, dados.email, dados.senha, LocalDate.parse(dados.dataNascimento));
                UsuarioVO usuarioCadastrado = usuarioBO.cadastrar(novoUsuario, dados.cpf);
                ctx.status(201).json(usuarioCadastrado);
            } catch (Exception e) { ctx.status(400).result(e.getMessage()); }
        });

        app.get("/api/posts", ctx -> {
            try { ctx.status(200).json(postDAO.listarTodos()); } 
            catch (Exception e) { ctx.status(500).result(e.getMessage()); }
        });

        app.post("/api/posts", ctx -> {
            try {
                DadosPost dados = ctx.bodyAsClass(DadosPost.class);
                PostVO postCriado = usuarioBO.criarPostagem(dados.autor, dados.texto);
                ctx.status(201).json(postCriado);
            } catch (Exception e) { ctx.status(400).result(e.getMessage()); }
        });

        // ==========================================
        // NOVAS ROTAS DA API: CURTIR E RESPONDER
        // ==========================================
        
        // Rota para Curtir (Recebe o ID do Post pela URL e o ID do Usuário pelo Corpo)
        app.post("/api/posts/{id}/curtir", ctx -> {
            try {
                int postId = Integer.parseInt(ctx.pathParam("id"));
                DadosInteracao dados = ctx.bodyAsClass(DadosInteracao.class);
                boolean curtiu = usuarioBO.processarCurtida(dados.usuarioId, postId);
                ctx.status(200).result(curtiu ? "curtiu" : "descurtiu");
            } catch (Exception e) { ctx.status(400).result(e.getMessage()); }
        });

        // Rota para Responder/Comentar
        app.post("/api/posts/{id}/responder", ctx -> {
            try {
                int postId = Integer.parseInt(ctx.pathParam("id"));
                DadosInteracao dados = ctx.bodyAsClass(DadosInteracao.class);
                usuarioBO.adicionarComentario(dados.usuarioId, postId, dados.texto);
                ctx.status(201).result("Comentário gravado com sucesso");
            } catch (Exception e) { ctx.status(400).result(e.getMessage()); }
        });
    } 

    // Classes auxiliares para mapeamento de JSON
    public static class DadosLogin { public String email; public String senha; }
    public static class DadosCadastro { public String nome; public String email; public String senha; public String dataNascimento; public String cpf; }
    public static class DadosPost { public String texto; public UsuarioVO autor; }
    
    // Nova classe auxiliar para receber os dados de interações
    public static class DadosInteracao {
        public int usuarioId;
        public String texto;
    }
}