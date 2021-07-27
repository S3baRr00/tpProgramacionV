package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UBean {

	/**
	 * Obtiene los atributos de un objeto
	 * @param o Objeto del que se van a obtener los atributos
	 * @return Lista de los atributos del objeto
	 */
	public static ArrayList<Field> obtenerAtributos(Object o) {
		ArrayList<Field> atributosFields = new ArrayList<Field>();
		Class c = o.getClass();
		for (Field f : c.getDeclaredFields()) {
			atributosFields.add(f);
		}
		return atributosFields;
	}

	/**
	 * Invoca el metodo set correspondiente a uno de los campos de un objeto
	 * @param o Instancia del objeto al que se va hacer un set
	 * @param att Nombre del atributo al que se va hacer un set
	 * @param valor Valor que se le va a setear al atributo definido
	 */
	public static void ejecutarSet(Object o, String att, Object valor) {
		Class c = o.getClass();
		Object[] parametros = new Object[1];
		parametros[0] = valor;
		String nombreSetter = "set".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
		for (Method m : c.getDeclaredMethods()) {
			if (nombreSetter.equals(m.getName())) {
				try {
					m.invoke(o, parametros);
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Invoca el metodo Get correspondiente a uno de los campos de un objeto
	 * @param o Instancia del objeto al que se va hacer un get
	 * @param att Nombre del atributo al que se va hacer un get
	 */
	public static Object ejecutarGet(Object o, String att) {
		Class c = o.getClass();
		Object retorno = null;
		String nombreGetter = "get".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
		for (Method m : c.getDeclaredMethods()) {
			if (nombreGetter.equals(m.getName())) {
				try {
					retorno = m.invoke(o, null);
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return retorno;
	}
}