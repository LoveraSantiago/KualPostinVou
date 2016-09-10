package lovera.kualpostinvou.modelos;

public enum Categoria {

    TODAS("todas"),
    HOSPITAL("hospital"),
    POSTO_DE_SAUDE("posto de saúde"),
    URGENCIA("urgência"),
    SAMU("samu"),
    FARMACIA("farmácia"),
    CLINICA("clínica"),
    CONSULTORIO("consultório"),
    LABORATORIO("laboratório"),
    APOIO_A_SAUDE("apoio à saúde"),
    ATENCAO_ESPECIFICA("atenção específica"),
    UNIDADE_ADMINISTRATIVA("unidade administrativa"),
    ATENDIMENTO_DOMICILIAR("atendimento domiciliar");

    private String texto;

    Categoria(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static String[] getTextos(){
        Categoria[] values = Categoria.values();
        String[] textos = new String[values.length];

        for(int i = 0; i < values.length; i++){
            textos[i] = values[i].getTexto();
        }
        return textos;
    }
}
