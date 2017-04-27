package view.menu;

public class RelatorioMenu {
    
    public static final int OP_DISPONIVEIS = 1;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Livros disponíveis\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }      
    
}
