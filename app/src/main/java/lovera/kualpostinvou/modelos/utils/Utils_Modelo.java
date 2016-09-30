package lovera.kualpostinvou.modelos.utils;

import lovera.kualpostinvou.modelos.ErrorObj;

/**
 * Created by resource on 30/09/2016.
 */
public class Utils_Modelo {

    public static ErrorObj cloneErrorObjeto(ErrorObj retorno, ErrorObj modelo){
        retorno.setDadosTecnicos(modelo.getDadosTecnicos());
        retorno.setMensagens(modelo.getMensagens());
        retorno.setReasonPhrase(modelo.getReasonPhrase());
        retorno.setStatusCode(modelo.getStatusCode());
        retorno.setUrl(modelo.getUrl());
        retorno.setSiglaErro(modelo.getSiglaErro());
        return retorno;
    }
}
