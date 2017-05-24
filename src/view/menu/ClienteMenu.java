package view.menu;

public class ClienteMenu {
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_DELETAR = 4;
    public static final int OP_MAIS_RETIRARAM = 5;
    public static final int OP_ATRASADOS = 6;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar cliente\n"
                + "2- Listar clientes\n"
                + "3- Atualizar cliente\n"
                + "4- Deletar cliente\n"
                + "5- Listar clientes que retiraram livros\n"
                + "6- Listar clientes que Atrasaram\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }      
    
}
