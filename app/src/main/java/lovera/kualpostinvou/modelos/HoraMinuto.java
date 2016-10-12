package lovera.kualpostinvou.modelos;

public class HoraMinuto {

    private final int hora;
    private final int minuto;


    public HoraMinuto(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(String.format("%02d", this.hora  ) + "h " +
                                          String.format("%02d", this.minuto) + "m").toString();
    }
}
