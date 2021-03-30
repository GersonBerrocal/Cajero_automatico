package cajero;
import cajero.Usuario;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class App {
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		
		//Definiendo usuarios
		ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
		usuarios.add(new Usuario(98765,"123a",506.3f,"Gerson"));
		usuarios.add(new Usuario(12345, "123b",600.0f,"Rosa"));
		
		
		
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
				System.out.println("");
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
					intentos=1;
					break;
				}else if(intentos==3) {
					reiniciar_ejecucion=true;
					System.out.println("-------------------------");
					System.out.println("¡Fallaste los 3 intentos!");
					System.out.println("-------------------------");
					System.out.println("");
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
				System.out.println("");
				System.out.println("OPERACIONES");
				System.out.println("[1] Consultar saldo");
				System.out.println("[2] Deposito");
				System.out.println("[3] Retiro");
				System.out.println("[4] Transferencia");
				System.out.println("[5] Pago de servicio");
				System.out.println("[6] Salir");
				System.out.print("Que operacion quieres realizar : ");
				int op=sc.nextInt();
				System.out.println("");
				
				op=(op<0 && op>3)?-1:op;
				
				boolean ejecucion=ejecutar_opciones(op,usuario_actual,sc,usuarios);
				if(ejecucion) {
					System.out.println("-----------------------------");
					System.out.println("Operacion realizada con exito");
					System.out.println("-----------------------------");
					System.out.println("");
					System.out.println("¿Quieres realizar otra opcion ?  ");
					
					while(true) {
						
						System.out.println("1. Si");
						System.out.println("2. No");
						System.out.print("Elegir : ");
						int op_reinicio=sc.nextInt();
						if(op_reinicio==2) {
							System.out.println("-----------------");
							System.out.println("Sesion finalizada");
							System.out.println("-----------------");
							terminar_opciones=true;
							break;
						} else if(op_reinicio!=1) {
							System.out.println("Opcion invalida, elija una opcion ");
						} else {
							break;
						}
					} 
					
				} else if(!ejecucion && op==2) { // todos los else siguientes es para manejo de errores
					System.out.println("");
					System.out.println("Error : la cantidad debe ser mayor igual a 10");
				}else if(!ejecucion && op==3) {
					System.out.println("");
					System.out.println("Error : Verificar la cantidad");
					System.out.println("Debe ser mayor de 10 y menor al saldo disponible");
					System.out.println("Billetes disponibles : 10,20,50,100");
				} else if(!ejecucion && op==4) {
//					System.out.println("Error : Verificar la cantidad");
//					System.out.println("Debe ser mayor de 10 y menor al saldo disponible");
				}  else if(!ejecucion && op==6) {
					System.out.println("-----------------");
					System.out.println("Sesion finalizada");
					System.out.println("-----------------");
					break;
				} else {
					System.out.println("----------------");
					System.out.println("Opcion no valida");
					System.out.println("----------------");
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

	public static boolean ejecutar_opciones(int op,Usuario user,Scanner sc,ArrayList<Usuario> usuarios) {
		String par[];
		
		switch (op) {
		case 1:
			par=new String[] {"Operacion","Consulta","Saldo ",""+user.getSaldo()};
			vaucher(user,par);
			break;
		case 2:
			System.out.print("Ingrese la cantidad : ");
			float cantidad=sc.nextFloat();
			if(cantidad>=10) {
				user.depositar(cantidad);
				par=new String[] {"Operacion","Deposito","Monto",cantidad+"","Saldo",user.getSaldo()+""};
				vaucher(user,par);
			} else
				return false;
			
			break;
		case 3:
			float retiro;

			System.out.println("Billetes disponibles : 10,20,50,100 ");
			System.out.print("Cantidad a retirar : ");
			retiro=sc.nextFloat();
			if(user.retirar(retiro) && retiro>=10) {
				par=new String[] {"Operacion","Retiro","Monto",""+retiro,"Saldo",""+user.getSaldo()};
				vaucher(user,par);
				break;
			} else {
				return false;
			}
			
		case 4:
			System.out.print("Cuenta a depositar : ");
			int cuenta=sc.nextInt();
			int id=verificar_tarjeta(cuenta,usuarios);
			if(id==-1) {
				System.out.println("");
				System.out.println("Error : La cuenta ingresada no existe");
				return false;
			}else if(user.getTarjeta()==usuarios.get(id).getTarjeta()) {
				System.out.println("");
				System.out.print("Error : No se puede transferir a la misma cuenta");
				System.out.print("");
				return false;
			} else {
				System.out.print("Ingrese la cantidad : ");
				float cant=sc.nextFloat();
				if(user.transferir(cant) && cant>10){
					Usuario usuario_transf=usuarios.get(id);
					usuario_transf.depositar(cant);
					par=new String[] {"Operacion","Transferencia","Cuenta destino",usuario_transf.getTarjeta()+"",
							"Monto",cant+"","Saldo",user.getSaldo()+""};
					vaucher(user,par);
					return true;
				} else {
					System.out.println("");
					System.out.println("Error : verifique la cantidad");
					System.out.println("Debe ser mayor a 10 y menor a tu saldo actual");
					return false;
				}
			}
		case 5:
			//pagar servicio
			System.out.println("[1] Agua");
			System.out.println("[2] Luz");
			System.out.print("Que servicio quieres pagar : ");
			int servicio=sc.nextInt();
			if(servicio<0 || servicio>2) {
				System.out.println("Servicio no valido");
				return false;
			}
			String nombre_servicio=(servicio==1)?"Agua":"Luz";
			float deuda_aletoria =(float) Math.floor(Math.random()*(100-10+1)+10);
			System.out.println("La deuda es : "+deuda_aletoria);
			System.out.print("Deseas pagar el servicio [1]Si [2]No: ");
			int el=sc.nextInt();
			if(el==2) {
				System.out.println("No se realizo el pago");
				return false;
			} else if(el!=1) {
				System.out.println("Opcion no valida, no se realizo el pago");
				return false;
			}
			boolean res=user.retirar_general(deuda_aletoria);
			if(!res) {
				System.out.println("Saldo insuficiente, deuda "+deuda_aletoria+" no pagada");
				return false;
			}
			par=new String[]{"Operacion","Pago de servicio","Servicio",nombre_servicio,"Monto",deuda_aletoria+"","Saldo",user.getSaldo()+"",};
			vaucher(user,par);
			return true;
		case 6:
			return false;
		default:
			return false;
		}
		return true;
	}
	public static void vaucher(Usuario usuario,String[] pares) {
		String patron="%-12s : %-20s";
		Date date=new Date();
		DateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("");
		System.out.println("*************");
		System.out.println("---VAUCHER---");
		System.out.println("*************");
		System.out.println(String.format(patron,"Usuario",usuario.getNombre()));
		System.out.println(String.format(patron,"Fecha y hora",formato_fecha.format(date)));
		for(int i=0;i<pares.length;) {
			System.out.println(String.format(patron,pares[i],pares[i+1]));
			i+=2;
		}
		System.out.println("");
		
	}
}