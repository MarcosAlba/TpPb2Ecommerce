package dominio;

public class ProductoEstandar extends Producto{

	public ProductoEstandar(Categoria categoria, Double precioBase, Inventario inventario, Oferta oferta) {
		super(categoria, precioBase, inventario, oferta);
	}

	
	@Override
	public Double calcularPrecioFinal() {
		Double precioFinal = super.getPrecioBase();
		
		Oferta oferta = super.getOferta();
		
		if(oferta != null) {
			precioFinal = super.getOferta().aplicarDescuento(precioFinal);
		}
		
		return precioFinal;
	}

}
