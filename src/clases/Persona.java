package clases;

import anotaciones.*;

@Tabla(nombre="Persona")
public class Persona {

	@Id
	@Columna(nombre="id")
	private Integer id;
	@Columna(nombre="nombre")
	private String nombre;
	@Columna(nombre="apellido")
	private String apellido;

	public Persona() {
	}

	public Persona(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (o.getClass().equals(this.getClass())) {
			Persona p = (Persona) o;
			if (p.id == this.id) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.id * 7 + this.nombre.hashCode() + this.apellido.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id + " - ");
		sb.append(this.nombre + " - ");
		sb.append(this.apellido);
		return sb.toString();
	}
}