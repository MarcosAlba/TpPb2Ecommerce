package dominio;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Tienda {
	private Set<Usuario> usuarios;
	private Set<Producto> productos;

	public Tienda() {
		this.usuarios = new HashSet<>();
		this.productos = new HashSet<>();
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
			if(usuario.getEmail().equals(email)) {
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

}
