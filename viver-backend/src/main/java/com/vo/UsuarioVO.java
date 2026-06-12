package com.vo;

import java.util.ArrayList; // 1. Importação corrigida para localizar a interface Observer
import java.util.List;

import com.observer.Observer;

public class UsuarioVO {
    private int id; 
    private String nome; 
    private String email;
    private String senha; 
    private List<Observer> seguidores = new ArrayList<>();

    // Construtor corrigido e fechado adequadamente
    public UsuarioVO(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha; 
    } // <-- Chave de fechamento do construtor que estava faltando

    // Métodos do Observer agora no escopo correto da classe
    public void adicionarSeguidor(Observer seguidor) {
        this.seguidores.add(seguidor);
    }

    public void notificarSeguidores(String mensagem) {
        for (Observer seguidor : seguidores) {
            seguidor.atualizar(mensagem); // Aciona o método do Seguidor
        }
    }

    // Getters e Setters (Encapsulamento)
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public int getId() { return id; }
}