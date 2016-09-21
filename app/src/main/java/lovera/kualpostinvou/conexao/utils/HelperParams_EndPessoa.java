package lovera.kualpostinvou.conexao.utils;

import java.util.HashMap;
import java.util.Map;

import static lovera.kualpostinvou.modelos.constantes.Parametros.*;
import static lovera.kualpostinvou.conexao.utils.HelperParamsUtils.*;

public class HelperParams_EndPessoa {

    public Map<String, String> factoryMap_EndP_Autenticar(String appIdentifier, String email, String senha, String faceToken, String googleToken, String twitterToken){
        Map<String, String> map = new HashMap<>();

        if(isStringNotNullOrEmpty(appIdentifier)){
            map.put(APPIDENTIFIER.getTexto(), appIdentifier);
        }
        if(isStringNotNullOrEmpty(email)){
            map.put(EMAIL.getTexto(), email);
        }
        if(isStringNotNullOrEmpty(senha)){
            map.put(SENHA.getTexto(), senha);
        }
        if(isStringNotNullOrEmpty(faceToken)){
            map.put(FACEBOOKTOKEN.getTexto(), faceToken);
        }
        if(isStringNotNullOrEmpty(googleToken)){
            map.put(GOOGLETOKEN.getTexto(), googleToken);
        }
        if(isStringNotNullOrEmpty(twitterToken)){
            map.put(TWITTERTOKEN.getTexto(), twitterToken);
        }
        return map;
    }

}
