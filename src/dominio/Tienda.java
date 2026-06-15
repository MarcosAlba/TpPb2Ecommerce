package dominio;

import java.util.HashSet;
import java.util.Set;

public class Tienda {
	private Set<Usuario> usuarios;
	private Set<Producto> productos;
	
	public Tienda() {
		this.usuarios = new HashSet<>();
		this.productos = new HashSet<>();
	}
	
	public boolean agregarUsuario(Usuario usuario) throws UsuarioDuplicadoException {
	    try {
	        this.buscarUsuarioPorCorreo(usuario.getEmail());

	        throw new UsuarioDuplicadoException("El usuario ya existe");

	    } catch (UsuarioNoEncontradoException e) {

	        return this.usuarios.add(usuario);
	    }
	}
	
	
	public Usuario buscarUsuarioPorCorreo(String email) throws UsuarioNoEncontradoException {
		for (Usuario usuario : usuarios) {
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

	public boolean agregarProducto(Producto producto) throws ProductoDuplicadoException {
		try {
			this.buscarProductoPorId(producto.getId());
			
			throw new ProductoDuplicadoException("El producto ya existe");
		} catch (ProductoNoEcontradoException e) {
			return this.productos.add(producto);
		}
	}

	private Producto buscarProductoPorId(Integer id) throws ProductoNoEcontradoException {
		for (Producto producto : productos) {
			if(producto.getId().equals(id)) {
				return producto;
			}
		}
		throw new ProductoNoEcontradoException("Producto no encontrado");
	}
}
