package view;

import java.util.List;
import model.Cliente;
import repositorio.RepositorioClientes;
import util.Console;
import view.menu.ClienteMenu;

public class ClienteUI {
    
    private RepositorioClientes lista;

    public ClienteUI(RepositorioClientes lista) {
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
                    listar();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != ClienteMenu.OP_VOLTAR);
    }
    
    private void cadastrar() {
        String matricula = Console.scanString("Matrícula: ");
        if (lista.clienteExiste(matricula)) {
            System.out.println("Cliente já existe");
        } else {
            String nome = Console.scanString("Nome: ");
            String autores = Console.scanString("Telefone: ");
            lista.addCliente(new Cliente(matricula, nome, autores));
            System.out.println("Cliente " + nome + " cadastrado com sucesso!");
        }
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
        imprimir(lista.getListaClientesRetiraram());
    }
    
}
