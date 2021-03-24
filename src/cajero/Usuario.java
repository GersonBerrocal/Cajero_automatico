package cajero;

public class Usuario {
	String nombre;
	int num_tarjeta;
	float saldo;
	String contrasena;
	public Usuario(int cod,String contrasena, float saldo, String nom) {
		this.nombre=nom;
		this.num_tarjeta=cod;
		this.saldo=saldo;
		this.contrasena=contrasena;
	}
	public float ver_saldo() {
		return this.saldo;
	}
	public void retirar(float cantidad_retirada) {
		this.saldo=this.saldo-cantidad_retirada;
	}
}