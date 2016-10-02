package lovera.kualpostinvou.modelos;

import java.util.List;

import lovera.kualpostinvou.modelos.constantes.MsgErrorObj;

public class ErrorObj {

    private String dadosTecnicos;
    private List<MsgErrorObj> mensagens;
    private String reasonPhrase;
    private String siglaErro;
    private int statusCode;
    private String url;

    public String getDadosTecnicos() {
        return dadosTecnicos;
    }

    public void setDadosTecnicos(String dadosTecnicos) {
        this.dadosTecnicos = dadosTecnicos;
    }

    public List<MsgErrorObj> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MsgErrorObj> mensagens) {
        this.mensagens = mensagens;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getSiglaErro() {
        return siglaErro;
    }

    public void setSiglaErro(String siglaErro) {
        this.siglaErro = siglaErro;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static ErrorObj cloneErrorObjeto(ErrorObj retorno, ErrorObj modelo){
        if(modelo != null){
            retorno.setDadosTecnicos(modelo.getDadosTecnicos());
            retorno.setMensagens(modelo.getMensagens());
            retorno.setReasonPhrase(modelo.getReasonPhrase());
            retorno.setStatusCode(modelo.getStatusCode());
            retorno.setUrl(modelo.getUrl());
            retorno.setSiglaErro(modelo.getSiglaErro());
        }
        return retorno;
    }
}

