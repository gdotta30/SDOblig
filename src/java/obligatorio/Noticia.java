/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import noobchain.NoobChain;
import noobchain.StringUtil;
import noobchain.TransactionInput;
import noobchain.TransactionOutput;

/**
 *
 * @author jose_
 */
public class Noticia {       
        
    private String usuario;
    private String titulo;
    private ArrayList<Validacion> validaciones;
    
    public byte[] signature; //This is to prevent anybody else from spending funds in our wallet.
    public PublicKey sender; //Senders address/public key.
    public PublicKey reciepient; //Recipients address/public key.    
        
    public Noticia(String usr, String tit, ArrayList<Validacion> vals){
        this.usuario = usr;
        this.titulo = tit;
        this.validaciones = vals;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
    
    /**
     * @return the validaciones
     */
    public ArrayList<Validacion> getValidaciones() {
        return validaciones;
    }

    /**
     * @param validaciones the validaciones to set
     */
    public void setValidaciones(ArrayList<Validacion> validaciones) {
        this.validaciones = validaciones;
    }
    
}
