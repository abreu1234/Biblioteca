package dao.impl_DB;

import dao.LivroDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Livro;

public class LivroDaoDb implements LivroDao{
        
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
    public void salvar(Livro livro) {
        int id = 0;
        try {
            String sql = "INSERT INTO livro ( isbn, nome, autores, editora, dataPublicacao) "
                    + "VALUES (?,?,?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, livro.getIsbn());
            comando.setString(2, livro.getNome());
            comando.setString(3, livro.getAutores());
            comando.setString(4, livro.getEditora());
            Date dataSql = Date.valueOf(livro.getDataPublicacao());
            comando.setDate(5, dataSql);
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                livro.setId(id);
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

    @Override
    public void deletar(Livro livro) {
        try {
            String sql = "DELETE FROM livro WHERE id = ?";

            conectar(sql);
            comando.setInt(1, livro.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar livro no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Livro livro) {
        try {
            String sql = "UPDATE livro SET nome=?, autores=?, editora=?, dataPublicacao=? "
                    + "WHERE id=?";

            conectar(sql);
            
            comando.setString(1, livro.getNome());
            comando.setString(2, livro.getAutores());
            comando.setString(3, livro.getEditora());
            Date dataSql = Date.valueOf(livro.getDataPublicacao());
            comando.setDate(4, dataSql);
            comando.setInt(5, livro.getId());
                        
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Livro> listar() {
        List<Livro> lista = new ArrayList<>();

        String sql = "SELECT *, entregue disponivel FROM livro l" +
                " INNER JOIN emprestimo_livro el ON (el.livro_id = l.id)" +
                " INNER JOIN emprestimo e ON (el.emprestimo_id = e.id)";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String isbn = resultado.getString("isbn");
                String nome = resultado.getString("nome");
                String autores = resultado.getString("autores");
                String editora = resultado.getString("editora");
//                int qtdRetirado = resultado.getInt("qtdRetirado");
                boolean disponivel = resultado.getBoolean("disponivel");
                LocalDate dataPublicacao =  resultado.getDate("dataPublicacao").toLocalDate();

                Livro livro = new Livro(id, isbn, nome, autores, editora, dataPublicacao);
                livro.setDisponivel(disponivel);
//                livro.setQtdRetirado(qtdRetirado);
                
                lista.add(livro);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os livros do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (lista);
    }

    @Override
    public Livro procurarPorId(int id) {
        String sql = "SELECT *, entregue disponivel FROM livro l" +
                " INNER JOIN emprestimo_livro el ON (el.livro_id = l.id)" +
                " INNER JOIN emprestimo e ON (el.emprestimo_id = e.id) WHERE l.id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String isbn = resultado.getString("isbn");
                String nome = resultado.getString("nome");
                String autores = resultado.getString("autores");
                String editora = resultado.getString("editora");
//                int qtdRetirado = resultado.getInt("qtdRetirado");
                boolean disponivel = resultado.getBoolean("entregue");
                LocalDate dataPublicacao =  resultado.getDate("dataPublicacao").toLocalDate();

                Livro livro = new Livro(id, isbn, nome, autores, editora, dataPublicacao);
                livro.setDisponivel(disponivel);
//                livro.setQtdRetirado(qtdRetirado);
                
                return livro;
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
        
    public Livro procurarPorIsbn(String isbn) {
        String sql = "SELECT * FROM livro l" +
                " INNER JOIN emprestimo_livro el ON (el.livro_id = l.id)" +
                " INNER JOIN emprestimo e ON (el.emprestimo_id = e.id) WHERE l.isbn = ?";

        try {
            conectar(sql);
            comando.setString(1, isbn);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String autores = resultado.getString("autores");
                String editora = resultado.getString("editora");
//                int qtdRetirado = resultado.getInt("qtdRetirado");
                boolean disponivel = resultado.getBoolean("entregue");
                LocalDate dataPublicacao =  resultado.getDate("dataPublicacao").toLocalDate();

                Livro livro = new Livro(id, isbn, nome, autores, editora, dataPublicacao);
                livro.setDisponivel(disponivel);
//                livro.setQtdRetirado(qtdRetirado);
                
                return livro;
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
        
}
