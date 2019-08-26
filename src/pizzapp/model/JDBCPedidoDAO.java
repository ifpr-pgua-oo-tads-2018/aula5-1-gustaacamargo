package pizzapp.model;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCPedidoDAO implements PedidoDAO{

    private QueryRunner dbAccess = new QueryRunner();

    @Override
    public void insere(Pedido pedido) throws SQLException {

    	Connection con = FabricaConexao.getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO Pedidos(idCliente,dataHora,valor) VALUES (?,?,? )");
     	stm.setInt(1, pedido.getCliente().getId());
     	stm.setObject(2, pedido.getDataHora());
     	stm.setDouble(3, pedido.getValor());
     	
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
    public boolean atualiza(Pedido p) throws SQLException {
        return false;
    }

    @Override
    public boolean deleta(Pedido p) throws SQLException {
        return false;
    }




    @Override
    public Pedido buscaId(int id) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        Pedido p = dbAccess.query(con,"SELECT * FROM Pedidos WHERE id=?",new PedidoHandler(new JDBCClienteDAO()),id);

        con.close();

        return p;
    }

    @Override
    public List<Pedido> buscaTodos() throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        List<Pedido> lista = dbAccess.query(connection,"SELECT * FROM Pedidos",new PedidoListHandler(new JDBCClienteDAO()));

        return lista;
    }
}
