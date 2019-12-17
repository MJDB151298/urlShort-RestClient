package clases;

public class Visita {
    private String id;
    private Url url;
    private String sistemaOperativo;
    private String navegador;
    private String direccionIp;
    private long hora;
    private String dia;

    public Visita(){

    }

    public Visita(Url url, String sistemaOperativo, String navegador, String direccionIp, long hora, String dia){
        this.url = url;
        this.sistemaOperativo = sistemaOperativo;
        this.navegador = navegador;
        this.direccionIp = direccionIp;
        this.hora = hora;
        this.dia = dia;
    }

    public String getId() {
        return id;
    }
    public String getDireccionIp() {
        return direccionIp;
    }
    public String getNavegador() {
        return navegador;
    }
    public String getSistemaOperativo() {
        return sistemaOperativo;
    }
    public Url getUrl() {
        return url;
    }

    public String getDia() {
        return dia;
    }

    public long getHora() {
        return hora;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }
    public void setUrl(Url url) {
        this.url = url;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public void setHora(long hora) {
        this.hora = hora;
    }
}
