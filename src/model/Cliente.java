package model;

import java.util.Objects;

public class Cliente {
    
    private int id;
    private String matricula, nome, telefone;
    private long diasAtraso;
    private int livrosRetirados, totalLivrosRetirados;
    
    public Cliente(String matricula, String nome, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
        this.livrosRetirados = 0;
        this.totalLivrosRetirados = 0;
        this.diasAtraso = 0;
    }
    
    public int getId() {
        return this.id;
    }
  
    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
  
    public int getLivrosRetirados() {
        return livrosRetirados;
    }
    
    public int getTotalLivrosRetirados() {
        return totalLivrosRetirados;
    }
    
    public long getDiasAstraso() {
        return diasAtraso;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setDiasAtraso(long diasAtraso) {
        this.diasAtraso += diasAtraso;
    }
    
    public void setLivrosRetirados(int livrosRetirados) {
        this.livrosRetirados = livrosRetirados;
    }
    
    public void livroRetirado() {
        this.livrosRetirados++;
        this.totalLivrosRetirados++;
    }
        
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        return true;
    }
    
}
