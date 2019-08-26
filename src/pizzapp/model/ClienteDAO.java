package pizzapp.model;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO extends DAO<Cliente> {

    List<Cliente> buscaAtributo(ClienteAtributoBusca atributo, Object valor) throws SQLException;


}
