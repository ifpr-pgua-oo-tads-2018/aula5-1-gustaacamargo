package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pizzaria {

    private static String ARQ = "pizzas.bin";

    private ObservableList<Pizza> cadastro;
    private ObservableList<Cliente> clientes;

    private Pedido pedidoAtual = null;

    private PizzaDAO pizzaDAO = new JDBCPizzaDAO();
    private ClienteDAO clienteDAO = new JDBCClienteDAO();
    private PedidoDAO pedidoDAO = new JDBCPedidoDAO();

    private static Pizzaria instance = new Pizzaria();


    private Pizzaria(){
        cadastro = FXCollections.observableArrayList();
        clientes = FXCollections.observableArrayList();
    }

    public static Pizzaria getInstance(){
        return instance;
    }

    public void cadastraPizza(String nome, double valor) throws SQLException{
        Pizza p = new Pizza();
        p.setSabor(nome);
        p.setValor(valor);

        pizzaDAO.insere(p);

        cadastro.add(p);
    }

    public void cadastraCliente(String nome, String telefone, int anoNascimento) throws SQLException{
        Cliente c = new Cliente(nome,telefone,anoNascimento);

        clienteDAO.insere(c);


        clientes.add(c);
    }



    public void abrePedido(){
        if(pedidoAtual == null){
            pedidoAtual = new Pedido();
        }

    }

    public double fechaPedido(){
        if(pedidoAtual != null){
            double total = pedidoAtual.getValor();
            pedidoAtual = null;

            return total;
        }
        return -1;
    }

    public void adicionaPizza(Pizza p){
        if(pedidoAtual != null){
            pedidoAtual.add(p);
        }
    }

    public ObservableList listaPedido(){
        return pedidoAtual.getPedido();
    }

    public double valorPedido(){
        return pedidoAtual.getValor();
    }

    public ObservableList listaCadastro() throws SQLException{

        cadastro.clear();

        cadastro.addAll(pizzaDAO.buscaTodos());

        return cadastro;
    }

    public ObservableList buscaPizza(String texto) throws SQLException{
        cadastro.clear();

        List<Pizza> lista = pizzaDAO.buscaAtributo(PizzaAtributoBusca.SABOR,texto);

        cadastro.addAll(lista);

        return cadastro;
    }

    public ObservableList buscaCliente(ClienteAtributoBusca atributoBusca, Object valor) throws SQLException{
        clientes.clear();

        clientes.addAll(clienteDAO.buscaAtributo(atributoBusca,valor));

        return clientes;
    }



    public ObservableList listaClientes() throws SQLException{
        clientes.clear();

        clientes.addAll(clienteDAO.buscaTodos());

        return clientes;
    }


    public void salva() throws IOException {

        File f = new File(ARQ);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(new ArrayList<Pizza>(cadastro));

        oos.close();
        fos.close();

    }

    public void carrega() throws IOException,ClassNotFoundException{

        File f = new File(ARQ);
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);


        ArrayList pizzas = (ArrayList) ois.readObject();

        cadastro.addAll(pizzas);


        ois.close();
        fis.close();

    }





}
