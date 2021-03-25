package cajero;

public class Usuario {
	private String nombre;
	private int tarjeta;
	private float saldo;
	private String contrasena;

	public float getSaldo() {
		return this.saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTarjeta() {
		return this.tarjeta;
	}

	public void setTarjeta(int tarjt) {
		this.tarjeta = tarjt;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	// Constructor
	public Usuario(int numero, String contrasena, float saldo, String nombre) {
		setTarjeta(numero);
		setContrasena(contrasena);
		setSaldo(saldo);
		setNombre(nombre);

	}

	// Metodos de usuario

	public void retirar(float cantidad_retirada) {
		this.saldo = this.saldo - cantidad_retirada;
	}

	public void transferir(int cuenta, float cantidad) {
		this.saldo -= cantidad;
	}

	public void depositar(float cantidad) {
		this.saldo += cantidad;
	}
}