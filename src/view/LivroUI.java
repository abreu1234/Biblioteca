package view;

import java.time.format.DateTimeParseException;
import java.util.List;
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
                case LivroMenu.OP_DISPONIVEIS:
                    livrosDisponiveis();
                    break;
                case LivroMenu.OP_MAIS_RETIRADOS:
                    livrosDisponiveis();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != LivroMenu.OP_VOLTAR);
    }
    
    private void emprestar() {
        String matricula = Console.scanString("Matrícula do cliente: ");
        Cliente cliente = listaClientes.buscarCliente(matricula);
        if( cliente != null && cliente.getLivrosRetirados() < 3 ) {
            try{
                String dataEntrega = Console.scanString("Data da entrega: ");
                Emprestimo emprestimo = new Emprestimo(cliente, DateUtil.stringToDate(dataEntrega));
                int outroLivro = 0;
                do{
                    String isbn = Console.scanString("ISBN do livro: ");
                    Livro livro = lista.buscarLivro(isbn);
                    if ( livro != null && livro.getDisponivel() ) {
                        emprestimo.adicionarLivro(livro);
                        livro.setDisponivel(false);
                        if(cliente.getLivrosRetirados() < 3) {
                            outroLivro = Console.scanInt("Deseja retirar outro livro?: ");
                        }
                    }else{
                        System.out.println("Livro indisponível ou não existe.");
                    } 
                }while(cliente.getLivrosRetirados() < 3 && outroLivro == 1);
                this.listaEmprestimo.addEmprestimo(emprestimo);
                System.out.println("Empréstimo realizado com sucesso. ID: "+emprestimo.getId());
            }catch (DateTimeParseException ex) {
                System.out.println("Data ou hora no formato inválido!");                
            }catch(Exception e) {
                System.out.println("ERRO: "+e.getMessage());                
            }
        }else{
            System.out.println("Cliente não disponível para retirada de livros.");
        }      
    }
    
    private void devolver() {
        String matricula = Console.scanString("Matrícula do cliente: ");
        Cliente cliente = listaClientes.buscarCliente(matricula);
        Emprestimo emprestimo = this.listaEmprestimo.buscar(cliente);
        if( emprestimo != null ) {
            emprestimo.entregar();
            System.out.println("Livros entregues com sucesso. Dias de atraso: "+emprestimo.getDiasAtraso());
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
        imprimir(lista.getListaLivros());
    }    
    
    public void livrosDisponiveis() {
        imprimir(lista.getListaLivrosDisponiveis());
    }
    
    public void livrosMaisRetirados() {
        imprimir(lista.getListaLivrosRetirados());
    }
    
    private void imprimir(List<Livro> livros) {
        if(livros.size() <= 0){
            System.out.println("-----------------------------");        
            System.out.println("Nao ha livros nesta lista");
            System.out.println("-----------------------------\n");
        }else{
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ISBN") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|AUTORES") + "\t"
                    + String.format("%-20s", "|EDITORA") + "\t"
                    + String.format("%-20s", "|DATA DE PUBLICAÇÃO")
                    + String.format("%-10s", "|VEZES EMPRESTADO"));
            for (Livro livro : livros) {
                System.out.println(String.format("%-10s", livro.getIsbn()) + "\t"
                        + String.format("%-20s", "|" + livro.getNome()) + "\t"
                        + String.format("%-20s", "|" + livro.getAutores()) + "\t"
                        + String.format("%-20s", "|" + livro.getEditora()) + "\t"
                        + String.format("%-20s", "|" + livro.getEditora()) + "\t"
                        + String.format("%-20s", "|" + DateUtil.dateToString(livro.getDataPublicacao()))
                        + String.format("%-10s", "|" + livro.getLivroRetirado())
                );
            }
        }
    }
        
}
