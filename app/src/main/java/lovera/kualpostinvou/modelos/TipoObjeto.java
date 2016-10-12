package lovera.kualpostinvou.modelos;

public class TipoObjeto {

    private transient long codTipoObjeto;
    private String descricao;
    private String ownerTabelaObjeto;
    private String tabelaObjeto;

    public long getCodTipoObjeto() {
        return codTipoObjeto;
    }

    public void setCodTipoObjeto(long codTipoObjeto) {
        this.codTipoObjeto = codTipoObjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOwnerTabelaObjeto() {
        return ownerTabelaObjeto;
    }

    public void setOwnerTabelaObjeto(String ownerTabelaObjeto) {
        this.ownerTabelaObjeto = ownerTabelaObjeto;
    }

    public String getTabelaObjeto() {
        return tabelaObjeto;
    }

    public void setTabelaObjeto(String tabelaObjeto) {
        this.tabelaObjeto = tabelaObjeto;
    }
}
