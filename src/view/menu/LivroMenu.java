package view.menu;

public class LivroMenu {
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_REMOVER = 4;
    public static final int OP_EMPRESTIMO = 5;
    public static final int OP_DEVOLUCAO = 6;
    public static final int OP_DISPONIVEIS = 7;
    public static final int OP_MAIS_RETIRADOS = 8;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar livro\n"
                + "2- Listar livros\n"
                + "3- Atualizar livro\n"
                + "4- Remover Livro\n"
                + "5- Emprestar livros\n"
                + "6- Devolver livros\n"
                + "7- Livros disponiveis\n"
                + "8- Livros mais retirados\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }      
    
}
