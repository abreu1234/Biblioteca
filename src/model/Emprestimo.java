package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {
    
    private int id;
    private long diasAtraso;
    private Cliente cliente;
    private LocalDate dataEntrega;
    private List<Livro> livros;
    private Boolean entregue;
    
    public Emprestimo(Cliente cliente, LocalDate dataEntrega) {
        this.cliente = cliente;
        this.dataEntrega = dataEntrega;
        this.livros = new ArrayList<>();
        this.entregue = false;
        this.diasAtraso = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDiasAtraso(long diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public void setEntregue(Boolean entregue) {
        this.entregue = entregue;
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
    
    public long getDiasAtraso() {
        return diasAtraso;
    }
    
    public void entregar() {
        for (Livro livro : livros) {
            livro.setDisponivel(true);
        }
        LocalDate dataHoje = LocalDate.now();
        if(dataEntrega.isAfter(dataHoje)) {
            long diasDiferenca = dataHoje.until(dataEntrega, ChronoUnit.DAYS);
            this.diasAtraso = diasDiferenca;
            this.cliente.setDiasAtraso(diasAtraso);
        }
        this.cliente.setLivrosRetirados(0);
        this.entregue = true;
    }
            
    public void adicionarLivro(Livro l) {
        this.cliente.livroRetirado();
        livros.add(l);
    }
    
}
