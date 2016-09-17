package lovera.kualpostinvou.modelos.constantes;

public enum Parametros {

    MUNICIPIO("municipio"),
    UF("uf"),
    CAMPOS("campos"),
    PAGINA("pagina"),
    QUANTIDADE("quantidade"),
    TEXTO("texto"),
    CATEGORIA("categoria"),
    ESPECIALIDADE("especialidade");

    private String texto;

    Parametros(String texto){ this.texto = texto; }

    public String getTexto() {
        return texto;
    }
}
