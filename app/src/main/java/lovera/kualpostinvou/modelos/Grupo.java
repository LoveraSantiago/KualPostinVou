package lovera.kualpostinvou.modelos;

import lovera.kualpostinvou.Aplicacao;

public class Grupo {

    private transient long codGrupo;
    private int codAplicativo = Aplicacao.COD_APLICACAO;
    private transient int codGrupoPai;
    private long codObjeto;
    private String descricao;

    public long getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(long codGrupo) {
        this.codGrupo = codGrupo;
    }

    public int getCodAplicativo() {
        return codAplicativo;
    }

    public void setCodAplicativo(int codAplicativo) {
        this.codAplicativo = codAplicativo;
    }

    public int getCodGrupoPai() {
        return codGrupoPai;
    }

    public void setCodGrupoPai(int codGrupoPai) {
        this.codGrupoPai = codGrupoPai;
    }

    public long getCodObjeto() {
        return codObjeto;
    }

    public void setCodObjeto(long codObjeto) {
        this.codObjeto = codObjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
