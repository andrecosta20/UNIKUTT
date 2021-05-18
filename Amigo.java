package Logica;

public class Amigo {
	private String para;
	private String de;
	private boolean pendente;
	
	public Amigo(String de, String para) {
		this.de = de;
		this.para = para;
		this.pendente = true;
	}
	
	public String getDe() {
		return de;
	}
	
	public String getPara() {
		return para;
	}
	
	public void setPendente (boolean p) {
		this.pendente = p;
	}
	
	public boolean getPendente () {
		return this.pendente;
	}
}
