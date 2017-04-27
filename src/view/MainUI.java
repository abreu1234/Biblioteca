package view;

import model.Cliente;
import model.Livro;
import repositorio.RepositorioClientes;
import repositorio.RepositorioEmprestimos;
import repositorio.RepositorioLivros;
import util.Console;
import util.DateUtil;
import view.menu.MainMenu;

/**
 *
 * @author 631710399
 */
public class MainUI {
    
    private RepositorioLivros listaLivros;
    private RepositorioClientes listaClientes;
    private RepositorioEmprestimos listaEmprestimos;
    
    public MainUI() {
        listaLivros = new RepositorioLivros();
        listaClientes = new RepositorioClientes();
        listaEmprestimos = new RepositorioEmprestimos();
        this.init();
    }
    
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(MainMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case MainMenu.OP_LIVROS:
                    new LivroUI(listaLivros, listaClientes, listaEmprestimos).executar();
                    break;
                case MainMenu.OP_CLIENTES:
                    new ClienteUI(listaClientes).executar();
                    break;
                case MainMenu.OP_SAIR:
                    System.out.println("Aplicação finalizada!!!");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != MainMenu.OP_SAIR);
    }
    
    private void init() {
        for(int i = 0; i<=10;i++) {
            this.listaLivros.addLivro(new Livro( "#"+i, "Nome "+i, "Autores "+i, "Editora "+i, DateUtil.stringToDate("13/12/1990") ));
            this.listaClientes.addCliente(new Cliente("#"+i, "Nome "+i, "Autores"+i ));
        }
    }
    
}
