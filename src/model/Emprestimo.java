package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {
    
    private static int AUTO_INCREMENT = 1;
    private int id;
    private Cliente cliente;
    private LocalDate dataEntrega;
    private List<Livro> livros;
    private Boolean entregue;
    
    public Emprestimo(Cliente cliente, LocalDate dataEntrega) {
        this.id = autoIncrement();   
        this.cliente = cliente;
        this.dataEntrega = dataEntrega;
        this.livros = new ArrayList<>();
        this.entregue = false;
    }

    public static int getAUTO_INCREMENT() {
        return AUTO_INCREMENT;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public Boolean getEntregue() {
        return entregue;
    }
    
    public void entregar() {
        for (Livro livro : livros) {
            livro.setDisponivel(true);
        }
        this.entregue = true;
    }
        
    private int autoIncrement() {
        return (AUTO_INCREMENT++);
    }
    
    public void adicionarLivro(Livro l) {
        livros.add(l);
    }
    
}
