package pizzapp.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pizzapp.model.Cliente;
import pizzapp.model.Pizzaria;

import java.sql.SQLException;


public class DetalhesCliente {

    @FXML
    private TableView<Cliente> tbClientes;

    @FXML
    private TableColumn<Cliente,String> tcClienteNome;

    @FXML
    private TableColumn<Cliente,String> tcClienteTelefone;

    @FXML
    private TableColumn<Cliente,Integer> tcClienteAnoNascimento;


    public void initialize(){

        tcClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcClienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcClienteAnoNascimento.setCellValueFactory(new PropertyValueFactory<>("anoNascimento"));

        tbClientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        try{
            tbClientes.setItems(Pizzaria.getInstance().listaClientes());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Problema ao buscar clientes \n"+e.getMessage());
            alert.showAndWait();
        }




    }


}
