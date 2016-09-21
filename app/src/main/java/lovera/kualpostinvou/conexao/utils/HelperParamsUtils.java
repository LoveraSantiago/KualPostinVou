package lovera.kualpostinvou.conexao.utils;

import java.util.List;

public class HelperParamsUtils {

    public static boolean isStringNotNullOrEmpty(String texto){
        if(texto == null || texto.isEmpty()) return false;
        return true;
    }

    public static boolean isListNotNullOrEmpty(List lista){
        if(lista == null || lista.size() <= 0) return false;
        return true;
    }

    public static boolean isNumberMaiorQueZero(int numero){
        if(numero > 0) return true;
        return false;
    }
}
