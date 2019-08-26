package pizzapp.model;

import java.sql.SQLException;
import java.util.List;

public interface PizzaDAO extends DAO<Pizza>{

    List<Pizza> buscaAtributo(PizzaAtributoBusca atributo, Object valor) throws SQLException;

}
