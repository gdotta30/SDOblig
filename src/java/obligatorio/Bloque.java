package obligatorio;

import noobchain.*;
import java.util.ArrayList;
import java.util.Date;

public class Bloque {
	
	public String hash;
	public String previousHash; 
	public String merkleRoot;
	public ArrayList<TransactionNoticia> noticias = new ArrayList<TransactionNoticia>(); //our data will be a simple message.
	public long timeStamp; //as number of milliseconds since 1/1/1970.
	public int nonce;
	
	//Block Constructor.  
	public Bloque(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(noticias);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	//Add noticias to this block
	public boolean addNoticia(TransactionNoticia noticia) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(noticia == null) return false;		
		if((!"0".equals(previousHash))) {
			if((noticia.processNoticia() != true)) {
				System.out.println("Noticia failed to process. Discarded.");
				return false;
			}
		}
		noticias.add(noticia);
		System.out.println("Noticia Successfully added to Block");
		return true;
	}
	
}
