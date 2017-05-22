package dao.impl_DB;

import dao.ClienteDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Cliente;

public class ClienteDaoDb implements ClienteDao {
    
    private Connection conexao;
    private PreparedStatement comando;

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

    @Override
    public void salvar(Cliente cliente) {
        int id = 0;
        try {
            String sql = "INSERT INTO cliente ( matricula, nome, telefone) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, cliente.getMatricula());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                cliente.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente procurarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
