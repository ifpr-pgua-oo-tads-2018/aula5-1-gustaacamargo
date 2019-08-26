package pizzapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class JDBCPizzaDAO implements PizzaDAO {

    private static String TABELA="pizzas";
    private static String CAMPO_SABOR="sabor";
    private static String CAMPO_VALOR="valor";
    private static String CAMPO_ID="id";

    private static String INSERT="INSERT INTO "+TABELA+"("+CAMPO_SABOR+","+CAMPO_VALOR+") VALUES (?,?)";
    private static String UPDATE="UPDATE "+TABELA+" SET "+CAMPO_SABOR+"=?,"+CAMPO_VALOR+"=? WHERE"+CAMPO_ID+"=?";
    private static String DELETE="DELETE FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";
    private static String SELECT="SELECT * FROM "+TABELA;
    private static String SELECT_ID="SELECT * FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";


    private QueryRunner dbAccess = new QueryRunner();

    @Override
    public void insere(Pizza p) throws SQLException {

    	 Connection con = FabricaConexao.getConnection();
      	 PreparedStatement stm = con.prepareStatement(INSERT);
      	 stm.setString(1, p.getSabor());
      	 stm.setDouble(2, p.getValor());
      	 
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
    public boolean atualiza(Pizza p) throws SQLException {
    	Connection con = FabricaConexao.getConnection();
        PreparedStatement stm = con.prepareStatement(UPDATE);
    	stm.setString(1, p.getSabor());
    	stm.setDouble(2, p.getValor());
    	stm.setInt(3, p.getId());
    	
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
    public boolean deleta(Pizza p) throws SQLException {
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
    public Pizza buscaId(int id) throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        Pizza p =dbAccess.query(connection,SELECT_ID,
                new BeanHandler<Pizza>(Pizza.class),id);

        connection.close();
        return p;

    }

    @Override
    public List<Pizza> buscaAtributo(PizzaAtributoBusca atributo, Object valor) throws SQLException {
        String where = "";
        String valorWhere = "";

        switch (atributo){
            case SABOR:
                where = " where "+CAMPO_SABOR+" like ?";
                valorWhere = "%"+valor.toString()+"%";
                break;
        }

        Connection connection = FabricaConexao.getConnection();
        List<Pizza> lista = dbAccess.query(connection,SELECT+where,
                new BeanListHandler<Pizza>(Pizza.class),valorWhere);

        connection.close();

        return lista;


    }

    @Override
    public List<Pizza> buscaTodos() throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        List<Pizza> lista = dbAccess.query(connection,SELECT,
                new BeanListHandler<Pizza>(Pizza.class));

        connection.close();

        return lista;
    }
}
