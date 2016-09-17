package lovera.kualpostinvou.conexao.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lovera.kualpostinvou.modelos.constantes.Parametros.*;

public class HelperParams_EndPSaude {

    //Ser usado no endpoint "rest/estabelecimentos"
    public Map<String, String> factoryMap_EndP_Estabelecimentos(String municipio, String uf, List<String> campos, String especialidade, int pagina, int quantidade){
        Map<String, String> map = new HashMap<>();

        if(isStringNotNullOrEmpty(municipio)){
            map.put(MUNICIPIO.getTexto(), municipio);
        }
        if(isStringNotNullOrEmpty(uf)){
            map.put(UF.getTexto(), uf);
        }
        if(isStringNotNullOrEmpty(especialidade)){
            map.put(ESPECIALIDADE.getTexto(), especialidade);
        }
        if(isListNotNullOrEmpty(campos)){
            StringBuilder camposVirgula = new StringBuilder();
            for(String campo : campos){
                camposVirgula.append(campo + ",");
            }
            map.put(CAMPOS.getTexto(), camposVirgula.toString());
        }
        if(isNumberMaiorQueZero(pagina)){
            map.put(PAGINA.getTexto(), String.valueOf(pagina));
        }
        if(isNumberMaiorQueZero(quantidade)){
            map.put(QUANTIDADE.getTexto(), String.valueOf(quantidade));
        }
        return map;
    }

    public Map<String, String> factoryMap_EndP_Estabelecimentos(String texto, String categoria, String campos, int pagina, int quantidade){
        Map<String, String> map = new HashMap<>();

        if(isStringNotNullOrEmpty(texto)){
            map.put(TEXTO.getTexto(), texto);
        }
        if(isStringNotNullOrEmpty(categoria)){
            map.put(CATEGORIA.getTexto(), categoria);
        }
        if(isStringNotNullOrEmpty(campos)){
            map.put(CAMPOS.getTexto(), campos);
        }
        if(isNumberMaiorQueZero(pagina)){
            map.put(PAGINA.getTexto(), String.valueOf(pagina));
        }
        if(isNumberMaiorQueZero(quantidade)){
            map.put(QUANTIDADE.getTexto(), String.valueOf(quantidade));
        }
        return map;
    }

    public String factoryString_LongLatRaio(double latitude, double longitude, int raio){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("latitude/" + String.valueOf(latitude) + "/")
                     .append("longitude/" + String.valueOf(longitude) + "/")
                     .append("raio/" + String.valueOf(raio));
        return stringBuilder.toString();
    }

    private boolean isStringNotNullOrEmpty(String texto){
        if(texto == null || texto.isEmpty()) return false;
        return true;
    }

    private boolean isListNotNullOrEmpty(List lista){
        if(lista == null || lista.size() <= 0) return false;
        return true;
    }

    private boolean isNumberMaiorQueZero(int numero){
        if(numero > 0) return true;
        return false;
    }

}
