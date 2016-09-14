package lovera.kualpostinvou.modelos;

import java.util.List;

public class Pessoa {

    private double latitude;
    private double longitude;

    private String CEP;
    private String biografia;
    private String dataNascimento;
    private String nomeCompleto;
    private String nomeUsuario;
    private String sexo;
    private String tokenFacebook;
    private String tokenGoogle;
    private String tokenInstagram;
    private String tokenTwitter;

    private List<Link> links;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public void setTokenFacebook(String tokenFacebook) {
        this.tokenFacebook = tokenFacebook;
    }

    public String getTokenGoogle() {
        return tokenGoogle;
    }

    public void setTokenGoogle(String tokenGoogle) {
        this.tokenGoogle = tokenGoogle;
    }

    public String getTokenInstagram() {
        return tokenInstagram;
    }

    public void setTokenInstagram(String tokenInstagram) {
        this.tokenInstagram = tokenInstagram;
    }

    public String getTokenTwitter() {
        return tokenTwitter;
    }

    public void setTokenTwitter(String tokenTwitter) {
        this.tokenTwitter = tokenTwitter;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
