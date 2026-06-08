package com.bo;

import com.dao.UsuarioDAO;
import com.vo.PostVO;
import com.vo.UsuarioVO;

public class UsuarioBO {
    private UsuarioDAO dao = new UsuarioDAO();

    public UsuarioVO cadastrar(UsuarioVO vo) throws Exception {
        if (vo.getNome() == null || vo.getNome().trim().isEmpty()) {
            throw new Exception("O nome nao pode estar vazio.");
        }
        if (vo.getEmail() == null || !vo.getEmail().contains("@")) {
            throw new Exception("Por favor, insira um e-mail valido com '@'.");
        }
        if (vo.getSenha() == null || vo.getSenha().length() < 3) {
            throw new Exception("Sua senha e muito curta. Use pelo menos 3 caracteres.");
        }
        if (dao.buscarPorEmail(vo.getEmail()) != null) {
            throw new Exception("Este e-mail ja esta cadastrado. Tente fazer login.");
        }

        return dao.salvar(vo);
    }

    public UsuarioVO login(String email, String senha) throws Exception {
        UsuarioVO usuario = dao.buscarPorEmailESenha(email, senha);
        if (usuario == null) {
            throw new Exception("E-mail ou senha incorretos. Tente novamente.");
        }
        return usuario;
    }

    public boolean seguir(UsuarioVO seguidor, UsuarioVO alvo) throws Exception {
        if (seguidor.getId() == alvo.getId()) {
            throw new Exception("Voce nao pode seguir a si mesmo!");
        }
        return true;
    }

    public PostVO criarPostagem(UsuarioVO autor, String texto) throws Exception {
        if (texto.contains("http://") || texto.contains("clique aqui")) {
            throw new Exception("ALERTA DE SEGURANCA: Evite links externos para sua protecao.");
        }
        return new PostVO(1, texto, autor);
    }
}
