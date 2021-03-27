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
		
		
		
		while(true) {
			Usuario usuario_actual = null;
			boolean reiniciar_ejecucion=false;
		
			System.out.print("Ingrese el número de tarjeta : ");
			int num_tarjeta=sc.nextInt();
			int indice_usuario=verificar_tarjeta(num_tarjeta,usuarios);
			if(indice_usuario==-1) {
				System.out.println("------------------");
				System.out.println("¡Tarjeta invalida!");
				System.out.println("------------------");
				reiniciar_ejecucion=true;
			} else {
				usuario_actual=usuarios.get(indice_usuario);
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
					System.out.println("Intentos restantes : "+(4-intentos));
					System.out.println("---------------------");
				}
			} while(true);
			
			if(reiniciar_ejecucion)
				continue;
			
			//opciones
			while(true) {
				boolean terminar_opciones=false;
				int op=opciones_cajero(sc);
				boolean ejecucion=ejecutar_opciones(op,usuario_actual,sc,usuarios);
				if(ejecucion) {
					System.out.println("-----------------------------");
					System.out.println("Operacion realizada con exito");
					System.out.println("-----------------------------");
					System.out.println("¿Quieres realizar otra opcion ?  ");
					
					while(true) {
						
						System.out.println("1. Si");
						System.out.println("2. No");
						System.out.print("Elegir : ");
						int op_reinicio=sc.nextInt();
						if(op_reinicio==2) {
							System.out.println("Sesion finalizada");
							terminar_opciones=true;
							break;
						} else if(op_reinicio!=1) {
							System.out.println("Opcion invalida, elija una opcion ");
						} else {
							break;
						}
					} 
					
				}else if(!ejecucion && op==3) {
					System.out.println("");
					System.out.println("Error : Verifique la cantidad");
					System.out.println("Billetes disponibles : 10,20,50,100");
				} else if(!ejecucion && op==4) {
					System.out.println("");
					System.out.println("Error verifique tu saldo o cantidad o cuenta");
				}  else if(!ejecucion && op==5) {
					System.out.println("Sesion finalizada");
					break;
				} else {
					System.out.print("Opcion no valida");
				}
				if(terminar_opciones)
					break;
			} 
		} 
	}
	
	public static int verificar_tarjeta(int tarjeta,ArrayList<Usuario> usuarios) {
		int indice_usuario=0;
		for(int x=0;x<usuarios.size();x++) {
			if(tarjeta==usuarios.get(x).getTarjeta()) {
				indice_usuario=x;
				break;
			} else if(x==usuarios.size()-1) {
				indice_usuario=-1;
				break;
			}
		}
		return indice_usuario;
		
	}
	public static int opciones_cajero(Scanner sc) {
		System.out.println("");
		System.out.println("OPERACIONES");
		System.out.println("1. Consultar saldo");
		System.out.println("2. Deposito");
		System.out.println("3. Retiro");
		System.out.println("4. Transferencia");
		System.out.println("5. Salir");
		System.out.print("Que operacion quieres realizar : ");
		int op=sc.nextInt();
		
		if(op<0 && op>3) {
			System.out.print("Opcion no valida");
			return -1;
		}
		return op;
	}
	public static boolean ejecutar_opciones(int op,Usuario user,Scanner sc,ArrayList<Usuario> usuarios) {
		switch (op) {
		case 1:
			System.out.println("");
			System.out.println("Usuario : "+user.getNombre());
			System.out.println("El saldo actual es de : "+user.getSaldo());
			System.out.println("");
			break;
		case 2:
			System.out.println("Ingrese la cantidad : ");
			float cantidad=sc.nextFloat();
			user.depositar(cantidad);
			System.out.println("Usuario : "+user.getNombre());
			System.out.println("Saldo : "+user.getSaldo());
			break;
		case 3:
			float retiro;
			System.out.println("Billetes disponibles : 10,20,50,100 ");
			System.out.print("Cantidad a retirar : ");
			retiro=sc.nextFloat();
			if(user.retirar(retiro)) {
				System.out.println("Usuario : "+user.getNombre());
				System.out.println("Saldo : "+user.getSaldo());
				break;
			} else {
				return false;
			}
			
		case 4:
			System.out.print("Cuenta a depositar : ");
			int cuenta=sc.nextInt();
			int id=verificar_tarjeta(cuenta,usuarios);
			if(id==-1) {
				System.out.println("La cuenta ingresada no existe");
				return false;
			}else if(user.getTarjeta()==usuarios.get(id).getTarjeta()) {
				System.out.println("");
				System.out.print("No se puede transferir a la misma cuenta");
				return false;
			} else {
				System.out.print("Ingrese la cantidad : ");
				float cant=sc.nextFloat();
				if(user.transferir(cant) ){
					usuarios.get(id).depositar(cant);
					return true;
				} else {
					return false;
				}
				
			}	
		case 5:
			return false;
		default:
			return false;
		}
		return true;
	}
}