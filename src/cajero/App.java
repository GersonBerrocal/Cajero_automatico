package cajero;
import cajero.Usuario;
import java.util.Scanner;
import java.util.ArrayList;
public class App {
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		
		
		ArrayList<Usuario> todos=new ArrayList<Usuario>();
		todos.add(new Usuario(98765,"123a",506.3f,"Pedro"));
		todos.add(new Usuario(12345, "123b",600.0f,"Maria"));
		
		
		int num_tarjeta;
		Usuario usuario_actual;
		
		int bloq_tarj=1;
		
		do {
		
			System.out.print("Ingrese el número de tarjeta : ");
			num_tarjeta=sc.nextInt();
			for(int x=0;x<todos.size();x++) {
				if(num_tarjeta==todos.get(x).num_tarjeta) {
					usuario_actual=todos.get(x);
					String contrasena="";
					System.out.print("Ingrese la contraseña : ");
					contrasena=sc.next();
					if(contrasena.equals(usuario_actual.contrasena)) {
						//ejecutar codigo si se encontro la tarjeta
						boolean verificar_seleccion;
						int opcion_elegida=0;
						boolean verificar_ciclo=false;
						do {
							verificar_seleccion=ejecutar_opciones(opciones_cajero(sc),usuario_actual,sc);
							if(verificar_seleccion) {
								verificar_ciclo=false;
							} else {
								System.out.println("Opcion no valida");
								verificar_ciclo=true;
							}
							
						} while(verificar_ciclo);
					} else {
						System.out.println("Contraseña no valida");
						bloq_tarj++;
					}
					
					
					
					break;
				} else if(x==todos.size()-1){
					bloq_tarj++;
				}
			}
			
		} while(bloq_tarj<=3);
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
			System.out.print("Usuario : "+user.nombre);
			System.out.println("");
			System.out.print("El saldo actual es de : "+user.ver_saldo());
			System.out.println("");
			break;
		case 2:
			float retiro;
			System.out.print("Cantidad a retirar : ");
			retiro=sc.nextFloat();
			
			System.out.print("Usuario : "+user.nombre);
			System.out.println("");
			user.retirar(retiro);
			System.out.print("El saldo actual es de : "+user.ver_saldo());
			System.out.println("");
			break;
		default:
			return false;
		}
		return true;
	}
}