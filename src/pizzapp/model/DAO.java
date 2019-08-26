package pizzapp.model;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    void insere(T t) throws SQLException;
    boolean atualiza(T p) throws SQLException;
    boolean deleta(T p) throws SQLException;

    T buscaId(int id) throws SQLException;
    List<T> buscaTodos() throws SQLException;

}
