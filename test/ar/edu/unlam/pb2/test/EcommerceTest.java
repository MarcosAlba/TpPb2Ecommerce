package ar.edu.unlam.pb2.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dominio.Categoria;
import dominio.Inventario;
import dominio.Oferta;
import dominio.ProductoEstandar;
import dominio.ProductoNoEncontradoException;
import dominio.Tienda;
import dominio.Usuario;
import dominio.UsuarioDuplicadoException;
import dominio.Producto;

public class EcommerceTest {
	@Test
	public void dadoQueExisteUnaTiendaSePuedeAgregarUnProducto() {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null; 
	
		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
		
		Boolean seAgrego = tienda.agregarProducto(producto);
		
		assertTrue(seAgrego);
	}
	
	@Test
	public void dadoQueExisteUnaTiendaSePuedeEliminarUnProducto() {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null; 
	
		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
		
		tienda.agregarProducto(producto); // Agrego el producto para asi poder eliminarlo.
		
		Boolean seElimino = tienda.eliminarProducto(producto);
		
		assertTrue(seElimino);
//		assertEquals(tienda.getProductos().size(), 0); // Funciona de las 2 maneras
	}
	
	@Test (expected = ProductoNoEncontradoException.class)
	public void dadoQueExisteUnaTiendaCuandoBuscoUnProductoPorIdQueNoExisteLanzaUnaExcepcion() throws ProductoNoEncontradoException {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null; 
	
		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
		
		tienda.agregarProducto(producto);
		
		tienda.buscarProductoPorId(2);
		
//		Producto prodBuscado = tienda.buscarProductoPorId(1);
//		assertNull(prodBuscado);
	}
	
	@Test
	public void dadoQueExisteUnaTiendaSePuedeAgregarUnUsuarioCorrectamente() throws UsuarioDuplicadoException {
		Tienda tienda = new Tienda();
		Usuario user = new Usuario("marcosalba@gmail.com", "Marcos", "Albarracin");
		
		Boolean seAgrego = tienda.agregarUsuario(user);
		
		assertTrue(seAgrego);
	}
	
	@Test (expected = UsuarioDuplicadoException.class)
	public void dadoQueExisteUnaTiendaCuandoQuieroAgregarDosUsuariosConElMismoMailLanzaExcepcion() throws UsuarioDuplicadoException {
		Tienda tienda = new Tienda();
		Usuario user = new Usuario("marcosalba@gmail.com", "Marcos", "Albarracin");
		Usuario user2 = new Usuario("marcosalba@gmail.com", "Marcos", "Alba");
		
		tienda.agregarUsuario(user);
		tienda.agregarUsuario(user2);
	}
}
