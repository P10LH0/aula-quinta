/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafael.oliveira
 */
public class Lista {
    
    private No primeiro;
    private int qtde;

    public Lista() {
        this.primeiro = null;
        this.qtde = 0;
    }
    
    public int size(){
        return this.qtde;
    }
    
    public boolean isEmpty(){
        return (this.primeiro == null);
    }
    
    public boolean add(int pos, Pessoa dado){
        No novo;
        No aux;
        
        if (pos > this.qtde || pos < 0)
           return false;
          
        //Se a lista esta vazia e o cara quer colocar o 
        //elemento numa posicao diferente que a primeira
        if (isEmpty() && pos != 0)
          return false;
        
        novo = new No(dado);
        
        if (isEmpty()){
            this.primeiro = novo;
            novo.setProx(novo);
            novo.setAnt(novo);
        }else if (pos == 0){ //vai inserir no inicio
             novo.setProx(this.primeiro);
             novo.setAnt(this.primeiro.getAnt());
             this.primeiro.getAnt().setProx(novo);
             this.primeiro.setAnt(novo);
             this.primeiro = novo;
        }else{//vai inserir no meio
            aux = this.primeiro;
            for (int i = 0; i < pos; i++){
                aux = aux.getProx();
            }
            
            novo.setProx(aux);
            novo.setAnt(aux.getAnt());
            novo.getProx().setAnt(novo);
            novo.getAnt().setProx(novo);
        }
        
        this.qtde++;
        return true;
    }
    public boolean add(Pessoa dado){
        return add(this.qtde,dado);
    }
    
     public Pessoa get(int pos){
        No aux;
        
        if (isEmpty())
            return null;
        
        if (pos >= this.qtde || pos < 0)
            return null;
        
        aux = this.primeiro;
        for(int i = 0; i < pos; i++){
            aux = aux.getProx();
        }
        
        return aux.getDado();
    }
    
    public boolean set(int pos, Pessoa valor){
        No aux;
        
        if (isEmpty())
            return false;
        
        if (pos > this.qtde || pos < 0)
            return false;
         
        aux = this.primeiro;
        for(int i = 0; i < pos; i++){
            aux = aux.getProx();
        }
        
        aux.setDado(valor);
        
        return true;
    }
    
    @Override
    public String toString() {
        No aux = this.primeiro;
        String saida =  "Lista{";
                
        do{
            saida += aux.getDado().toString() + "\n******************\n";
            aux = aux.getProx();
        }while (aux != this.primeiro);

        saida += '}';

        return saida;
    }
}