package ar.edu.unlam.pb2.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dominio.Categoria;
import dominio.Inventario;
import dominio.Oferta;
import dominio.OfertaConPorcentaje;
import dominio.Opcion;
import dominio.ProductoEstandar;
import dominio.ProductoNoEncontradoException;
import dominio.ProductoPersonalizado;
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
	
	
	@Test
	public void dadoQueExisteUnaTiendaCuandoConsultoSusProductosObtengoUnaColeccionOrdenadaDeManeraAscendente() {
		Tienda tienda = new Tienda();
		
		Inventario inventario = new Inventario("Mouse001",20,5);
		
		
		Producto productoEstandar = new ProductoEstandar("Mouse",Categoria.ELECTRONICA,30000.0,inventario,null);
		
		
		Inventario inventario2 = new Inventario("Silla002",100,10);
		
		Producto productoPersonalizado = new ProductoPersonalizado("Silla escritorio", Categoria.HOGAR,173000.0,inventario2,null);
		
		
		Inventario inventario3 = new Inventario("Camiseta003",1000,50);
	
		Producto camiseta = new ProductoEstandar("Camiseta Seleccion Argentian",Categoria.DEPORTES,100000.0,inventario3,null);
		
		tienda.agregarProducto(productoEstandar);
		tienda.agregarProducto(productoPersonalizado);
		tienda.agregarProducto(camiseta);
		
		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorNombreDeManeraAscendente();
		
		List<Producto> lista = new ArrayList<>(resultado);

		assertEquals(productoEstandar.getPrecioBase(),lista.get(0).getPrecioBase());
		assertEquals(camiseta.getPrecioBase(),lista.get(1).getPrecioBase());
		assertEquals(productoPersonalizado.getPrecioBase(),lista.get(2).getPrecioBase());
	}
	
	
	@Test 
	public void dadoQueExisteUnaTiendaCuandoElPrecioDeLosProductosEstosSeOrdenanDeManeraDescendente() {
		Tienda tienda = new Tienda();
		
		Inventario inventario = new Inventario("Gorra1001",30,5);
		
		Producto gorra = new ProductoEstandar("Gorra",Categoria.DEPORTES,43000.0,inventario,null);
		
		tienda.agregarProducto(gorra);
		
		Inventario inventario2 = new Inventario("KingOfTheKongo003",1500,300);
		
		Producto buzoOverSize = new ProductoPersonalizado("Buzo king of the kongo",Categoria.DEPORTES,144990.0,inventario2,null);
		
		tienda.agregarProducto(buzoOverSize);
		
		Inventario inventario3 = new Inventario("MTA001",3000,1000);
		
		Producto bateriaCocina = new ProductoPersonalizado("Bateria cocina mta 5 piezas",Categoria.HOGAR,75225.0,inventario3,null);
		
		tienda.agregarProducto(bateriaCocina);
		
		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorPrecioDeManeraDescendente();
		
		List<Producto> lista = new ArrayList<>(resultado);
		
		assertEquals(buzoOverSize.getPrecioBase(),lista.get(0).getPrecioBase());
		assertEquals(bateriaCocina.getPrecioBase(),lista.get(1).getPrecioBase());
		assertEquals(gorra.getPrecioBase(),lista.get(2).getPrecioBase());
	}
	
	
	@Test
	public void dadoQueExisteUnaTiendaCuandoConsultoElNombreDeLosProductosSeOrdenanDeFormaAscendente() {
		Tienda tienda = new Tienda();
		
		Inventario inventario = new Inventario("M004",100,20);
		
		Producto microfono = new ProductoEstandar("Microfono",Categoria.ELECTRONICA,32999.0,inventario,null);
		
		tienda.agregarProducto(microfono);
		
		Inventario inventario2 = new Inventario("CO104",3000,50);
		
		Producto colchon = new ProductoPersonalizado("Colchon",Categoria.HOGAR,209999.0,inventario2,null);
		
		tienda.agregarProducto(colchon);
		
		Inventario inventario3 = new Inventario("L01",10,5);
		
		Producto libro = new ProductoEstandar("Padre rico y padre pobre",Categoria.LIBROS,70000.0,inventario3,null);
		
		tienda.agregarProducto(libro);
		
		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorNombreDeFormaAscendente();
		
		
		List<Producto> lista = new ArrayList<>(resultado);
		
		assertEquals(lista.get(0).getNombre(),colchon.getNombre());
		assertEquals(lista.get(1).getNombre(),microfono.getNombre());
		assertEquals(lista.get(2).getNombre(),libro.getNombre());
	}
	
	
	//Tests nuevos para probar el precioFinal con oferta
	@Test
	public void dadoQueExisteUnProductoPersonalizadoConUnaOfertaDelVeintePorCientoSeDebePoderObtenerSuPrecioFinal() {
		Tienda tienda = new Tienda();
		
		Inventario inventario = new Inventario("Cinta001",1000,100);
		
		Oferta oferta = new OfertaConPorcentaje(20.0);
		
		Producto cintaDeCorrer = new ProductoPersonalizado("Cinta de correr electrica",Categoria.DEPORTES,615553.0,inventario,oferta);
		
		tienda.agregarProducto(cintaDeCorrer);
		
		Opcion motorPremium = new Opcion("motor premium",50000.0);
		
		Opcion pantallaTactil = new Opcion("pantalla tactil",30000.0);
		
		ProductoPersonalizado productoPersonalizadoCinta = (ProductoPersonalizado) cintaDeCorrer;
	
		productoPersonalizadoCinta.agregarOpcion(motorPremium);
		productoPersonalizadoCinta.agregarOpcion(pantallaTactil);
		
		Double precioTotal = tienda.obtenerPrecioFinal();
		
		assertEquals(556442.4,precioTotal,0.01);
	}
	
}
