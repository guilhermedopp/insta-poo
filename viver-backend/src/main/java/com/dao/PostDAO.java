package com.dao;

import com.vo.PostVO;
import com.vo.UsuarioVO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public PostVO salvar(PostVO post) throws Exception {
        String sql = "INSERT INTO postagens (conteudo, usuario_id) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, post.getConteudo());
            stmt.setInt(2, post.getAutor().getId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return new PostVO(rs.getInt(1), post.getConteudo(), post.getAutor());
            }
            return post;
        } catch (Exception e) { throw new Exception("Erro ao salvar a postagem: " + e.getMessage()); }
    }

    public List<PostVO> listarTodos() throws Exception {
        String sql = "SELECT p.id, p.conteudo, p.data_criacao, u.id AS user_id, u.nome, u.email, u.data_nascimento " +
                     "FROM postagens p INNER JOIN usuarios u ON p.usuario_id = u.id ORDER BY p.data_criacao DESC";
        List<PostVO> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LocalDate dataNasc = rs.getDate("data_nascimento") != null ? rs.getDate("data_nascimento").toLocalDate() : null;
                UsuarioVO autor = new UsuarioVO(rs.getInt("user_id"), rs.getString("nome"), rs.getString("email"), "", dataNasc);
                PostVO post = new PostVO(rs.getInt("id"), rs.getString("conteudo"), autor);
                post.setData(rs.getTimestamp("data_criacao")); 
                lista.add(post);
            }
        } catch (Exception e) { throw new Exception("Erro ao listar postagens: " + e.getMessage()); }
        return lista;
    }

    // ==========================================
    // NOVOS MÉTODOS DE INTERAÇÃO
    // ==========================================

    // Alterna a curtida: Se já curtiu, remove. Se não curtiu, adiciona. Retorna true se adicionou.
    public boolean alternarCurtida(int usuarioId, int postId) throws Exception {
        String checkSql = "SELECT * FROM curtidas WHERE usuario_id = ? AND post_id = ?";
        try (Connection conn = ConexaoDB.conectar(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, usuarioId);
            checkStmt.setInt(2, postId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Já curtiu, então vamos remover (Descurtir)
                String deleteSql = "DELETE FROM curtidas WHERE usuario_id = ? AND post_id = ?";
                try(PreparedStatement delStmt = conn.prepareStatement(deleteSql)) {
                    delStmt.setInt(1, usuarioId);
                    delStmt.setInt(2, postId);
                    delStmt.executeUpdate();
                }
                return false; // Retorna falso porque removeu a curtida
            } else {
                // Não curtiu, então vamos adicionar
                String insertSql = "INSERT INTO curtidas (usuario_id, post_id) VALUES (?, ?)";
                try(PreparedStatement insStmt = conn.prepareStatement(insertSql)) {
                    insStmt.setInt(1, usuarioId);
                    insStmt.setInt(2, postId);
                    insStmt.executeUpdate();
                }
                return true; // Retorna verdadeiro porque adicionou a curtida
            }
        } catch (Exception e) { throw new Exception("Erro ao processar curtida: " + e.getMessage()); }
    }

    public void salvarResposta(int usuarioId, int postId, String conteudo) throws Exception {
        String sql = "INSERT INTO respostas (conteudo, usuario_id, post_id) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, conteudo);
            stmt.setInt(2, usuarioId);
            stmt.setInt(3, postId);
            stmt.executeUpdate();
        } catch (Exception e) { throw new Exception("Erro ao salvar comentário: " + e.getMessage()); }
    }
}