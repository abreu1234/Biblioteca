package view;

import dao.impl_DB.ClienteDaoDb;
import dao.impl_DB.EmprestimoDaoDb;
import dao.impl_DB.LivroDaoDb;
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
    private LivroDaoDb livroDaoDb;
    private EmprestimoDaoDb emprestimoDaoDb;
    private ClienteDaoDb clienteDaoDb;

    public LivroUI(RepositorioLivros lista, RepositorioClientes listaClientes, RepositorioEmprestimos listaEmprestimo) {
        this.lista = lista;
        this.listaEmprestimo = listaEmprestimo;
        this.listaClientes = listaClientes;
        this.livroDaoDb = new LivroDaoDb();
        this.emprestimoDaoDb = new EmprestimoDaoDb();
        this.clienteDaoDb = new ClienteDaoDb();
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
                case LivroMenu.OP_ATUALIZAR:
                    atualizar();
                    break;
                case LivroMenu.OP_REMOVER:
                    deletar();
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
                    livrosMaisRetirados();
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != LivroMenu.OP_VOLTAR);
    }
    
    private void deletar() {
        int id = Console.scanInt("ID do livro a ser deletado: ");
        Livro livro = livroDaoDb.procurarPorId(id);
        this.imprimir(livro);
        if (UIUtil.getConfirmacao("Realmente deseja excluir esse livro?")) {
            livroDaoDb.deletar(livro);
            System.out.println("Livro deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
    }
    
    private void emprestar() {
        String matricula = Console.scanString("Matrícula do cliente: ");
        Cliente cliente = clienteDaoDb.procurarPorMatricula(matricula);
        if( cliente != null && cliente.getLivrosRetirados() < 3 ) {
            try{
                String dataEntrega = Console.scanString("Data da entrega: ");
                Emprestimo emprestimo = new Emprestimo(cliente, DateUtil.stringToDate(dataEntrega));
                int outroLivro = 0;
                do{
                    String isbn = Console.scanString("ISBN do livro: ");
                    Livro livro = livroDaoDb.procurarPorIsbn(isbn);
                    if ( livro != null && livro.getDisponivel() ) {
                        emprestimo.adicionarLivro(livro);
                        livro.setDisponivel(false);
                        if( cliente.getLivrosRetirados() < 3) {
                            outroLivro = Console.scanInt("Deseja retirar outro livro?: ");
                        }
                    }else{
                        System.out.println("Livro indisponível ou não existe.");
                    } 
                }while(cliente.getLivrosRetirados() < 3 && outroLivro == 1);
                if(emprestimo.getLivros().size() > 0) {
                    emprestimoDaoDb.emprestar(emprestimo);
                    System.out.println("Empréstimo realizado com sucesso. ID: "+emprestimo.getId());
                }
            }catch (DateTimeParseException ex) {
                System.out.println("Data ou hora no formato inválido!");                
            }catch(Exception e) {
                System.out.println("ERRO: "+e.getMessage());                
            }
        }else{
            System.out.println("Cliente não disponível para retirada de livros.");
        }      
    }
    
    private void atualizar() {
        String isbn = Console.scanString("ISBN do livro: ");

        Livro livro;
        livro = livroDaoDb.procurarPorIsbn(isbn);
        if(livro == null) {
            System.out.println("Livro não encontrado");
        }else {
            this.imprimir(livro);

            System.out.println("Digite os dados do livro que quer alterar [Vazio caso nao queira]");
            String nome = Console.scanString("Nome: ");
            String autores = Console.scanString("Autores: ");
            String editores = Console.scanString("Editores: ");
            String dataPublicacao = Console.scanString("Data de publicação: ");
            if (!nome.isEmpty()) {
                livro.setNome(nome);
            }
            if (!autores.isEmpty()) {
                livro.setAutores(autores);
            }
            if (!editores.isEmpty()) {
                livro.setEditora(editores);
            }            
            if (!dataPublicacao.isEmpty()) {
                livro.setDataPublicacao(DateUtil.stringToDate(dataPublicacao));
            }            

            livroDaoDb.atualizar(livro);
            System.out.println("Livro " + livro.getNome() + " atualizado com sucesso!");
        }
    }
    
    private void devolver() {
        int id = Console.scanInt("ID do emprestimo: ");
        Emprestimo emprestimo = emprestimoDaoDb.procurarPorId(id);
        if( emprestimo != null ) {
            emprestimo.setEntregue(true);
            emprestimoDaoDb.devolver(emprestimo);
            System.out.println("Livros entregues com sucesso. Dias de atraso: "+emprestimo.getDiasAtraso());
        }else{
            System.out.println("Cliente não possui empréstimos pendentes.");
        }   
    }
    
    private void cadastrar() {
        try {
            String isbn = Console.scanString("ISBN: ");
            String nome = Console.scanString("Nome: ");
            String autores = Console.scanString("Autores: ");
            String editora = Console.scanString("Editora: ");
            String dataString = Console.scanString("Data de Publicação: ");

            livroDaoDb.salvar(new Livro(isbn, nome, autores, editora, DateUtil.stringToDate(dataString)));
            System.out.println("Livro " + nome + " cadastrado com sucesso!");
        } catch (DateTimeParseException ex) {
            System.out.println("Formato de Data inválido!");
        }
    }
    
    public void listar() {
        imprimir(livroDaoDb.listar());
    }    
    
    public void livrosDisponiveis() {
        imprimir(livroDaoDb.disponiveis());
    }
    
    public void livrosMaisRetirados() {
        imprimir(livroDaoDb.maisRetirados());
    }
    
    private void imprimir(Livro livro) {
        System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ISBN") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|AUTORES") + "\t"
                    + String.format("%-20s", "|EDITORA") + "\t"
                    + String.format("%-20s", "|DATA DE PUBLICAÇÃO")
                    + String.format("%-10s", "|VEZES EMPRESTADO")); 
        System.out.println(String.format("%-10s", livro.getIsbn()) + "\t"
                        + String.format("%-20s", "|" + livro.getNome()) + "\t"
                        + String.format("%-20s", "|" + livro.getAutores()) + "\t"
                        + String.format("%-20s", "|" + livro.getEditora()) + "\t"
                        + String.format("%-20s", "|" + DateUtil.dateToString(livro.getDataPublicacao()))
                        + String.format("%-10s", "|" + livro.getLivroRetirado())
        );
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
                        + String.format("%-20s", "|" + DateUtil.dateToString(livro.getDataPublicacao()))
                        + String.format("%-10s", "|" + livro.getLivroRetirado())
                );
            }
        }
    }
        
}
