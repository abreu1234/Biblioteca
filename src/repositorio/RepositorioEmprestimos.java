package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Emprestimo;

public class RepositorioEmprestimos {

    private List<Emprestimo> emprestimos;
    
    public RepositorioEmprestimos() {
        this.emprestimos = new ArrayList<Emprestimo>();
    }
    
    public boolean addEmprestimo(Emprestimo emprestimos) {
        return (this.emprestimos.add(emprestimos));
    }
    
    public List<Emprestimo> getListaLivros() {
        return this.emprestimos;
    }
    
    public Emprestimo buscar(Cliente cliente) {
        for (Emprestimo emprestimo : emprestimos) {
            if (!emprestimo.getEntregue() && emprestimo.getCliente().equals(cliente)) {
                return emprestimo;
           }
        }
        return null;
    }
    
}
