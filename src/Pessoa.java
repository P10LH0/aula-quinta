import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafael.oliveira
 */
class Pessoa implements Serializable{

    private String nome;
    private int idade;
    private int alturaCm;
    private boolean cliente;

    public Pessoa(String nome, int idade, int alturaCm, boolean cliente) {
        this.nome = nome;
        this.idade = idade;
        this.alturaCm = alturaCm;
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(int alturaCm) {
        this.alturaCm = alturaCm;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "nome=" + nome + "\nidade=" + idade + "\naltura em Cm=" + alturaCm + "\ncliente=" + cliente;
    }
}
