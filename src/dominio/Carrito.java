package dominio;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
	private List<LineaDeCarrito> lineas;
	
	
	public Carrito() {
		this.lineas = new ArrayList<>();
	}

	public boolean agregarLinea(LineaDeCarrito linea) {
		return this.lineas.add(linea);
	}
	
	public boolean eliminarLinea(LineaDeCarrito linea) {
		return this.lineas.remove(linea);
	}

	public List<LineaDeCarrito> getLineas() {
		return lineas;
	}
	
	
}
