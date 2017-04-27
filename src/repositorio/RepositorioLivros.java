package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Livro;

/**
 * Guarda Todos os livros
 * @author Rafael Abreu
 */
public class RepositorioLivros {

    private List<Livro> livros;
    
    public RepositorioLivros() {
        this.livros = new ArrayList<Livro>();
    }
    
    /**
     * Adiciona a lista de livros
     * 
     * @param livro
     * @return booelan
     */
    public boolean addLivro(Livro livro) {
        return (this.livros.add(livro));
    }
    
    /**
     * Retorna lista de livros
     * 
     * @return List<Livro>
     */
    public List<Livro> getListaLivros() {
        return this.livros;
    }
    
    /**
     * Retorna lista de livros disponívels
     * 
     * @return List<Livro>
     */
    public List<Livro> getListaLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<Livro>();
        for(Livro livro : livros) {
            if(livro.getDisponivel()) {
                disponiveis.add(livro);
            }
        }
        
        return disponiveis;
    }
    
    /**
     * Verifica se livro já existe na lista
     * 
     * @param isbn
     * @return booelan
     */
    public boolean livroExiste(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }    
    
    /**
     * Retorna um livro pelo ISBN
     * 
     * @param isbn
     * @return Livro
     */
    public Livro buscarLivro(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
           }
        }
        return null;
    }
    
}
