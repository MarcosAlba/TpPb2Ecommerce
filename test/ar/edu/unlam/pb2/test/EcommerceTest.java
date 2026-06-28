package ar.edu.unlam.pb2.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dominio.Carrito;
import dominio.CarritoVacioException;
import dominio.Categoria;
import dominio.Envio;
import dominio.Estado;
import dominio.Inventario;
import dominio.LineaDeCarrito;
import dominio.LineasDeOrden;
import dominio.Oferta;
import dominio.OfertaConPorcentaje;
import dominio.Opcion;
import dominio.Orden;
import dominio.ProductoEstandar;
import dominio.ProductoNoEncontradoException;
import dominio.ProductoPersonalizado;
import dominio.StockInsuficienteException;
import dominio.Tienda;
import dominio.Usuario;
import dominio.UsuarioDuplicadoException;
import dominio.UsuarioNoEncontradoException;
import interfaz.EnvioEstandar;
import interfaz.EnvioExpress;
import interfaz.EnvioGratis;
import dominio.Producto;

public class EcommerceTest {
	@Test //1
	public void dadoQueExisteUnaTiendaSePuedeAgregarUnProducto() {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null;

		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);

		Boolean seAgrego = tienda.agregarProducto(producto);

		assertTrue(seAgrego);
	}

	@Test //2
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

	@Test(expected = ProductoNoEncontradoException.class) //3
	public void dadoQueExisteUnaTiendaCuandoBuscoUnProductoPorIdQueNoExisteLanzaUnaExcepcion()
			throws ProductoNoEncontradoException {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null;

		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);

		tienda.agregarProducto(producto);

		tienda.buscarProductoPorId(1);

//		Producto prodBuscado = tienda.buscarProductoPorId(1);
//		assertNull(prodBuscado);
	}

	@Test //4
	public void dadoQueExisteUnaTiendaSePuedeAgregarUnUsuarioCorrectamente() throws UsuarioDuplicadoException {
		Tienda tienda = new Tienda();
		Usuario user = new Usuario("marcosalba@gmail.com", "Marcos", "Albarracin", null);

		Boolean seAgrego = tienda.agregarUsuario(user);

		assertTrue(seAgrego);
	}

	@Test(expected = UsuarioDuplicadoException.class) //5
	public void dadoQueExisteUnaTiendaCuandoQuieroAgregarDosUsuariosConElMismoMailLanzaExcepcion()
			throws UsuarioDuplicadoException {
		Tienda tienda = new Tienda();
		Usuario user = new Usuario("marcosalba@gmail.com", "Marcos", "Albarracin", null);
		Usuario user2 = new Usuario("marcosalba@gmail.com", "Marcos", "Alba", null);

		tienda.agregarUsuario(user);
		tienda.agregarUsuario(user2);
	}

	@Test //6
	public void dadoQueExisteUnaTiendaCuandoAgregoUnProductoAlCarritoSeVerificaSiTieneStock()
			throws ProductoNoEncontradoException, UsuarioDuplicadoException, StockInsuficienteException {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Oferta oferta = null;

		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
		tienda.agregarProducto(producto);

		Carrito carrito = new Carrito();

		Usuario usuario = new Usuario("gianmice@gmail.com", "Gian", "Mice", carrito);

		tienda.agregarUsuario(usuario);

		Boolean resultado = tienda.agregarProductoAlCarrito(usuario, producto.getId(), 3);

		assertTrue(resultado);
	}

	@Test(expected = StockInsuficienteException.class) //7
	public void dadoQueExisteUnaTiendaCuandoAgregoUnProductoAlCarritoSinStockSuficienteLanzaUnaExcepción()
			throws StockInsuficienteException, UsuarioDuplicadoException, ProductoNoEncontradoException {
		Tienda tienda = new Tienda();
		Inventario inventario = new Inventario("0317-0471", 2, 0);
		Oferta oferta = null;

		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, oferta);
		tienda.agregarProducto(producto);

		Carrito carrito = new Carrito();

		Usuario usuario = new Usuario("gianmice@gmail.com", "Gian", "Mice", carrito);

		tienda.agregarUsuario(usuario);

		tienda.agregarProductoAlCarrito(usuario, producto.getId(), 10);
	}

	@Test
	public void dadoQueExisteUnaTiendaCuandoConsultoSusProductosObtengoUnaColeccionOrdenadaDeManeraAscendente() {
		Tienda tienda = new Tienda();

		Inventario inventario = new Inventario("Mouse001", 20, 5);

		Producto productoEstandar = new ProductoEstandar("Mouse", Categoria.ELECTRONICA, 30000.0, inventario, null);

		Inventario inventario2 = new Inventario("Silla002", 100, 10);

		Producto productoPersonalizado = new ProductoPersonalizado("Silla escritorio", Categoria.HOGAR, 173000.0,
				inventario2, null);

		Inventario inventario3 = new Inventario("Camiseta003", 1000, 50);

		Producto camiseta = new ProductoEstandar("Camiseta Seleccion Argentian", Categoria.DEPORTES, 100000.0,
				inventario3, null);

		tienda.agregarProducto(productoEstandar);
		tienda.agregarProducto(productoPersonalizado);
		tienda.agregarProducto(camiseta);

		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorNombreDeManeraAscendente();

		List<Producto> lista = new ArrayList<>(resultado);

		assertEquals(productoEstandar.getPrecioBase(), lista.get(0).getPrecioBase());
		assertEquals(camiseta.getPrecioBase(), lista.get(1).getPrecioBase());
		assertEquals(productoPersonalizado.getPrecioBase(), lista.get(2).getPrecioBase());
	}

	@Test
	public void dadoQueExisteUnaTiendaCuandoElPrecioDeLosProductosEstosSeOrdenanDeManeraDescendente() {
		Tienda tienda = new Tienda();

		Inventario inventario = new Inventario("Gorra1001", 30, 5);

		Producto gorra = new ProductoEstandar("Gorra", Categoria.DEPORTES, 43000.0, inventario, null);

		tienda.agregarProducto(gorra);

		Inventario inventario2 = new Inventario("KingOfTheKongo003", 1500, 300);

		Producto buzoOverSize = new ProductoPersonalizado("Buzo king of the kongo", Categoria.DEPORTES, 144990.0,
				inventario2, null);

		tienda.agregarProducto(buzoOverSize);

		Inventario inventario3 = new Inventario("MTA001", 3000, 1000);

		Producto bateriaCocina = new ProductoPersonalizado("Bateria cocina mta 5 piezas", Categoria.HOGAR, 75225.0,
				inventario3, null);

		tienda.agregarProducto(bateriaCocina);

		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorPrecioDeManeraDescendente();

		List<Producto> lista = new ArrayList<>(resultado);

		assertEquals(buzoOverSize.getPrecioBase(), lista.get(0).getPrecioBase());
		assertEquals(bateriaCocina.getPrecioBase(), lista.get(1).getPrecioBase());
		assertEquals(gorra.getPrecioBase(), lista.get(2).getPrecioBase());
	}

	@Test
	public void dadoQueExisteUnaTiendaCuandoConsultoElNombreDeLosProductosSeOrdenanDeFormaAscendente() {
		Tienda tienda = new Tienda();

		Inventario inventario = new Inventario("M004", 100, 20);

		Producto microfono = new ProductoEstandar("Microfono", Categoria.ELECTRONICA, 32999.0, inventario, null);

		tienda.agregarProducto(microfono);

		Inventario inventario2 = new Inventario("CO104", 3000, 50);

		Producto colchon = new ProductoPersonalizado("Colchon", Categoria.HOGAR, 209999.0, inventario2, null);

		tienda.agregarProducto(colchon);

		Inventario inventario3 = new Inventario("L01", 10, 5);

		Producto libro = new ProductoEstandar("Padre rico y padre pobre", Categoria.LIBROS, 70000.0, inventario3, null);

		tienda.agregarProducto(libro);

		Set<Producto> resultado = tienda.obtenerProductosOrdenadosPorNombreDeFormaAscendente();

		List<Producto> lista = new ArrayList<>(resultado);

		assertEquals(lista.get(0).getNombre(), colchon.getNombre());
		assertEquals(lista.get(1).getNombre(), microfono.getNombre());
		assertEquals(lista.get(2).getNombre(), libro.getNombre());
	}
	
	@Test
	public void dadoQueExisteUnaTiendaCuandoElUsuarioAgregaProductosASuCarritoYTomaLasLineasDeProductoSeGeneraUnaNuevOrdenYLasLineasDeCarritoFormanParteDeLaLineaDeOrdenSeGuardaLaOrdenEnLaTiendaYElCarritoSeLimpiaYElInventarioSeTerminaDeActualizar() throws UsuarioDuplicadoException, CarritoVacioException, UsuarioNoEncontradoException {
		Tienda tienda = new Tienda();
		
		Carrito carrito = new Carrito();
		
		Inventario inventario = new Inventario("RA001",100,20);
		
		Oferta ofertaNueva = new OfertaConPorcentaje(5.0);
		
		Producto remera = new ProductoEstandar("remera adidas",Categoria.DEPORTES,27345.0,inventario,ofertaNueva);
		
		LineaDeCarrito linea1 = new LineaDeCarrito(remera,3);
		
		carrito.agregarLinea(linea1);
		
		Usuario usuario = new Usuario("martin@gmail.com","Martin","Diaz",carrito);
		
		tienda.agregarUsuario(usuario);
		
		Envio envioEstandar = new EnvioEstandar();
		
		Orden ordenGenerada = tienda.generarOrden("martin@gmail.com","ORD001",Estado.CONFIRMADA,envioEstandar);
		
		assertNotNull(ordenGenerada);
		
		assertEquals(usuario,ordenGenerada.getUsuario());
		
		assertEquals(1,tienda.getOrdenes().size());
		
		assertEquals(1,ordenGenerada.getLineas().size());
		
		LineasDeOrden lineaDeOrden = ordenGenerada.getLineas().get(0);
		
		assertEquals(remera,lineaDeOrden.getProducto());
		
		assertEquals(3,lineaDeOrden.getCantidad(),0.01);
		
		assertEquals(Estado.CONFIRMADA,ordenGenerada.getEstado());
		
		
		assertEquals(envioEstandar.getTipo(),ordenGenerada.getEnvio().getTipo());
		
		inventario.reservar(linea1.getCantidad());
		
		assertEquals(97,inventario.getUnidades(),0.01);
	}
	
	 

	// Tests nuevos para probar el precioFinal con oferta

	@Test
	public void dadoQueExisteUnProductoPersonalizadoConUnaOfertaDelVeintePorCientoSeDebePoderObtenerSuPrecioFinal() {
		Tienda tienda = new Tienda();

		Inventario inventario = new Inventario("Cinta001", 1000, 100);

		Oferta oferta = new OfertaConPorcentaje(20.0);

		Producto cintaDeCorrer = new ProductoPersonalizado("Cinta de correr electrica", Categoria.DEPORTES, 615553.0,
				inventario, oferta);

		tienda.agregarProducto(cintaDeCorrer);

		Opcion motorPremium = new Opcion("motor premium", 50000.0);

		Opcion pantallaTactil = new Opcion("pantalla tactil", 30000.0);

		ProductoPersonalizado productoPersonalizadoCinta = (ProductoPersonalizado) cintaDeCorrer;

		productoPersonalizadoCinta.agregarOpcion(motorPremium);
		productoPersonalizadoCinta.agregarOpcion(pantallaTactil);

		Double precioTotal = tienda.obtenerPrecioFinal();

		assertEquals(556442.4, precioTotal, 0.01);
	}

	@Test //14
	public void dadoQueExisteUnaTiendaCuandoConsultoLosProductosObtengoElTotalVendidoPorCadaCategoria()
			throws UsuarioDuplicadoException, CarritoVacioException, UsuarioNoEncontradoException {
		Tienda tienda = new Tienda();

		Usuario usuario = new Usuario("juan@gmail.com", "Juan", "Perez", new Carrito());

		Usuario usuario2 = new Usuario("damian@gmail.com", "Damian", "Ramirez", new Carrito());

		Usuario usuario3 = new Usuario("ana@gmail.com", "Ana", "Lopez", new Carrito());

		tienda.agregarUsuario(usuario);
		tienda.agregarUsuario(usuario2);
		tienda.agregarUsuario(usuario3);

		Inventario inventarioPantalon = new Inventario("D1", 100, 10);

		Oferta ofertaPantalon = new OfertaConPorcentaje(20.0);

		Producto pantalon = new ProductoEstandar("Pantalon deportivo", Categoria.DEPORTES, 69900.0, inventarioPantalon,
				ofertaPantalon);

		Inventario inventarioZapatillas = new Inventario("D2", 30000, 2000);

		Oferta ofertaZapatillas = new OfertaConPorcentaje(10.0);

		Producto zapatillas = new ProductoPersonalizado("Zapatillas running", Categoria.DEPORTES, 51300.0,
				inventarioZapatillas, ofertaZapatillas);

		Inventario inventarioGuantes = new Inventario("D3", 5000, 200);

		Oferta ofertaGuantes = new OfertaConPorcentaje(15.0);

		Producto guantes = new ProductoEstandar("Guantes de arquero", Categoria.DEPORTES, 29779.0, inventarioGuantes,
				ofertaGuantes);

		Inventario inventarioDispenser = new Inventario("H1", 1000, 20);

		Oferta ofertaDispenser = new OfertaConPorcentaje(46.0);

		Producto dispenser = new ProductoPersonalizado("Dispenser de agua", Categoria.HOGAR, 181720.0,
				inventarioDispenser, ofertaDispenser);

		Inventario inventarioTender = new Inventario("H2", 3000, 2000);

		Producto tender = new ProductoPersonalizado("Tender vertical para ropa", Categoria.HOGAR, 75248.0,
				inventarioTender, null);

		Inventario inventarioMate = new Inventario("H3", 2000, 500);

		Producto mate = new ProductoEstandar("Mate justo imperial", Categoria.HOGAR, 11000.0, inventarioMate, null);

		Inventario inventario7 = new Inventario("CV002", 1000, 100);
		Oferta oferta5 = new OfertaConPorcentaje(48.0);

		Producto colchon = new ProductoEstandar("Colchon viggo", Categoria.HOGAR, 626289.0, inventario7, oferta5);

		Inventario inventarioCooler = new Inventario("E1", 3000, 200);

		Oferta ofertaCooler = new OfertaConPorcentaje(54.0);

		Producto cooler = new ProductoPersonalizado("Kit 3 coolers", Categoria.ELECTRONICA, 27298.0, inventarioCooler,
				ofertaCooler);

		Inventario inventarioVentilador = new Inventario("E2", 3000, 2000);

		Oferta ofertaVentilador = new OfertaConPorcentaje(52.0);

		Producto ventilador = new ProductoEstandar("Ventilador de techo", Categoria.ELECTRONICA, 96485.0,
				inventarioVentilador, ofertaVentilador);

		tienda.agregarProducto(pantalon);
		tienda.agregarProducto(zapatillas);
		tienda.agregarProducto(guantes);

		tienda.agregarProducto(dispenser);
		tienda.agregarProducto(tender);
		tienda.agregarProducto(mate);
		tienda.agregarProducto(colchon);

		tienda.agregarProducto(cooler);
		tienda.agregarProducto(ventilador);

		usuario.getCarrito().agregarLinea(new LineaDeCarrito(pantalon, 40));

		usuario.getCarrito().agregarLinea(new LineaDeCarrito(zapatillas, 100));

		usuario.getCarrito().agregarLinea(new LineaDeCarrito(guantes, 500));

		usuario2.getCarrito().agregarLinea(new LineaDeCarrito(dispenser, 100));

		usuario2.getCarrito().agregarLinea(new LineaDeCarrito(tender, 30));

		usuario2.getCarrito().agregarLinea(new LineaDeCarrito(mate, 50));

		usuario3.getCarrito().agregarLinea(new LineaDeCarrito(colchon, 100));

		usuario3.getCarrito().agregarLinea(new LineaDeCarrito(cooler, 300));

		usuario3.getCarrito().agregarLinea(new LineaDeCarrito(ventilador, 200));

		Envio envio = new EnvioEstandar();

		tienda.generarOrden(usuario.getEmail(), "ORD001", Estado.PENDIENTE, envio);

		tienda.generarOrden(usuario2.getEmail(), "ORD002", Estado.PENDIENTE, envio);

		tienda.generarOrden(usuario3.getEmail(), "ORD003", Estado.PENDIENTE, envio);

		Map<Categoria, Double> resultado = tienda.obtenerTotalVendidoPorCategoria();


	    
	    Double totalVendidoPorElectronica = resultado.get(Categoria.ELECTRONICA);
	    
	    Double totalVendidoPorDeportes = resultado.get(Categoria.DEPORTES);
	    
	    Double totalVendidoPorHogar = resultado.get(Categoria.HOGAR);
	    
	    assertEquals(totalVendidoPorElectronica,(Double) 1.3029684E7);
	    assertEquals(totalVendidoPorDeportes,(Double)1.9509875E7);
	    assertEquals(totalVendidoPorHogar, (Double) 4.5187348E7);

	}
	
	@Test // 15
	public void  DadoUnProductoConUnaOfertaPorcentualAsociadaCalcularPrecioFinalDevuelveElPrecioConElDescuentoPorCentajeAplicado() {
		
		Inventario inventario = new Inventario("SKU123", 10, 0); // stock inicial
        Oferta oferta = new OfertaConPorcentaje(20.0); // 20% de descuento
        Producto producto = new ProductoEstandar("Silla", Categoria.HOGAR, 1000.0, inventario, oferta);

        
        Double precioFinal = producto.calcularPrecioFinal();

        assertEquals(800.0, precioFinal, 0.01); 
	}
	
	@Test // 17
	public void  DadoUnProductoSinOfertaAsociadaCalcularPrecioFinalDevuelveElPrecioSinNingunDescuento() {
		
		
	    Inventario inventario = new Inventario("SKU124", 5, 0);
	    Producto producto = new ProductoEstandar("Mesa", Categoria.HOGAR, 2000.0, inventario, null);

	    
	    Double precioFinal = producto.calcularPrecioFinal();
	    inventario.reservar(2);

	 
	    assertEquals(2000.0, precioFinal, 0.01);
	    assertEquals(Integer.valueOf(3), inventario.getUnidades());
	    assertEquals(Integer.valueOf(2), inventario.getReservadas());

	    String detalle = producto.obtenerDetalle();
	    assertTrue(detalle.contains(producto.calcularPrecioFinal().toString()));
	
	}
	
	

	@Test // 19
	public void dadoQueExisteUnaOrdenConEnvioExpressElCostoTotalDeLaOrdenIncluyeElCostoDelEnvio() {
		Carrito carrito = new Carrito();

		Usuario usuario = new Usuario("marquitosalba2018@gmail.com", "Marcos", "Alba", carrito);
		Envio envio = new EnvioExpress();

		Orden orden = new Orden(usuario, "Orden 01", Estado.PENDIENTE, envio);
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, null);

		LineasDeOrden linea = new LineasDeOrden(producto, 1);

		orden.agregarLineaDeOrden(linea);

		// Raqueta vale 550, Envio 5000 = 5550;
		Double totalEsperado = 5550.0;
		Double totalObtenido = orden.calcularTotal();

		assertEquals(totalEsperado, totalObtenido);
	}

	@Test // 20
	public void dadoQueExisteUnaOrdenConEnvioGratuitoElCostoDeEnvioEsCeroYNoAfectaElTotalDeLaOrden() {
		Carrito carrito = new Carrito();

		Usuario usuario = new Usuario("marquitosalba2018@gmail.com", "Marcos", "Alba", carrito);
		Envio envio = new EnvioGratis();

		Orden orden = new Orden(usuario, "Orden 01", Estado.PENDIENTE, envio);
		Inventario inventario = new Inventario("0317-0471", 10, 0);
		Producto producto = new ProductoEstandar("Raqueta de Tenis", Categoria.DEPORTES, 550.00, inventario, null);

		LineasDeOrden linea = new LineasDeOrden(producto, 1);

		orden.agregarLineaDeOrden(linea);
		
		// Raqueta = 550, Envio = 0, Total = 550;
		Double totalEsperado = 550.0;
		Double totalObtenido = orden.calcularTotal();
		
		assertEquals(totalEsperado, totalObtenido);
	}

}
