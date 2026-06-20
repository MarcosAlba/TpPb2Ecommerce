package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Tienda {
	private Set<Usuario> usuarios;
	private Set<Producto> productos;
	private List<Orden> ordenes;
	
	public Tienda() {
		this.usuarios = new HashSet<>();
		this.productos = new HashSet<>();
		this.ordenes = new ArrayList<>();
	}

	public Boolean agregarProducto(Producto producto) {
		return this.productos.add(producto);
	}

	public Boolean eliminarProducto(Producto producto) {
		Boolean seElimino = false;

		if (this.productos.contains(producto)) {
			this.productos.remove(producto);
			seElimino = true;
		}

		return seElimino;
	}

	public Producto buscarProductoPorId(Integer id) throws ProductoNoEncontradoException {
		for (Producto producto : this.productos) {
			if (producto.getId().equals(id)) {
				return producto;
			}
		}

		throw new ProductoNoEncontradoException("Producto no encontrado");

	}

	public Boolean agregarUsuario(Usuario usuario) throws UsuarioDuplicadoException {
		Boolean seAgrego = true;

		for (Usuario user : this.usuarios) {
			if (user.getEmail().equals(usuario.getEmail())) {
				seAgrego = false;
				throw new UsuarioDuplicadoException("Usuario ya creado");
			}
		}

		this.usuarios.add(usuario);

		return seAgrego;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public TreeSet<Producto> obtenerProductosOrdenadosPorNombreDeManeraAscendente() {
		TreeSet<Producto> resultado = new TreeSet<>();

		resultado.addAll(this.productos);

		return resultado;
	}

	public Set<Producto> obtenerProductosOrdenadosPorPrecioDeManeraDescendente() {
		TreeSet<Producto> resultado = new TreeSet<>(new PrecioDeProductosOrdenadosDeManeraDescendente());

		resultado.addAll(this.productos);

		return resultado;
	}

	public Set<Producto> obtenerProductosOrdenadosPorNombreDeFormaAscendente() {
		TreeSet<Producto> resultado = new TreeSet<>(new NombreDeProductosOrdenadosDeManeraAscendente());

		resultado.addAll(this.productos);

		return resultado;
	}

	public Double obtenerPrecioFinal() {
		Double suma = 0.0;
		for (Producto producto : productos) {
			suma += producto.calcularPrecioFinal();
		}
		return suma;
	}

	public Usuario buscarUsuarioPorCorreo(String email) throws UsuarioNoEncontradoException {
		for (Usuario usuario : this.usuarios) {
			if (usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		throw new UsuarioNoEncontradoException("Usuario no encontrado");
	}

	public String mostrarCatalogo() {
		String mensaje = "===== CATÁLOGO =====\n";

		for (Producto producto : productos) {
			mensaje += producto.obtenerDetalle() + "\n";
		}

		return mensaje;
	}
	
	public List<Orden> obtenerOrdenesDeUsuario(Usuario usuario) {

		List<Orden> resultado = new ArrayList<>();

		for (Orden orden : this.ordenes) {

			if (orden.getUsuario().equals(usuario)) {
				resultado.add(orden);
			}
		}

		return resultado;
	}
	
	
	public Boolean agregarProductoAlCarrito(Usuario usuario, Integer id, Integer cantidad)
			throws ProductoNoEncontradoException, StockInsuficienteException {
		Producto encontrado = this.buscarProductoPorId(id);

		if (encontrado == null) {
			throw new ProductoNoEncontradoException("No se encontro el producto");
		}
		Inventario inventario = encontrado.getInventario();

		if (!inventario.hayStock(cantidad)) {
			throw new StockInsuficienteException("No hay stock suficiente");
		}

		LineaDeCarrito linea = new LineaDeCarrito(encontrado, cantidad);

		if (usuario.getCarrito().agregarLinea(linea)) {
			inventario.reservar(cantidad);
			return true;
		}

		return false;

	}
	
	
	
	public Orden generarOrden(String email, String codigo, Estado estado, Envio envio)
			throws CarritoVacioException, UsuarioNoEncontradoException {

		Usuario usuario = this.buscarUsuarioPorCorreo(email);

		Carrito carrito = usuario.getCarrito();

		if (carrito.getLineas().isEmpty()) {
			throw new CarritoVacioException();
		}

		Orden orden = new Orden(usuario, codigo, estado, envio);

		for (LineaDeCarrito lineaCarrito : carrito.getLineas()) {

			LineasDeOrden lineaOrden = new LineasDeOrden(lineaCarrito.getProducto(), lineaCarrito.getCantidad());

			orden.agregarLineaDeOrden(lineaOrden);
		}

		orden.setEstado(Estado.CONFIRMADA);

		this.ordenes.add(orden);

		carrito.vaciar();

		return orden;
	}

		public Map<Categoria, Double> obtenerTotalVendidoPorCategoria() {
			Map<Categoria, Double> resultado = new HashMap<>();

			for (Orden orden : ordenes) {
				if (orden.getEstado().equals(Estado.CONFIRMADA)) {
					List<LineasDeOrden> lineas = orden.getLineas();

					for (LineasDeOrden lineasDeOrden : lineas) {
						Categoria categoria = lineasDeOrden.getProducto().getCategoria();

						if (resultado.containsKey(categoria)) {
							Double suma = resultado.get(categoria);

							suma += lineasDeOrden.obtenerSubtotal();

							resultado.put(categoria, suma);
						} else {
							resultado.put(categoria, lineasDeOrden.obtenerSubtotal());
						}
					}
				}
			}
			return resultado;
		}
	

}
