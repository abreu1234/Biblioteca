package view.menu;

public class LivroMenu {
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_EMPRESTIMO = 3;
    public static final int OP_DEVOLUCAO = 4;
    public static final int OP_DISPONIVEIS = 5;
    public static final int OP_MAIS_RETIRADOS = 6;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar livro\n"
                + "2- Listar livros\n"
                + "3- Emprestar livros\n"
                + "4- Devolver livros\n"
                + "5- Livros disponiveis\n"
                + "6- Livros mais retirados\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }      
    
}
