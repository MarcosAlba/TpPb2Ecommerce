package dominio;

public abstract class Producto implements Comparable<Producto>{
	private String nombre;
	private Integer id;
	private static Integer idAutoIncremental = 1;
	private Categoria categoria;
	private Double precioBase;
	private Inventario inventario;
	private Oferta oferta;

	public Producto(String nombre, Categoria categoria, Double precioBase, Inventario inventario, Oferta oferta) {
		this.id = Producto.idAutoIncremental++;
		this.categoria = categoria;
		this.precioBase = precioBase;
		this.inventario = inventario;
		this.oferta = oferta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static Integer getIdAutoIncremental() {
		return idAutoIncremental;
	}

	public static void setIdAutoIncremental(Integer idAutoIncremental) {
		Producto.idAutoIncremental = idAutoIncremental;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(Double precioBase) {
		this.precioBase = precioBase;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public abstract Double calcularPrecioFinal();

	public String obtenerDetalle() {
		String mensaje = "ID: " + this.id + "\nNombre: " + this.nombre + "\nCategoría: " + this.categoria
				+ "\nPrecio: $" + this.calcularPrecioFinal();

		return mensaje;
	}
}
