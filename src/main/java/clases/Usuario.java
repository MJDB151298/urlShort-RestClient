package clases;

import java.util.ArrayList;

public class Usuario {
    private String id;
    private String username;
    private String nombre;
    private String password;
    private boolean administrador;

    public Usuario(){

    }
    public Usuario(String username, String nombre, String password, boolean administrador){
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
    }

    public String getId() {
        return id;
    }
    public boolean isAdministrador() {
        return administrador;
    }
    public String getNombre() {
        return nombre;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) { this.username = username; }
}
