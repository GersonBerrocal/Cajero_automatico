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

	public boolean retirar(float cantidad_retirada) {
		if(verificar_retiro(cantidad_retirada) && verificar_billetes(cantidad_retirada)) {
			this.saldo -= cantidad_retirada;
			return true;
		}else {
			return false;
		}
			
		
	}

	public boolean transferir( float cantidad) {
		if(verificar_retiro(cantidad) ) {
			this.saldo -= cantidad;
			return true;
		}else {
			return false;
		}
	}

	public void depositar(float cantidad) {
		this.saldo += cantidad;
	}
	public boolean verificar_retiro(float cantidad) {
		if(cantidad>this.saldo || cantidad<0)
			return false;
		else
			return true;
	}
	public boolean verificar_billetes(float cantidad) {
		if(cantidad%10==0)
			return true;
		else
			return false;
	}
}