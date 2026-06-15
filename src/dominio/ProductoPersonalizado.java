package dominio;

import java.util.ArrayList;
import java.util.List;

public class ProductoPersonalizado extends Producto {
	private List<Opcion> opciones;

	public ProductoPersonalizado(String nombre, Categoria categoria, Double precioBase, Inventario inventario,
			Oferta oferta) {
		super(nombre, categoria, precioBase, inventario, oferta);
		this.opciones = new ArrayList<>();
	}

	public boolean agregarOpcion(Opcion opcion) {
		return this.opciones.add(opcion);
	}

	@Override
	public Double calcularPrecioFinal() {
		Double precio = super.getPrecioBase();

		for (Opcion opcion : opciones) {
			precio += opcion.getCosto();
		}

		Oferta oferta = super.getOferta();

		if (oferta != null) {
			precio = super.getOferta().aplicarDescuento(precio);
		}

		return precio;
	}

}
