package dao;

import java.util.List;
import model.Livro;

public interface LivroDao {
    public void salvar(Livro livro);
    public void deletar(Livro livro);
    public void atualizar(Livro livro);
    public List<Livro> listar();
    public Livro procurarPorId(int id);
}
