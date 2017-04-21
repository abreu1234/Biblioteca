package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Livro;

public class RepositorioLivros {

    private List<Livro> livros;
    
    public RepositorioLivros() {
        this.livros = new ArrayList<Livro>();
    }
    
    public boolean addLivro(Livro livro) {
        return (this.livros.add(livro));
    }
    
    public List<Livro> getListaLivros() {
        return this.livros;
    }
    
    public boolean livroExiste(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }    
    
    public Livro buscarLivro(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
           }
        }
        return null;
    }
    
}
