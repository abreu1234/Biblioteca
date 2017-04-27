package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class RepositorioClientes {

    private List<Cliente> clientes;
    
    public RepositorioClientes() {
        this.clientes = new ArrayList<Cliente>();
    }
    
    public boolean addCliente(Cliente cliente) {
        return (this.clientes.add(cliente));
    }
    
    public List<Cliente> getListaClientes() {
        return this.clientes;
    }
    
    public boolean clienteExiste(String matricula) {
        for (Cliente cliente : clientes) {
            if (cliente.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }    
    
    public Cliente buscarCliente(String matricula) {
        for (Cliente cliente : clientes) {
            if (cliente.getMatricula().equals(matricula)) {
                return cliente;
           }
        }
        return null;
    }
    
    public List<Cliente> getListaClientesRetiraram() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (Cliente cliente: this.clientes) {
            if(cliente.getTotalLivrosRetirados() > 0) {
                clientes.add(cliente);
            }
        }
        
        return clientes;
    }
    
    public List<Cliente> getListaClientesAtrasados() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (Cliente cliente: this.clientes) {
            if(cliente.getDiasAstraso()> 0) {
                clientes.add(cliente);
            }
        }
        
        return clientes;
    }
    
}
