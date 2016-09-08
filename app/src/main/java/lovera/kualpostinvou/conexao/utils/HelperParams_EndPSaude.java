package lovera.kualpostinvou.conexao.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.CAMPOS;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.CATEGORIA;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.MUNICIPIO;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.PAGINA;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.QUANTIDADE;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.TEXTO;
import static lovera.kualpostinvou.conexao.utils.ConstantesParametros.UF;

public class HelperParams_EndPSaude {

    //Ser usado no endpoint "rest/estabelecimentos"
    public Map<String, String> factoryMap_EndP_Estabelecimentos(String municipio, String uf, List<String> campos, int pagina, int quantidade){
        Map<String, String> map = new HashMap<>();

        if(isStringNotNullOrEmpty(municipio)){
            map.put(MUNICIPIO, municipio);
        }
        if(isStringNotNullOrEmpty(uf)){
            map.put(UF, uf);
        }
        if(isListNotNullOrEmpty(campos)){
            StringBuilder camposVirgula = new StringBuilder();
            for(String campo : campos){
                camposVirgula.append(campo + ",");
            }
            map.put(CAMPOS, camposVirgula.toString());
        }
        if(isNumberMaiorQueZero(pagina)){
            map.put(PAGINA, String.valueOf(pagina));
        }
        if(isNumberMaiorQueZero(quantidade)){
            map.put(QUANTIDADE, String.valueOf(quantidade));
        }
        return map;
    }

    public Map<String, String> factoryMap_EndP_Estabelecimentos(String texto, String categoria, String campos, int pagina, int quantidade){
        Map<String, String> map = new HashMap<>();

        if(isStringNotNullOrEmpty(texto)){
            map.put(TEXTO, texto);
        }
        if(isStringNotNullOrEmpty(categoria)){
            map.put(CATEGORIA, categoria);
        }
        if(isStringNotNullOrEmpty(campos)){
            map.put(CAMPOS, campos);
        }
        if(isNumberMaiorQueZero(pagina)){
            map.put(PAGINA, String.valueOf(pagina));
        }
        if(isNumberMaiorQueZero(quantidade)){
            map.put(QUANTIDADE, String.valueOf(quantidade));
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
