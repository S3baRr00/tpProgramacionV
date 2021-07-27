package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Uconexion {

	private static Uconexion cn;
	public static Connection c;

	private Uconexion() {
	}

	/**
	 * Genera un nuevo objeto UConexion
	 * 
	 * @return El nuevo objeto Uconexion
	 */
	public static Uconexion generarObjeto() {
		if (cn == null) {
			cn = new Uconexion();
		}
		return cn;
	}

	/**
	 * Abre la conexion con la base de datos
	 */
	public static void abrirConexion() {
		try {
			Properties p = Properties.generarObjeto("utilidades.framework");
			Class.forName(p.getDriver());
			c = DriverManager.getConnection(p.getUrl(), p.getUsername(), p.getPassword());
			System.out.println("Conexion establecida");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la conexion con la base de datos
	 */
	public static void cerrarConexion() {
		try {
			c.close();
			c = null;
			System.out.println("Conexion Cerrada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
