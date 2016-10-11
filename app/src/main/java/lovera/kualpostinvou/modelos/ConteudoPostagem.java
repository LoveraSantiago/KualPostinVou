package lovera.kualpostinvou.modelos;

public class ConteudoPostagem {

    private transient int codPostagem;
    private String JSON;
    private String texto;
    private int valor;

    public int getCodPostagem() {
        return codPostagem;
    }

    public void setCodPostagem(int codPostagem) {
        this.codPostagem = codPostagem;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
