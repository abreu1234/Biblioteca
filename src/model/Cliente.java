package model;

import java.util.Objects;

public class Cliente {
    
    private String matricula, nome, telefone;
    private int livrosRetirados;
    
    public Cliente(String matricula, String nome, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
        this.livrosRetirados = 0;
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
    
    public void setLivrosRetirados(int livrosRetirados) {
        this.livrosRetirados = livrosRetirados;
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
