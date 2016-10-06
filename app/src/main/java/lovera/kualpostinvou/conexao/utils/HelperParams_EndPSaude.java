package lovera.kualpostinvou.conexao.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lovera.kualpostinvou.modelos.Grupo;

import static lovera.kualpostinvou.modelos.constantes.Parametros.*;
import static lovera.kualpostinvou.conexao.utils.HelperParamsUtils.*;

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
        if(isStringNotNullOrEmpty(especialidade) && !especialidade.equals("todas")){
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
        if(isStringNotNullOrEmpty(categoria) && !categoria.equals("Todos")){
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

    public Map<String, String> factoryMap_EndPGrupos(Grupo grupo){
        Map<String, String> map = new HashMap<>();

        map.put(CODAPLICATIVO.getTexto(), String.valueOf(grupo.getCodAplicativo()));
        map.put(DESC_GRUPO.getTexto(), grupo.getDescricao());
        return map;
    }
}
