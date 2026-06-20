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
	
	public void vaciar() {
	    this.lineas.clear();
	}
	
	
	public String obtenerDetalle() {

	    if (this.lineas.isEmpty()) {
	        return "===== CARRITO =====\n\nEl carrito está vacío.";
	    }

	    String detalle = "===== CARRITO =====\n\n";

	    Double total = 0.0;

	    for (LineaDeCarrito linea : this.lineas) {

	        detalle += linea.toString() + "\n";

	        total += linea.obtenerSubTotal();
	    }

	    detalle += "\n-------------------";
	    detalle += "\nTOTAL: $" + total;

	    return detalle;
	}
}
