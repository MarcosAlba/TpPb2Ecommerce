package dominio;

public class LineasDeOrden {
	private Producto producto;
	private Integer cantidad;
	

	public LineasDeOrden(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	
	
}
