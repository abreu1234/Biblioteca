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
                case ClienteMenu.OP_ATUALIZAR:
                    atualizar();
                    break;
                case ClienteMenu.OP_DELETAR:
                    deletar();
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
        if(!matricula.isEmpty() && !clienteDaoDb.checkMatricula(matricula)) {
            String nome = Console.scanString("Nome: ");
            String telefone = Console.scanString("Telefone: ");
            if(!nome.isEmpty() && !telefone.isEmpty()) {
                clienteDaoDb.salvar(new Cliente(matricula, nome, telefone));
                System.out.println("Cliente " + nome + " cadastrado com sucesso!");
            }else{
                System.out.println("Preencha todas as informações!");
            }
        }else{
            System.out.println("Matrícula " + matricula + " já existente!");
        }
    }
    
    private void deletar() {
        int id = Console.scanInt("ID do cliente a ser deletado: ");
        Cliente cliente = clienteDaoDb.procurarPorId(id);
        this.imprimir(cliente);
        if (UIUtil.getConfirmacao("Realmente deseja excluir esse paciente?")) {
            clienteDaoDb.deletar(cliente);
            System.out.println("Paciente deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
    }
    
    private void atualizar() {
        int id = Console.scanInt("ID do cliente: ");

        Cliente cliente = clienteDaoDb.procurarPorId(id);
        if(cliente == null) {
            System.out.println("Cliente não encontrado");
        }else {
            this.imprimir(cliente);

            System.out.println("Digite os dados do cliente que quer alterar [Vazio caso nao queira]");
            String nome = Console.scanString("Nome: ");
            String telefone = Console.scanString("Telefone: ");
            if (!nome.isEmpty()) {
                cliente.setNome(nome);
            }
            if (!telefone.isEmpty()) {
                cliente.setTelefone(telefone);
            }

            clienteDaoDb.atualizar(cliente);
            System.out.println("Cliente " + nome + " atualizado com sucesso!");
        }
    }
    
    private void imprimir(Cliente cliente) {
        System.out.println("-----------------------------\n");
        System.out.println(String.format("%-10s", "ID") + "\t"
                + String.format("%-10s", "MATRÍCULA") + "\t"
                + String.format("%-20s", "|NOME") + "\t"
                + String.format("%-20s", "|TELEFONE") + "\t"
                + String.format("%-10s", "|LIVROS RETIRADOS") + "\t"
        );
        System.out.println(String.format("%-10s", cliente.getId()) + "\t"
                        + String.format("%-10s", cliente.getMatricula()) + "\t"
                        + String.format("%-20s", "|" + cliente.getNome()) + "\t"
                        + String.format("%-20s", "|" + cliente.getTelefone()) + "\t"
                        + String.format("%-10s", "|" + cliente.getTotalLivrosRetirados()) + "\t"
                );
    }
    
    public void imprimir(List<Cliente> clientes) {
        if(clientes.size() <= 0){
            System.out.println("-----------------------------");        
            System.out.println("Nao ha clientes cadastrados");
            System.out.println("-----------------------------\n");
        }else{
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ID") + "\t"
                + String.format("%-10s", "MATRÍCULA") + "\t"
                + String.format("%-20s", "|NOME") + "\t"
                + String.format("%-20s", "|TELEFONE") + "\t"
                + String.format("%-10s", "|LIVROS RETIRADOS") + "\t"
        );
            for (Cliente cliente : clientes) {
                System.out.println(String.format("%-10s", cliente.getId()) + "\t"
                        + String.format("%-10s", cliente.getMatricula()) + "\t"
                        + String.format("%-20s", "|" + cliente.getNome()) + "\t"
                        + String.format("%-20s", "|" + cliente.getTelefone()) + "\t"
                        + String.format("%-10s", "|" + cliente.getTotalLivrosRetirados()) + "\t"
                );
            }
        }
    }
    
    public void listar() {
        imprimir(clienteDaoDb.listar());
    }
    
    public void listarClientesRetiraram() {
        imprimir(clienteDaoDb.listarClientesRetiraram());
    }
    
    public void listarClientesAtrasados() {
        imprimir(clienteDaoDb.listarClientesAtrasados());
    }
    
}
