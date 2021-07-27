package utilidades;

import java.util.ResourceBundle;

public class Properties {

	private static Properties properties;
	private String driver;
	private String url;
	private String username;
	private String password;

	private Properties(String ruta) {
		ResourceBundle rb = ResourceBundle.getBundle(ruta);
		this.driver = rb.getString("driver");
		this.url = rb.getString("url");
		this.username = rb.getString("username");
		this.password = rb.getString("password");
	}

	/**
	 * Genera un nuevo objeto Properties
	 * @param ruta Ruta donde se encuentra el archivo de configuracion de la DB
	 * @return
	 */
	public static Properties generarObjeto(String ruta) {
		if (properties == null) {
			properties = new Properties(ruta);
		}
		return properties;
	}

	public String getDriver() {
		return this.driver;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
