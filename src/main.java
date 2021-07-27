import java.lang.reflect.Field;
import java.util.ArrayList;

import clases.Persona;
import servicios.Consultas;
import utilidades.UBean;
import utilidades.Uconexion;


public class main {

	public static void main(String[] args) {
		
		//Crear objeto de tipo Persona
				Persona p = new Persona();
				
				//obtener atributos de objeto
				ArrayList<Field> fields = UBean.obtenerAtributos(p);
				System.out.println("obtener atributos de objeto");
				for(Field f: fields) {
					System.out.println(f.getName());
				}
				System.out.println("------------------------------------");
				//setear parametros por reflection
				System.out.println("setear parametros por reflection");
				UBean.ejecutarSet(p, "nombre", "Sebastian");
				UBean.ejecutarSet(p, "apellido", "Rilo");
				System.out.println("------------------------------------");
				//obtener parametros por reflection
				System.out.println("obtener parametros por reflection");
				System.out.println(UBean.ejecutarGet(p, "nombre"));
				System.out.println(UBean.ejecutarGet(p, "apellido"));
				System.out.println("------------------------------------");
				//guardar Objeto
				System.out.println("guardar Objeto");
				Consultas.guardar(p);
				System.out.println("------------------------------------");
				//modificar objeto
				System.out.println("modificar objeto");
				UBean.ejecutarSet(p, "id", 1);
				UBean.ejecutarSet(p, "nombre", "juan");
				UBean.ejecutarSet(p, "apellido", "Perez");
				Consultas.modificar(p);
				System.out.println("------------------------------------");
				//obtener objeto por id
				System.out.println("obtener objeto por id");
				System.out.println((Persona)Consultas.obtenerPorId(Persona.class, 1));
				System.out.println("------------------------------------");
				//eliminar objeto
				System.out.println("eliminar objeto");
				UBean.ejecutarSet(p, "id", 1);
				Consultas.eliminar(p);
				System.out.println("------------------------------------");
				//obtener todos los objetos
				System.out.println("obtener todos los objetos");
				ArrayList<Persona> personas = (ArrayList<Persona>) Consultas.obtenerTodos(Persona.class);
				for(Persona per: personas) {
					System.out.println(per);
				}
		
	}

}
