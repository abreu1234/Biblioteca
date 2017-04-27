package view.menu;

public class MainMenu {

    public static final int OP_LIVROS = 1;
    public static final int OP_CLIENTES = 2;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Menu Livros\n"
                + "2- Menu Clientes\n"
                + "0- Sair da Aplicação"
                + "\n--------------------------------------");
    }
}