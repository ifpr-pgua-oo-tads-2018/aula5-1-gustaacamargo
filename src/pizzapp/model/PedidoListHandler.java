package pizzapp.model;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoListHandler implements ResultSetHandler<List<Pedido>> {

    private ClienteDAO clienteDAO;


    public PedidoListHandler(ClienteDAO clienteDAO){
        this.clienteDAO = clienteDAO;
    }


    @Override
    public List<Pedido> handle(ResultSet resultSet) throws SQLException {

        ArrayList<Pedido> lista = new ArrayList<>();

        if(resultSet.next()){

            Pedido pedido = new Pedido();


            int id = resultSet.getInt("id");
            int idCliente = resultSet.getInt("idCliente");
            String dataHora = resultSet.getString("dataHora");
            double valor = resultSet.getDouble("valor");

            pedido.setId(id);
            pedido.setValor(valor);
            pedido.setDataHora(LocalDateTime.parse(dataHora));

            this.ajustaCliente(pedido,idCliente);
            this.buscaPizzasPedido(pedido);
            lista.add(pedido);
        }

        return lista;
    }

    private void ajustaCliente(Pedido pedido, int idCliente) throws SQLException{

        Cliente c = this.clienteDAO.buscaId(idCliente);
        pedido.setCliente(c);

    }


    private void buscaPizzasPedido(Pedido p) throws SQLException{
        PizzaDAO pizzaDAO = new JDBCPizzaDAO();

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM pedidospizza WHERE idPedido=?");
        stm.setInt(1,p.getId());

        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            int idPizza = rs.getInt("idPizza");
            double valor = rs.getDouble("valor");

            Pizza pizza = ((JDBCPizzaDAO) pizzaDAO).buscaId(idPizza);
            pizza.setValor(valor);

            p.add(pizza);
        }

        rs.close();
        stm.close();
        con.close();
    }


}
