package view;

import model.Livro;
import repositorio.RepositorioClientes;
import repositorio.RepositorioEmprestimos;
import repositorio.RepositorioLivros;
import util.Console;
import util.DateUtil;
import view.menu.RelatorioMenu;

public class RelatorioUI {
    
    private RepositorioLivros listaLivros;
    private RepositorioEmprestimos listaEmprestimo;
    private RepositorioClientes listaClientes;

    public RelatorioUI(RepositorioLivros listaLivros, RepositorioClientes listaClientes, RepositorioEmprestimos listaEmprestimo) {
        this.listaLivros = listaLivros;
        this.listaEmprestimo = listaEmprestimo;
        this.listaClientes = listaClientes;
    }
    
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(RelatorioMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case RelatorioMenu.OP_DISPONIVEIS:
                    livrosDisponiveis();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != RelatorioMenu.OP_VOLTAR);
    }
        
    public void livrosDisponiveis() {
        if(listaLivros.getListaLivrosDisponiveis().size() <= 0){
            System.out.println("-----------------------------");        
            System.out.println("Nao ha livros disponiveis");
            System.out.println("-----------------------------\n");
        }
        else{
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ISBN") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|AUTORES") + "\t"
                    + String.format("%-20s", "|EDITORA") + "\t"
                    + String.format("%-20s", "|DATA DE PUBLICAÇÃO"));
            for (Livro livro : listaLivros.getListaLivrosDisponiveis()) {
                System.out.println(String.format("%-10s", livro.getIsbn()) + "\t"
                        + String.format("%-20s", "|" + livro.getNome()) + "\t"
                        + String.format("%-20s", "|" + livro.getAutores()) + "\t"
                        + String.format("%-20s", "|" + livro.getEditora()) + "\t"
                        + String.format("%-20s", "|" + DateUtil.dateToString(livro.getDataPublicacao()))
                );
            }
        }

    }
    
}
