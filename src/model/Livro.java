package model;

import java.time.LocalDate;
import java.util.Objects;

public class Livro {
    
    private int id;
    private String isbn, nome, autores, editora;
    private LocalDate dataPublicacao;
    private int qtdRetirado;
    private boolean disponivel;
    
    public Livro(String isbn, String nome, String autores, String editora, LocalDate dataPublicacao) {
        this.isbn = isbn;
        this.nome = nome;
        this.autores = autores;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.disponivel = true;
        this.qtdRetirado = 0;
    }
    
    public Livro(int id, String isbn, String nome, String autores, String editora, LocalDate dataPublicacao) {
        this.id = id;
        this.isbn = isbn;
        this.nome = nome;
        this.autores = autores;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.disponivel = true;
        this.qtdRetirado = 0;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtdRetirado() {
        return qtdRetirado;
    }

    public void setQtdRetirado(int qtdRetirado) {
        this.qtdRetirado = qtdRetirado;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public String getNome() {
        return nome;
    }

    public String getAutores() {
        return autores;
    }

    public String getEditora() {
        return editora;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }
    
    public boolean getDisponivel() {
        return disponivel;
    }
    
    public int getLivroRetirado() {
        return qtdRetirado;
    }
    
    public void livroRetirado() {
        this.qtdRetirado++;
    }
    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Livro other = (Livro) obj;
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        return true;
    }
    
}
