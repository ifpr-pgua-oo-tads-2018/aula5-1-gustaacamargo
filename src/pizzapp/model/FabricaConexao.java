package pizzapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private enum Sgbds{
        sqlite,mysql
    }


    private static int MAX_CONNECTIONS=5;

    private static Sgbds SGBD=Sgbds.mysql;
    private static String IP_SERVIDOR="localhost";
    private static String NOME_BANCO="pizzapp";
    private static String USUARIO="user";
    private static String SENHA="user12345";

    private static String CON_STRING="jdbc:"+SGBD+":";

    private static Connection[] pool;

    private static FabricaConexao instance = new FabricaConexao();

    private FabricaConexao(){

        if(SGBD == Sgbds.mysql){
            CON_STRING="jdbc:"+SGBD.name()+"://"+IP_SERVIDOR+"/"+NOME_BANCO+"?useTimezone=true&serverTimezone=UTC";
        }else{
            CON_STRING="jdbc:"+SGBD.name()+":"+NOME_BANCO;
        }

        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{
        for(int i=0;i<MAX_CONNECTIONS;i++){
            if(instance.pool[i]==null || instance.pool[i].isClosed()){
                instance.pool[i] = DriverManager.getConnection(CON_STRING,USUARIO,SENHA);
                return instance.pool[i];
            }
        }

        throw new SQLException("Máximo de conexões");
    }


}
