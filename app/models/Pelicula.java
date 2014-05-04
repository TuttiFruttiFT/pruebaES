/**
 * 
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author rfanego
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Pelicula {
	@JsonProperty("nombre_comercial")
	private String nombreComercial;
	
	private String nombreOriginal;
	
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	
	public String getNombreOriginal() {
		return nombreOriginal;
	}
	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}
}
