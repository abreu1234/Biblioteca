package dao.impl_DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import model.Cliente;
import model.Emprestimo;
import model.Livro;

public class EmprestimoDaoDb {
    
    private Connection conexao;
    private PreparedStatement comando;
    
    private LivroDaoDb livroDaoDb;
    private ClienteDaoDb ClienteDaoDb;
    
    public EmprestimoDaoDb() {
        livroDaoDb = new LivroDaoDb();
        ClienteDaoDb = new ClienteDaoDb();
    }

    public Connection conectar(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
        return conexao;
    }
    
    public void conectarObtendoId(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public void fecharConexao() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Erro ao encerrar a conexao");
            throw new BDException(ex);

        }

    }  
    
    private void saveLivros(Emprestimo emprestimo) {
        int id = 0;
        try {
            String sql = "INSERT INTO emprestimo_livro ( emprestimo_id, livro_id) "
                    + "VALUES (?,?)";

           
            List<Livro> livros = emprestimo.getLivros();
            for (Livro livro : livros) {
                 conectarObtendoId(sql);
                comando.setInt(1, emprestimo.getId());
                comando.setInt(2, livro.getId());
                comando.executeUpdate();
            }            

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar livro no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
        
    public void emprestar(Emprestimo emprestimo) {
        int id = 0;
        try {
            String sql = "INSERT INTO emprestimo ( cliente_id, dataEntrega) "
                    + "VALUES (?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setInt(1, emprestimo.getCliente().getId());
            Date dataSql = Date.valueOf(emprestimo.getDataEntrega());
            comando.setDate(2, dataSql);
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                emprestimo.setId(id);
                this.saveLivros(emprestimo);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar livro no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    public Emprestimo procurarPorId(int id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                boolean entregue = resultado.getBoolean("entregue");
                int diasAtraso = resultado.getInt("diasAtraso");
                int cliente_id = resultado.getInt("cliente_id");
                LocalDate dataEntrega =  resultado.getDate("dataEntrega").toLocalDate();
                
                ClienteDaoDb clienteDaoDb = new ClienteDaoDb();
                Cliente cliente = clienteDaoDb.procurarPorId(cliente_id);
                                
                Emprestimo emprestimo = new Emprestimo(id, cliente, dataEntrega);
                emprestimo.setEntregue(entregue);
                emprestimo.setDiasAtraso(diasAtraso);
                
                return emprestimo;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o cliente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    
    public void devolver(Emprestimo emprestimo) {
        try {
            String sql = "UPDATE emprestimo SET entregue=?, diasAtraso=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setBoolean(1, emprestimo.getEntregue());
            comando.setInt(3, emprestimo.getId());
            LocalDate dataHoje = LocalDate.now();            
            long diasDiferenca = dataHoje.until(emprestimo.getDataEntrega(), ChronoUnit.DAYS);
            emprestimo.setDiasAtraso(diasDiferenca);
            comando.setLong(2, diasDiferenca);
            
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
}
