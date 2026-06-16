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
	public void dadoQueExisteUnaTiendaCuandoAgregoUnProductoAlCarritoSeVerificaSiTieneStock() {
		Tienda tienda = new Tienda();
	    Inventario inventario = new Inventario("0317-0471", 10, 0);
	    Oferta oferta = null;
 
	    Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
	    tienda.agregarProducto(producto);
 
	    Usuario usuario = new Usuario("gianmice@gmail.com", "Gian", "Mice");
 
	    Boolean resultado = tienda.agregarProductoAlCarrito(usuario, producto.getId(), 3);
 
	    assertTrue(resultado);
	}
	
	
	@Test(expected = StockInsuficienteException.class)
	public void DadoQueExisteUnaTiendaCuandoAgregoUnProductoAlCarritoSinStockSuficienteLanzaUnaExcepción() throws StockInsuficienteException {
	    Tienda tienda = new Tienda();
	    Inventario inventario = new Inventario("0317-0471", 2, 0);
	    Oferta oferta = null;
 
	    Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
	    tienda.agregarProducto(producto);
 
	    Usuario usuario = new Usuario("gianmice@gmail.com", "Gian", "Mice");
 
	    tienda.agregarProductoAlCarrito(usuario, producto.getId(), 10);
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
	
	
	@Test
	public void dadoQueExisteUnaTiendaCuandoElUsuarioAgregaProductosASuCarritoYTomaLasLineasDeProductoSeGeneraUnaNuevOrdenYLasLineasDeCarritoFormanParteDeLaLineaDeOrdenSeGuardaLaOrdenEnLaTiendaYElCarritoSeLimpiaYElInventarioSeTerminaDeActualizar() {
		Tienda tienda = new Tienda();
		
		Carrito carrito = new Carrito();
		
		Inventario inventario = new Inventario("RA001",100,20);
		
		Oferta ofertaNueva = new OfertaConPorcentaje(5.0);
		
		Producto remera = new ProductoEstandar("remera adidas",Categoria.DEPORTES,27345.0,inventario,ofertaNueva);
		
		LineaDeCarrito linea1 = new LineaDeCarrito();
		
		carrito.agregarLinea(linea1);
		
		Usuario usuario = new Usuario("martin@gmail.com","Martin","Diaz",carrito);
		
		Orden ordenGenerada = tienda.finalizarCompra(usuario,Estado.CONFIRMADA);
		
		assertNotNull(ordenGenerada);
		
		assertEquals(usuario,ordenGenerada.getUsuario());
		
		
		assertEquals(1,tienda.getCantidadDeOrdenes());
		
		assertEquals(1,ordenGenerada.getCantidadDeLineas());
		
		LineaDeOrden lineaDeOrden = ordenGenerada.getLineaPorIndice(0);
		
		assertEquals(remera,lineaDeOrden.getProducto());
		
		assertEquals(3,lineaDeOrden.getCantidad());
		
		assertEquals(0, carrito.getCantidadLineas());
		
		assertEquals(0, carrito.getCantidadLineas());
		
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
	
	
	@Test
	public void dadoQueExisteUnaTiendaCuandoConsultoLosProductosObtengoElTotalVendidoPorCadaCategoria() {
		Tienda tienda = new Tienda();
	
		Usuario usuario = new Usuario("juan@gmail.com","Juan","perez",null);
		
		tienda.agregarUsuario(usuario);
		
		Usuario usuario2 = new Usuario("damian@gmail.com","Damian","Ramirez",null);
		
		tienda.agregarUsuario(usuario2);
		
		
		Usuario usuario3 = new Usuario("ana@gmail.com","Ana","Lopez",null);
		
		Inventario inventario = new Inventario("P1",100,10);
		
		Oferta nueva = new OfertaConPorcentaje(20.0);
		
		Producto pantalon = new ProductoEstandar("Pantalon deportivo",Categoria.DEPORTES,69900.0,inventario,nueva);
		
		Inventario inventario2 = new Inventario("Z11",30000,2000);
		
		Oferta nueva2 = new OfertaConPorcentaje(10.0);
		
		Producto zapatillas = new ProductoPersonalizado("Zapatillas running",Categoria.DEPORTES,51300.0,inventario,nueva2);
		
		Inventario inventario3 = new Inventario("G1001",5000,200);
		
		Oferta nueva3 = new OfertaConPorcentaje(15.0);
		
		Producto guantes = new ProductoEstandar("Guantes de arquero",Categoria.DEPORTES,29779.0,inventario3,nueva3);
		
		Inventario inventario4 = new Inventario("D002",1000,20);
		
		Oferta nueva4 = new OfertaConPorcentaje(46.0);
		
		Producto dispenser = new ProductoPersonalizado("Dispenser de agua",Categoria.HOGAR,181720.0, inventario4,nueva4);

		Inventario inventario5 = new Inventario("TE001",3000,2000);
		
		Producto tender = new ProductoPersonalizado("Tender vertical para ropa",Categoria.HOGAR,75248.0,inventario5,null);
		
		Inventario inventario6 = new Inventario("MA00",2000,500);
		
		Producto mate = new ProductoEstandar("Mate justo imperial",Categoria.HOGAR,11000.0,inventario6, null);
		
		Inventario inventario7 = new Inventario("CV002",1000,100);
		
		Oferta nueva6 = new OfertaConPorcentaje(48.0);
		
		Producto colchon = new ProductoEstandar("Colchon viggo",Categoria.HOGAR,626289.0,inventario7,nueva6);
		
		Inventario inventario8 = new Inventario("COO3",3000,200);
		
		Oferta nueva7 = new OfertaConPorcentaje(54.0);
		
		Producto cooler = new ProductoPersonalizado("kit 3 coolers",Categoria.ELECTRONICA,27298.0,inventario8,nueva7);
		
		Inventario inventario9 = new Inventario("VT1000",3000,2000);
		
		Oferta nueva8 = new OfertaConPorcentaje(52.0);
		
		Producto ventilador = new ProductoEstandar("Ventilador de techo",Categoria.ELECTRONICA,96485.0,inventario9,nueva8);
		
		LineaDeCarrito linea1 = new LineaDeOrden(pantalon,40,pantalon.calcularPrecioFinal());
		LineaDeCarrito linea2 = new LineaDeOrden(zapatillas,100,zapatillas.calcularPrecioFinal());
		LineaDeCarrito linea3 = new LineaDeOrden(guantes,500,guantes.calcularPrecioFinal());
		LineaDeCarrito linea4 = new LineaDeOrden(dispenser,100,dispenser.calcularPrecioFinal());
		LineaDeCarrito linea5 = new LineaDeOrden(tender,30,tender.calcularPrecioFinal());
		LineaDeCarrito linea6 = new LineaDeOrden(mate,50,mate.calcularPrecioFinal());
		LineaDeCarrito linea7 = new LineaDeOrden(colchon,100,colchon.calcularPrecioFinal());
		LineaDeCarrito linea8 = new LineaDeOrden(cooler,300,cooler.calcularPrecioFinal());
		LineaDeCarrito linea9 = new LineaDeOrden(ventilador,200,ventilador.calcularPrecioFinal());
		
		
		Orden orden1 = new Orden(usuario,Estado.CONFIRMADA);
		
		orden1.agregarLineaDeOrden(linea1);
		orden1.agregarLineaDeOrden(linea2);
		orden1.agregarLineaDeOrden(linea3);
		
		Orden orden2 = new Orden(usuario2,Estado.CONFIRMADA);
		
		orden2.agregarLineaDeOrden(linea4);
		orden2.agregarLinea(linea5);
		orden2.agregarLinea(linea6);
		
		orden3.agregarLinea(linea7);
		orden3.agregarLinea(linea8);
		orden3.agregarLinea(linea9);
		
		Orden orden3 = new Orden(usuario3,Estado.CONFIRMADA);

		
		tienda.agregarOrden(orden1);
		tienda.agregarOrden(orden2);
		tienda.agregarOrden(orden3);
		
	}
}
