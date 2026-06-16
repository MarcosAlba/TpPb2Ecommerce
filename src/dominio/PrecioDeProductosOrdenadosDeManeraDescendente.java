package dominio;

import java.util.Comparator;

public class PrecioDeProductosOrdenadosDeManeraDescendente implements Comparator<Producto>{

	@Override
	public int compare(Producto o1, Producto o2) {
		return o2.getPrecioBase().compareTo(o1.getPrecioBase());
	}

}
