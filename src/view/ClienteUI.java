package view;

import dao.impl_DB.ClienteDaoDb;
import java.util.List;
import model.Cliente;
import repositorio.RepositorioClientes;
import util.Console;
import view.menu.ClienteMenu;

public class ClienteUI {
    
    private RepositorioClientes lista;
    private ClienteDaoDb clienteDaoDb;

    public ClienteUI(RepositorioClientes lista) {
        this.clienteDaoDb = new ClienteDaoDb();
        this.lista = lista;
    }
    
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(ClienteMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case ClienteMenu.OP_CADASTRAR:
                    cadastrar();
                    break;
                case ClienteMenu.OP_LISTAR:
                    listar();
                    break;
                case ClienteMenu.OP_MAIS_RETIRARAM:
                    listarClientesRetiraram();
                    break;
                case ClienteMenu.OP_ATRASADOS:
                    listarClientesRetiraram();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != ClienteMenu.OP_VOLTAR);
    }
    
    private void cadastrar() {
        String matricula = Console.scanString("Matrícula: ");
        String nome = Console.scanString("Nome: ");
        String telefone = Console.scanString("Telefone: ");
        clienteDaoDb.salvar(new Cliente(matricula, nome, telefone));
        System.out.println("Cliente " + nome + " cadastrado com sucesso!");
    }
    
    public void imprimir(List<Cliente> clientes) {
        if(clientes.size() <= 0){
            System.out.println("-----------------------------");        
            System.out.println("Nao ha clientes cadastrados");
            System.out.println("-----------------------------\n");
        }else{
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "MATRÍCULA") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|TELEFONE") + "\t"
                    + String.format("%-10s", "|LIVROS RETIRADOS") + "\t"
            );
            for (Cliente cliente : clientes) {
                System.out.println(String.format("%-10s", cliente.getMatricula()) + "\t"
                        + String.format("%-20s", "|" + cliente.getNome()) + "\t"
                        + String.format("%-20s", "|" + cliente.getTelefone()) + "\t"
                        + String.format("%-10s", "|" + cliente.getTotalLivrosRetirados()) + "\t"
                );
            }
        }
    }
    
    public void listar() {
        imprimir(lista.getListaClientes());
    }
    
    public void listarClientesRetiraram() {
        imprimir(lista.getListaClientesRetiraram());
    }
    
    public void listarClientesAtrasados() {
        imprimir(lista.getListaClientesRetiraram());
    }
    
}
