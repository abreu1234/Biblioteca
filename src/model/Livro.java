package model;

import java.time.LocalDate;
import java.util.Objects;

public class Livro {
    
    private String isbn, nome, autores, editora;
    private LocalDate dataPublicacao;
    private boolean disponivel;
    
    public Livro(String isbn, String nome, String autores, String editora, LocalDate dataPublicacao) {
        this.isbn = isbn;
        this.nome = nome;
        this.autores = autores;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.disponivel = true;
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
