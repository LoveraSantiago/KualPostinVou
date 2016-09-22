package lovera.kualpostinvou.modelos;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public class Pessoa implements Serializable{

    //Talvez quebre o insert de pessoa por mandar valor 0
    private transient int codigo;

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
    private String email;

    private transient Uri uriImgPerfil;
    private transient int intImgPerfil;

    private List<Link> links;

    @Override
    public String toString() {
        return "Pessoa { nome: " + this.nomeCompleto + "}";
    }

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

    //TODO: validação de ate oito digitos
    public void setCEP(String CEP) {
        this.CEP = CEP.replace("-", "");
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

    //TODO: data de nascimento 1981-06-25
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento.replace("/", "-");
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

    public Uri getUriImgPerfil() {
        return uriImgPerfil;
    }

    public void setUriImgPerfil(Uri uriImgPerfil) {
        this.uriImgPerfil = uriImgPerfil;
    }

    public int getIntImgPerfil() {
        return intImgPerfil;
    }

    public void setIntImgPerfil(int intImgPerfil) {
        this.intImgPerfil = intImgPerfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
