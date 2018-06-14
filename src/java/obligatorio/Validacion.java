/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio;

/**
 *
 * @author jose_
 */
public class Validacion {
    
    private String usuario;
    private boolean validacion;
        
    public Validacion(String usr, boolean val){
        this.usuario = usr;
        this.validacion = val;
    }
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the validacion
     */
    public boolean isValidacion() {
        return validacion;
    }

    /**
     * @param validacion the validacion to set
     */
    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }
    
}
