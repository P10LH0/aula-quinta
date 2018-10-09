/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafael.oliveira
 */
public class No {
    
    private Pessoa dado;
    private No prox;
    private No ant;

    public No(Pessoa dado) {
        this.dado = dado;
        this.prox = null;
        this.ant = null;
    }

    public Pessoa getDado() {
        return dado;
    }

    public void setDado(Pessoa dado) {
        this.dado = dado;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }
    
    
    
    
    
}

