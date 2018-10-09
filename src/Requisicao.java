
import java.io.Serializable;

public class Requisicao implements Serializable {

    private Lista lista;
    private int operacao;
    private String procura;
    private int tipoConsulta;

    public String getProcura() {
        return procura;
    }

    public int getTipoConsulta() {
        return tipoConsulta;
    }

    public Requisicao(Lista lista, int operacao) {
        this.lista = lista;
        this.operacao = operacao;
    }

    public Requisicao(String procura, int operacao, int tipoConsulta) {
        this.procura = procura;
        this.operacao = operacao;
        this.tipoConsulta = tipoConsulta;
    }

    public Lista getLista() {
        return lista;
    }

    public int getOperacao() {
        return operacao;
    }

}
