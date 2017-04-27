package view;

import java.time.format.DateTimeParseException;
import model.Cliente;
import model.Emprestimo;
import model.Livro;
import repositorio.RepositorioClientes;
import repositorio.RepositorioEmprestimos;
import repositorio.RepositorioLivros;
import util.Console;
import util.DateUtil;
import view.menu.LivroMenu;

public class LivroUI {
    
    private RepositorioLivros lista;
    private RepositorioEmprestimos listaEmprestimo;
    private RepositorioClientes listaClientes;

    public LivroUI(RepositorioLivros lista, RepositorioClientes listaClientes, RepositorioEmprestimos listaEmprestimo) {
        this.lista = lista;
        this.listaEmprestimo = listaEmprestimo;
        this.listaClientes = listaClientes;
    }
    
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(LivroMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case LivroMenu.OP_CADASTRAR:
                    cadastrar();
                    break;
                case LivroMenu.OP_LISTAR:
                    listar();
                    break;
                case LivroMenu.OP_EMPRESTIMO:
                    emprestar();
                    break;
                case LivroMenu.OP_DEVOLUCAO:
                    devolver();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != LivroMenu.OP_VOLTAR);
    }
    
    private void emprestar() {
        String matricula = Console.scanString("Matrícula do cliente: ");
        Cliente cliente = listaClientes.buscarCliente(matricula);
        if( cliente != null ) {
            try{
                String dataEntrega = Console.scanString("Data da entrega: ");
                Emprestimo emprestimo = new Emprestimo(cliente, DateUtil.stringToDate(dataEntrega));
                int maxLivro = 0;
                do{
                    String isbn = Console.scanString("ISBN do livro: ");
                    Livro livro = lista.buscarLivro(isbn);
                    if ( livro != null && livro.getDisponivel() ) {
                        maxLivro++;
                        emprestimo.adicionarLivro(livro);
                        livro.setDisponivel(false);
                        if(maxLivro < 3) {
                            int outroLivro = Console.scanInt("Deseja retirar outro livro?: ");
                            if(outroLivro != 1)
                                maxLivro = 3;
                        }
                    }else{
                        System.out.println("Livro indisponível ou não existe.");
                    } 
                }while(maxLivro < 3);
                System.out.println("Empréstimo realizado com sucesso. ID: "+emprestimo);
            }catch (DateTimeParseException ex) {
                System.out.println("Data ou hora no formato inválido!");                
            }
        }else{
            System.out.println("Cliente não existe.");
        }      
    }
    
    private void devolver() {
        String matricula = Console.scanString("Matrícula do cliente: ");
        Cliente cliente = listaClientes.buscarCliente(matricula);
        Emprestimo emprestimo = this.listaEmprestimo.buscar(cliente);
        if( emprestimo != null ) {
            emprestimo.entregar();
            System.out.println("Livros entregues com sucesso.");
        }else{
            System.out.println("Cliente não possui empréstimos pendentes.");
        }   
    }
    
    private void cadastrar() {
        String isbn = Console.scanString("ISBN: ");
        if (lista.livroExiste(isbn)) {
            System.out.println("Livro já existe");
        } else {
            String nome = Console.scanString("Nome: ");
            String autores = Console.scanString("Autores: ");
            String editora = Console.scanString("Editora: ");
            String dataString = Console.scanString("Data de Publicação: ");
            try {
                lista.addLivro(new Livro(isbn, nome, autores, editora, DateUtil.stringToDate(dataString)));
                System.out.println("Livro " + nome + " cadastrado com sucesso!");
            
            } catch (DateTimeParseException ex) {
                System.out.println("Formato de Data inválido!");
            }
        }
    }
    
    public void listar() {
        if(lista.getListaLivros().size() <= 0){
            System.out.println("-----------------------------");        
            System.out.println("Nao ha livros cadastrados");
            System.out.println("-----------------------------\n");
        }
        else{
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ISBN") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|AUTORES") + "\t"
                    + String.format("%-20s", "|EDITORA") + "\t"
                    + String.format("%-20s", "|DATA DE PUBLICAÇÃO"));
            for (Livro livro : lista.getListaLivros()) {
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
