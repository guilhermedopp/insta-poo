package com.bo;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import com.dao.PostDAO;
import com.dao.UsuarioDAO;
import com.vo.PostVO;
import com.vo.UsuarioVO;

public class UsuarioBO {
    private PostDAO postDao = new PostDAO();
    private UsuarioDAO dao = new UsuarioDAO();

    private boolean consultarReceitaFederal(String cpf, LocalDate dataNascimentoInformada) {
        Map<String, LocalDate> baseGoverno = new HashMap<>();
        baseGoverno.put("12345678900", LocalDate.of(1955, 5, 20));
        baseGoverno.put("98765432100", LocalDate.of(1960, 10, 15));
        if (baseGoverno.containsKey(cpf)) {
            LocalDate dataReal = baseGoverno.get(cpf);
            return dataReal.equals(dataNascimentoInformada);
        }
        return false;
    }

    public UsuarioVO cadastrar(UsuarioVO vo, String cpf) throws Exception {
        if (vo.getNome() == null || vo.getNome().trim().isEmpty()) throw new Exception("O nome não pode estar vazio.");
        if (vo.getEmail() == null || !vo.getEmail().contains("@")) throw new Exception("Insira um e-mail válido.");
        if (vo.getSenha() == null || vo.getSenha().length() < 3) throw new Exception("Senha muito curta.");
        if (vo.getDataNascimento() == null) throw new Exception("Data de nascimento obrigatória.");
        if (cpf == null || cpf.trim().isEmpty()) throw new Exception("CPF obrigatório.");
        
        cpf = cpf.replaceAll("[^0-9]", "");
        if (!consultarReceitaFederal(cpf, vo.getDataNascimento())) {
            throw new Exception("ALERTA DE FRAUDE: CPF não existe ou não corresponde à data de nascimento.");
        }

        int idade = Period.between(vo.getDataNascimento(), LocalDate.now()).getYears();
        if (idade < 60) throw new Exception("Exclusivo para pessoas com 60 anos ou mais.");
        if (dao.buscarPorEmail(vo.getEmail()) != null) throw new Exception("E-mail já cadastrado.");

        return dao.salvar(vo);
    }

    public UsuarioVO login(String email, String senha) throws Exception {
        UsuarioVO usuario = dao.buscarPorEmailESenha(email, senha);
        if (usuario == null) throw new Exception("E-mail ou senha incorretos.");
        return usuario; 
    }

    public PostVO criarPostagem(UsuarioVO autor, String texto) throws Exception {
        if (texto == null || texto.trim().isEmpty()) throw new Exception("A mensagem não pode estar vazia.");
        String textoMin = texto.toLowerCase();
        if (textoMin.contains("http://") || textoMin.contains("https://")) throw new Exception("Bloqueio de segurança: Links não permitidos.");
        return postDao.salvar(new PostVO(0, texto, autor)); 
    }

    // ==========================================
    // REGRAS DE INTERAÇÃO SOCIAL
    // ==========================================
    public boolean processarCurtida(int usuarioId, int postId) throws Exception {
        return postDao.alternarCurtida(usuarioId, postId);
    }

    public void adicionarComentario(int usuarioId, int postId, String texto) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("O comentário não pode estar vazio.");
        }
        postDao.salvarResposta(usuarioId, postId, texto);
    }
}