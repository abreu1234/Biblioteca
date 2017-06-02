package dao.impl_DB;

import dao.ClienteDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            System.err.println("Erro de Sistema - Problema ao salvar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Cliente cliente) {
        try {
            String sql = "DELETE FROM cliente WHERE id = ?";

            conectar(sql);
            comando.setInt(1, cliente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            String sql = "UPDATE cliente SET nome=?, telefone=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getTelefone());
            comando.setInt(3, cliente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> listaClientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String matricula = resultado.getString("matricula");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, matricula, nome, telefone);

                int livrosRetirados = resultado.getInt("livrosRetirados");
                int totalLivrosRetirados = resultado.getInt("totalLivrosRetirados");
                long diasAtraso = resultado.getInt("diasAtraso");
                cliente.setLivrosRetirados(livrosRetirados);
                cliente.setTotalLivrosRetirados(totalLivrosRetirados);
                cliente.setDiasAtraso(diasAtraso);
                
                listaClientes.add(cliente);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }

    @Override
    public Cliente procurarPorId(int id) {
        String sql = "SELECT *, count(*) livrosRetirados FROM cliente c" +
                " INNER JOIN emprestimo e ON (e.cliente_id = c.id)" +
                " INNER JOIN emprestimo_livro el ON (el.emprestimo_id = e.id)" +
                " WHERE e.entregue = false AND c.id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String matricula = resultado.getString("matricula");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, matricula, nome, telefone);
                
                int livrosRetirados = resultado.getInt("livrosRetirados");
                int totalLivrosRetirados = resultado.getInt("totalLivrosRetirados");
                long diasAtraso = resultado.getInt("diasAtraso");
                cliente.setLivrosRetirados(livrosRetirados);
                cliente.setTotalLivrosRetirados(totalLivrosRetirados);
                cliente.setDiasAtraso(diasAtraso);
                
                return cliente;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o cliente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
        
    public Cliente procurarPorMatricula(String matricula) {
        String sql = "SELECT *, count(*) qtdRetirados FROM cliente c" +
                " INNER JOIN emprestimo e ON (e.cliente_id = c.id)" +
                " INNER JOIN emprestimo_livro el ON (el.emprestimo_id = e.id)" +
                " WHERE e.entregue = false AND c.matricula = ?";

        try {
            conectar(sql);
            comando.setString(1, matricula);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, matricula, nome, telefone);
                
                int livrosRetirados = resultado.getInt("qtdRetirados");
                int totalLivrosRetirados = resultado.getInt("totalLivrosRetirados");
                long diasAtraso = resultado.getInt("diasAtraso");
                cliente.setLivrosRetirados(livrosRetirados);
                cliente.setTotalLivrosRetirados(totalLivrosRetirados);
                cliente.setDiasAtraso(diasAtraso);
                
                return cliente;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o cliente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    
    public List<Cliente> listarClientesRetiraram() {
        List<Cliente> listaClientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente WHERE livrosRetirados > 0 ORDER BY livrosRetirados DESC";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String matricula = resultado.getString("matricula");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, matricula, nome, telefone);

                int livrosRetirados = resultado.getInt("livrosRetirados");
                int totalLivrosRetirados = resultado.getInt("totalLivrosRetirados");
                long diasAtraso = resultado.getInt("diasAtraso");
                cliente.setLivrosRetirados(livrosRetirados);
                cliente.setTotalLivrosRetirados(totalLivrosRetirados);
                cliente.setDiasAtraso(diasAtraso);
                
                listaClientes.add(cliente);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }
    
    public List<Cliente> listarClientesAtrasados() {
        List<Cliente> listaClientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente WHERE diasAtraso > 0 ORDER BY diasAtraso DESC";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String matricula = resultado.getString("matricula");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, matricula, nome, telefone);

                int livrosRetirados = resultado.getInt("livrosRetirados");
                int totalLivrosRetirados = resultado.getInt("totalLivrosRetirados");
                long diasAtraso = resultado.getInt("diasAtraso");
                cliente.setLivrosRetirados(livrosRetirados);
                cliente.setTotalLivrosRetirados(totalLivrosRetirados);
                cliente.setDiasAtraso(diasAtraso);
                
                listaClientes.add(cliente);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }
    
}
