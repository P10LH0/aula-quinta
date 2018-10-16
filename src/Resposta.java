
public class Resposta implements java.io.Serializable {

    private int status;
    private Lista result;

    public Resposta(int status, Lista result) {
        this.status = status;
        this.result = result;
    }

    public Resposta() {
        // nao faz nada
    }

    public int getStatus() {
        return status;
    }

    public Lista getResult() {
        return result;
    }

    public void setStatus(int newStatus) {
        status = newStatus;
    }

    public void setResult(Lista newResult) {
        result = newResult;
    }
}
