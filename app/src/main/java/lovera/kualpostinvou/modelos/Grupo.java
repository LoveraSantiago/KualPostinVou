package lovera.kualpostinvou.modelos;

import lovera.kualpostinvou.Aplicacao;

public class Grupo {

    private int codAplicativo = Aplicacao.COD_APLICACAO;
    private int codGrupoPai;
    private int codObjeto;
    private String descricao;

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

    public int getCodObjeto() {
        return codObjeto;
    }

    public void setCodObjeto(int codObjeto) {
        this.codObjeto = codObjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
