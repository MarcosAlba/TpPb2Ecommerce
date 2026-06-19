package dominio;

public class LineaDeCarrito {
	private Producto producto;
	private Integer cantidad;
	
	public LineaDeCarrito(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public Double obtenerSubTotal() {
		return this.producto.getPrecioBase() * this.cantidad;
	}

	
	
	
}
