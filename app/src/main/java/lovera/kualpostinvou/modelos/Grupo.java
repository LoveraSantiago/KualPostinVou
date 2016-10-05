package lovera.kualpostinvou.modelos;

import lovera.kualpostinvou.Aplicacao;

public class Grupo {

    private int codAplicativo = Aplicacao.COD_APLICACAO;
    private int codGrupoPai;
    private int codObjeto;
    private int descricao;

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

    public int getDescricao() {
        return descricao;
    }

    public void setDescricao(int descricao) {
        this.descricao = descricao;
    }
}
