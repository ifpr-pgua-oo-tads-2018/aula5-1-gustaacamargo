package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private ObservableList<Pizza> pedido;
    private double valor;

    public Pedido() {
        pedido = FXCollections.observableArrayList();
    }

    public void add(Pizza p){
        pedido.add(p);
    }

    public double getValor(){
        return  valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dateTime) {
        this.dataHora = dateTime;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ObservableList<Pizza> getPedido(){
        return pedido;
    }
}
