package cajero;
import cajero.Usuario;
import java.util.Scanner;
import java.util.ArrayList;
public class App {
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		
		//Definiendo usuarios
		ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
		usuarios.add(new Usuario(98765,"123a",506.3f,"Pedro"));
		usuarios.add(new Usuario(12345, "123b",600.0f,"Maria"));
		
		
		
		
		
		
		do {
			Usuario usuario_actual = null;
			boolean reiniciar_ejecucion=false;
		
			System.out.print("Ingrese el número de tarjeta : ");
			int num_tarjeta=sc.nextInt();
			for(int x=0;x<usuarios.size();x++) {
				if(num_tarjeta==usuarios.get(x).getTarjeta()) {
					usuario_actual=usuarios.get(x);
					break;
				} else  {
					System.out.println("------------------");
					System.out.println("¡Tarjeta invalida!");
					System.out.println("------------------");
					reiniciar_ejecucion=true;
					break;
				}
			}
			if(reiniciar_ejecucion)
				continue;
			
			int intentos=1;
			do {
				System.out.print("Ingrese la contraseña : ");
				String contrasena=sc.next();
				if(contrasena.equals(usuario_actual.getContrasena())) {
					intentos=0;
					break;
				}else if(intentos==3) {
					reiniciar_ejecucion=true;
					System.out.println("-------------------------");
					System.out.println("¡Fallaste los 3 intentos!");
					System.out.println("-------------------------");
					break;
				} else {
					intentos++;
					System.out.println("---------------------");
					System.out.println("¡Contraseña invalida!");
					System.out.println("---------------------");
				}
			} while(true);
			
			if(reiniciar_ejecucion)
				continue;
			
			//opciones
			do {
				boolean op=false;
				op=ejecutar_opciones(opciones_cajero(sc),usuario_actual,sc);
				if(op) {
					System.out.println("Operacion realizada con exito");
					System.out.println("¿Quieres realizar otra opcion ?  ");
					System.out.println("1. Si");
					System.out.println("2. No");
					System.out.print("Elegir : ");
					int op_reinicio=sc.nextInt();
					if(op_reinicio==2)
						break;
				} else {
					System.out.println("Opcion no valida;");
				}
			} while(true);
			
		} while(true);
	}
	
	public static int opciones_cajero(Scanner sc) {
		System.out.println("OPERACIONES");
		System.out.println("1. Consultar saldo");
		System.out.println("2. Retirar");
		System.out.print("Que operacion quieres realizar : ");
		int op=sc.nextInt();
		
		if(op<0 && op>3) {
			System.out.print("Opcion no valida");
			return -1;
		}
		return op;
	}
	public static boolean ejecutar_opciones(int op,Usuario user,Scanner sc) {
		switch (op) {
		case 1:
			System.out.print("Usuario : "+user.getNombre());
			System.out.println("");
			System.out.print("El saldo actual es de : "+user.getSaldo());
			System.out.println("");
			break;
		case 2:
			float retiro;
			System.out.print("Cantidad a retirar : ");
			retiro=sc.nextFloat();
			
			System.out.print("Usuario : "+user.getNombre());
			System.out.println("");
			user.retirar(retiro);
			System.out.print("El saldo actual es de : "+user.getSaldo());
			System.out.println("");
			break;
		default:
			return false;
		}
		return true;
	}
}