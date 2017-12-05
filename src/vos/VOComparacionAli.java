package vos;

public class VOComparacionAli {
	
	
	private int id;
	private int veces;
	private String tipo;
	
	
	public VOComparacionAli(int id,int veces, String tipo) {
		this.id = id;
		this.veces = veces;
		this.tipo = tipo;	
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getVeces() {
		return veces;
	}
	public void setVeces(int veces) {
		this.veces = veces;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
