package lovera.kualpostinvou.modelos.constantes;

public enum Parametros {

    CODAPLICATIVO("codAplicativo"),

    MUNICIPIO("municipio"),
    UF("uf"),
    CAMPOS("campos"),
    PAGINA("pagina"),
    QUANTIDADE("quantidade"),
    TEXTO("texto"),
    CATEGORIA("categoria"),
    ESPECIALIDADE("especialidade"),

    APPIDENTIFIER("appIdentifier"),
    EMAIL("email"),
    SENHA("senha"),
    FACEBOOKTOKEN("facebookToken"),
    GOOGLETOKEN("googleToken"),
    TWITTERTOKEN("twitterToken"),

    DESC_GRUPO("descricao");

    private String texto;

    Parametros(String texto){ this.texto = texto; }

    public String getTexto() {
        return texto;
    }
}
