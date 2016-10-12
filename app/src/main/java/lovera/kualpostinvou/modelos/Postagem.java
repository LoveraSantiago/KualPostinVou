package lovera.kualpostinvou.modelos;

public class Postagem {

    private Autor autor;
    private long codGrupoDestino;
    private long codObjetoDestino;
    private long codPessoaDestino;
    private long codTipoObjetoDestino;
    private double latitude;
    private double longitude;
    private PostagemRelacionada postagemRelacionada;
    private Tipo tipo;

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public long getCodGrupoDestino() {
        return codGrupoDestino;
    }

    public void setCodGrupoDestino(long codGrupoDestino) {
        this.codGrupoDestino = codGrupoDestino;
    }

    public long getCodObjetoDestino() {
        return codObjetoDestino;
    }

    public void setCodObjetoDestino(long codObjetoDestino) {
        this.codObjetoDestino = codObjetoDestino;
    }

    public long getCodPessoaDestino() {
        return codPessoaDestino;
    }

    public void setCodPessoaDestino(long codPessoaDestino) {
        this.codPessoaDestino = codPessoaDestino;
    }

    public long getCodTipoObjetoDestino() {
        return codTipoObjetoDestino;
    }

    public void setCodTipoObjetoDestino(long codTipoObjetoDestino) {
        this.codTipoObjetoDestino = codTipoObjetoDestino;
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

    public PostagemRelacionada getPostagemRelacionada() {
        return postagemRelacionada;
    }

    public void setPostagemRelacionada(PostagemRelacionada postagemRelacionada) {
        this.postagemRelacionada = postagemRelacionada;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
