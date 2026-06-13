package com.vo;

import java.util.Date;

public class RespostaVO {
    private int id;
    private String conteudo;
    private Date data;
    private UsuarioVO autor;
    private int postId;

    // Construtor vazio para o Jackson (JSON)
    public RespostaVO() {}

    public RespostaVO(int id, String conteudo, UsuarioVO autor, int postId) {
        this.id = id;
        this.conteudo = conteudo;
        this.autor = autor;
        this.postId = postId;
        this.data = new Date();
    }

    public void setData(Date data){ this.data = data; }

    public int getId() { return id; }
    public String getConteudo() { return conteudo; }
    public Date getData() { return data; }
    public UsuarioVO getAutor() { return autor; }
    public int getPostId() { return postId; }
}