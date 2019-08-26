package pizzapp.model;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCClienteDAO implements ClienteDAO {

    private static String TABELA="clientes";
    private static String CAMPO_NOME="nome";
    private static String CAMPO_NASCIMENTO="ano_nascimento";
    private static String CAMPO_TELEFONE="telefone";
    private static String CAMPO_ID="id";

    private static String INSERT="INSERT INTO "+TABELA+"("+CAMPO_NOME+","+CAMPO_TELEFONE+","+CAMPO_NASCIMENTO+") VALUES (?,?,?)";
    private static String UPDATE="UPDATE "+TABELA+" SET "+CAMPO_NOME+"=?,"+CAMPO_TELEFONE+"=?,"+CAMPO_NASCIMENTO+"=? WHERE"+CAMPO_ID+"=?";
    private static String DELETE="DELETE FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";
    private static String SELECT="SELECT * FROM "+TABELA;
    private static String SELECT_ID="SELECT * FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";


    private QueryRunner dbAccess = new QueryRunner();


    public Map<String,String> getColumnsToFieldsMap(){
        Map<String, String> columnsToFieldsMap = new HashMap<>();
        columnsToFieldsMap.put("ano_nascimento", "anoNascimento");

        return columnsToFieldsMap;
    }

    @Override
    public void insere(Cliente cliente) throws SQLException {
        Connection con = FabricaConexao.getConnection();
        PreparedStatement stm = con.prepareStatement(INSERT);
    	stm.setString(1, cliente.getNome());
    	stm.setString(2, cliente.getTelefone());
    	stm.setInt(3, cliente.getAnoNascimento());
    	
        try {
        	stm.executeUpdate();
        }catch (SQLException e) {
        	if (con != null) {
                try {
                    System.err.print("Rollback efetuado na transação");
                    con.rollback();
                } catch(SQLException e2) {
                    System.err.print("Erro na transação!"+e2);
                }
            }
		}finally {
 			stm.close();
 			con.close();
		}

    }

    @Override
    public boolean atualiza(Cliente p) throws SQLException {
    	Connection con = FabricaConexao.getConnection();
    	//UPDATE "+TABELA+" SET "+CAMPO_NOME+"=?,"+CAMPO_TELEFONE+"=?,"+CAMPO_NASCIMENTO+"=? WHERE"+CAMPO_ID+"=?
        PreparedStatement stm = con.prepareStatement(UPDATE);
    	stm.setString(1, p.getNome());
    	stm.setString(2, p.getTelefone());
    	stm.setInt(3, p.getAnoNascimento());
    	stm.setInt(4, p.getId());

    	
        try {
        	stm.executeUpdate();
        }catch (SQLException e) {
        	if (con != null) {
                try {
                    System.err.print("Rollback efetuado na transação");
                    con.rollback();
                } catch(SQLException e2) {
                    System.err.print("Erro na transação!"+e2);
                }
            }
		}finally {
 			stm.close();
 			con.close();
		}
    	
        return true;
    }

    @Override
    public boolean deleta(Cliente p) throws SQLException {
    	Connection con = FabricaConexao.getConnection();
        PreparedStatement stm = con.prepareStatement(DELETE);
    	stm.setInt(1, p.getId());
    	
        try {
        	stm.executeUpdate();
        }catch (SQLException e) {
        	if (con != null) {
                try {
                    System.err.print("Rollback efetuado na transação");
                    con.rollback();
                } catch(SQLException e2) {
                    System.err.print("Erro na transação!"+e2);
                }
            }
		}finally {
 			stm.close();
 			con.close();
		}
       
        return true;
    }

    @Override
    public List<Cliente> buscaAtributo(ClienteAtributoBusca atributo, Object valor) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        BeanListHandler<Cliente> handler = new BeanListHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        String where = "";
        String wValor="";

        switch (atributo){
            case NOME:
                where = CAMPO_NOME+" like ?";
                wValor = "%"+valor.toString()+"%";
                break;
            case TELEFONE:
                where = CAMPO_TELEFONE+" like ?";
                wValor = valor.toString();
                break;
            case ANO_NASCIMENTO:
                where = CAMPO_NASCIMENTO+" = ?";
                wValor = valor.toString();
                break;
        }

        List<Cliente> list = dbAccess.query(con,SELECT+" WHERE "+where,handler,wValor);

        con.close();

        return list;

    }

    @Override
    public Cliente buscaId(int id) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanHandler<Cliente> handler = new BeanHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        Cliente c = dbAccess.query(con,SELECT_ID,handler,id);

        con.close();

        return c;
    }

    @Override
    public List<Cliente> buscaTodos() throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanListHandler<Cliente> handler = new BeanListHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        List<Cliente> list = dbAccess.query(con,SELECT,handler);

        con.close();

        return list;
    }
}
