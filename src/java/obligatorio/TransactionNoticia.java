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
public class TransactionNoticia {       
    
    private String usuario;
    private String titulo;
    private String validacion;
    
    public byte[] signature; //This is to prevent anybody else from spending funds in our wallet.
    public PublicKey sender; //Senders address/public key.
    public PublicKey reciepient; //Recipients address/public key.
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
        
    public TransactionNoticia(String usr, String tit, String val){
        this.usuario = usr;
        this.titulo = tit;
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
     * @return the validacion
     */
    public String getValidacion() {
        return validacion;
    }

    /**
     * @param validacion the validacion to set
     */
    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }
    
    public boolean processNoticia() {
		
		if(verifySignature() == false) {
			System.out.println("#Noticia Signature failed to verify");
			return false;
		}
				
		//Gathers transaction inputs (Making sure they are unspent):
		for(TransactionInput i : inputs) {
			i.UTXO = NoobChain.UTXOs.get(i.transactionOutputId);
		}

		//Checks if transaction is valid:
		if(getInputsValue() < NoobChain.minimumTransaction) {
			System.out.println("Transaction Inputs too small: " + getInputsValue());
			System.out.println("Please enter the amount greater than " + NoobChain.minimumTransaction);
			return false;
		}
		
		
		transactionId = calulateHash();
		outputs.add(new TransactionOutput( this.reciepient, value,transactionId)); //send value to recipient
		outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender		
				
		//Add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			NoobChain.UTXOs.put(o.id , o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			NoobChain.UTXOs.remove(i.UTXO.id);
		}
		
		return true;
	}
	
    
    public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it, This behavior may not be optimal.
			total += i.UTXO.value;
		}
		return total;
	}
	
    public void generateSignature(PrivateKey privateKey) {
            String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + this.titulo;
            signature = StringUtil.applyECDSASig(privateKey,data);		
    }

    public boolean verifySignature() {
            String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + this.titulo;
            return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public float getOutputsValue() {
            float total = 0;
            for(TransactionOutput o : outputs) {
                    total += o.value;
            }
            return total;
    }

    private String calulateHash() {
            sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
            return StringUtil.applySha256(
                            StringUtil.getStringFromKey(sender) +
                            StringUtil.getStringFromKey(reciepient) +
                            Float.toString(value) + sequence
                            );
    }

}
