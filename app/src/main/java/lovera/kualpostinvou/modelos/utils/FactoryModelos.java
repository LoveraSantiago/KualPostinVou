package lovera.kualpostinvou.modelos.utils;

import java.util.Calendar;

import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.views.utils.Utils_View;

public class FactoryModelos {

    public Grupo geradorDeGrupo(String codUnidade){
        Grupo grupoResult = new Grupo();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(codUnidade);
        stringBuilder.append(Utils_View.dateToString(Calendar.getInstance().getTime(), "ddMMyyyy"));

        return grupoResult;
    }
}
