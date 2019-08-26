package pizzapp.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import pizzapp.model.Cliente;
import pizzapp.model.ClienteAtributoBusca;
import pizzapp.model.Pizza;
import pizzapp.model.Pizzaria;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Principal {


    @FXML
    private TextField tfSabor;

    @FXML
    private TextField tfValor;


    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfTelefone;

    @FXML TextField tfAnoNascimento;


    @FXML
    private ListView<Cliente> ltvClientes;

    @FXML
    private ListView<Pizza> ltvPizzas;

    @FXML
    private ListView<Pizza> ltvPedido;

    @FXML
    private Text txtValorTotal;

    @FXML
    private Button btAdiciona;

    @FXML
    private Button btFecha;


    public void initialize(){

        try{
            ltvPizzas.setItems(Pizzaria.getInstance().listaCadastro());
            ltvClientes.setItems(Pizzaria.getInstance().listaClientes());
        }catch (SQLException e){
            e.printStackTrace();
        }



        btAdiciona.setDisable(true);
        btFecha.setDisable(true);
        ltvPedido.setDisable(true);


    }

    @FXML
    public void cadastraPizza(){
        String sabor = tfSabor.getText();
        double valor = Double.valueOf(tfValor.getText());

        try{
            Pizzaria.getInstance().cadastraPizza(sabor,valor);
        }catch (SQLException e){
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Problema ao inserir a pizza \n"+e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    public void cadastraCliente(){

        String nome = tfNome.getText();
        String telefone = tfTelefone.getText();
        int anoNascimento = Integer.valueOf(tfAnoNascimento.getText());

        try{
            Pizzaria.getInstance().cadastraCliente(nome,telefone,anoNascimento);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Problema ao inserir o cliente \n"+e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void buscaPizzas(KeyEvent evt){

        if(evt.getCode() == KeyCode.Z && evt.isControlDown()){
            try{
                Pizzaria.getInstance().listaCadastro();
            }catch (SQLException e){
                e.printStackTrace();
            }

            ((TextField)evt.getSource()).setText("");
        }else{
            String texto = ((TextField)evt.getSource()).getText() + evt.getText();

            if(texto.length() >= 3){
                try{
                    Pizzaria.getInstance().buscaPizza(texto);
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        }
    }

    @FXML
    public void buscaClientes(KeyEvent evt){
        try{
            if(evt.getCode() == KeyCode.Z && evt.isControlDown()){
                Pizzaria.getInstance().listaClientes();
                ((TextField)evt.getSource()).setText("");
            }else{
                String texto = ((TextField)evt.getSource()).getText() + evt.getText();

                if(texto.length() >= 3){
                    Pizzaria.getInstance().buscaCliente(ClienteAtributoBusca.NOME,texto);
                }
            }
        }catch (SQLException e){

        }

    }


    @FXML
    public void abrePedido(){
        Pizzaria.getInstance().abrePedido();

        ltvPedido.setItems(Pizzaria.getInstance().listaPedido());
        btAdiciona.setDisable(false);
        ltvPedido.setDisable(false);
        btFecha.setDisable(false);
    }

    @FXML
    public void fechaPedido(){
        ltvPedido.setItems(null);

        double total = Pizzaria.getInstance().fechaPedido();

        btAdiciona.setDisable(true);
        ltvPedido.setDisable(true);
        btFecha.setDisable(true);

        txtValorTotal.setText("Valor Total: R$ 0,00");
    }

    @FXML
    public void adicionaPizzaPedido(){
        Pizza p = ltvPizzas.getSelectionModel().getSelectedItem();
        if(p != null){
            Pizzaria.getInstance().adicionaPizza(p);
        }

        txtValorTotal.setText("Valor Total: R$"+ Pizzaria.getInstance().valorPedido());
    }

    @FXML
    public void mostraClientes(){

        Dialog<ButtonType> dialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/detalhesClientes.fxml"));
        try{
            Node conteudo = loader.load();

            dialog.getDialogPane().setContent(conteudo);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            dialog.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }


    }

}
