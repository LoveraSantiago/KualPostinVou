package lovera.kualpostinvou.modelos;

import lovera.kualpostinvou.Aplicacao;

public class Instalacao {

    private int codApp = Aplicacao.COD_APLICACAO;
    private long codUsuario;
    private String dataHora;
    private String deviceOS = "android";
    private String deviceToken;

    public int getCodApp() {
        return codApp;
    }

    public void setCodApp(int codApp) {
        this.codApp = codApp;
    }

    public long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
