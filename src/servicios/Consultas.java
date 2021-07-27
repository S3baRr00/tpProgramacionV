package servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import anotaciones.*;
import utilidades.*;

public class Consultas {

	public static void guardar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "insert into ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" (");
		String nombreCampos = "";
		String contenidoCampos = "";
		for (Field f : fields) {
			if (f.getAnnotation(Id.class) == null) {
				nombreCampos = nombreCampos.concat(f.getAnnotation(Columna.class).nombre()).concat(", ");
				if (f.getType() == String.class) {
					contenidoCampos = contenidoCampos.concat("'" + UBean.ejecutarGet(o, f.getName()).toString() + "'")
							.concat(", ");
				} else {
					contenidoCampos = contenidoCampos.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");
				}
			}
		}
		nombreCampos = nombreCampos.substring(0, nombreCampos.length() - 2).concat(") values (");
		contenidoCampos = contenidoCampos.substring(0, contenidoCampos.length() - 2).concat(")");
		query = query.concat(nombreCampos).concat(contenidoCampos);
		System.out.println(query);
		try {
			Uconexion.abrirConexion();
			PreparedStatement pst = Uconexion.c.prepareStatement(query);
			pst.execute();
			Uconexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void modificar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "update ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" set ");
		String valorCampo = "";
		String idKey = "";
		String idValue = "";
		for (Field f : fields) {
			if (f.getAnnotation(Id.class) == null) {
				if (f.getType() == String.class) {
					valorCampo = valorCampo.concat(f.getAnnotation(Columna.class).nombre()).concat("=")
							.concat("'" + UBean.ejecutarGet(o, f.getName()).toString()).concat("', ");
				} else {
					valorCampo = valorCampo.concat(f.getAnnotation(Columna.class).nombre()).concat("=")
							.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");
				}
			} else {
				idKey = f.getAnnotation(Columna.class).nombre();
				idValue = UBean.ejecutarGet(o, f.getName()).toString();
			}
		}
		valorCampo = valorCampo.substring(0, valorCampo.length() - 2);
		query = query.concat(valorCampo).concat(" where ").concat(idKey).concat("=").concat(idValue);
		System.out.println(query);
		try {
			Uconexion.abrirConexion();
			PreparedStatement pst = Uconexion.c.prepareStatement(query);
			pst.execute();
			Uconexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminar(Object o) {
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		String query = "delete from ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" where ");
		for (Field f : fields) {
			if (f.getAnnotation(Id.class) != null) {
				query = query.concat(f.getAnnotation(Columna.class).nombre())
						.concat("=" + UBean.ejecutarGet(o, f.getName()));
				break;
			}
		}
		System.out.println(query);
		try {
			Uconexion.abrirConexion();
			PreparedStatement pst = Uconexion.c.prepareStatement(query);
			pst.execute();
			Uconexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Object obtenerPorId(Class<?> c, Object id) {
		Object o = null;
		try {
			Constructor[] constructores = c.getConstructors();
			for (Constructor con : constructores) {
				if (con.getParameterCount() == 0) {
					o = con.newInstance();
					break;
				}
			}
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			String query = "select * from ".concat(c.getAnnotation(Tabla.class).nombre()).concat(" where ");
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					query = query.concat(f.getAnnotation(Columna.class).nombre()).concat("=" + id.toString());
					break;
				}
			}
			System.out.println(query);
			Uconexion.abrirConexion();
			PreparedStatement pst = Uconexion.c.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				for (Field f : fields) {
					UBean.ejecutarSet(o, f.getName(), rs.getObject(f.getAnnotation(Columna.class).nombre()));
				}
			}
			Uconexion.cerrarConexion();
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static ArrayList<?> obtenerTodos(Class<?> c) {
		ArrayList<Object> objetos = new ArrayList<Object>();
		try {
			Object o = null;
			Constructor[] constructores = c.getConstructors();
			for (Constructor con : constructores) {
				if (con.getParameterCount() == 0) {
					o = con.newInstance();
					break;
				}
			}
			String query = "select * from " + c.getAnnotation(Tabla.class).nombre();
			System.out.println(query);
			Uconexion.abrirConexion();
			PreparedStatement pst = Uconexion.c.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			while (rs.next()) {
				for (Field f : fields) {
					UBean.ejecutarSet(o, f.getName(), rs.getObject(f.getAnnotation(Columna.class).nombre()));
				}
				objetos.add(c.cast(o));
				for (Constructor con : constructores) {
					if (con.getParameterCount() == 0) {
						o = con.newInstance();
						break;
					}
				}
			}
			Uconexion.cerrarConexion();
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return objetos;
	}
}
