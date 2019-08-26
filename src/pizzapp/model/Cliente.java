package pizzapp.model;

public class Cliente {

    private int id;
    private String nome;
    private String telefone;
    private int anoNascimento;


    public Cliente(){

    }

    public Cliente( String nome, String telefone, int anoNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.anoNascimento = anoNascimento;
    }

    public Cliente(int id, String nome, String telefone, int anoNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.anoNascimento = anoNascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String toString(){
        return this.nome;
    }

}
