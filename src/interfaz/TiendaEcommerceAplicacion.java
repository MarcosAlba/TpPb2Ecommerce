package interfaz;

import java.util.List;
import java.util.Scanner;

import dominio.Carrito;
import dominio.CarritoVacioException;
import dominio.Categoria;
import dominio.Envio;
import dominio.Estado;
import dominio.Inventario;
import dominio.Orden;
import dominio.Producto;
import dominio.ProductoDuplicadoException;
import dominio.ProductoEstandar;
import dominio.ProductoNoEncontradoException;
import dominio.StockInsuficienteException;
import dominio.Tienda;
import dominio.Usuario;
import dominio.UsuarioDuplicadoException;
import dominio.UsuarioNoEncontradoException;

public class TiendaEcommerceAplicacion {
	private static final Scanner teclado = new Scanner(System.in);
    private static Tienda tienda = new Tienda();

    public static void main(String[] args) throws UsuarioNoEncontradoException, ProductoDuplicadoException, UsuarioDuplicadoException {

        cargarDatosIniciales(); 

        int opcion;

        do {

            opcion = menuPublico();

            switch (opcion) {

                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    Usuario usuario = iniciarSesion();

                    if (usuario != null) {
                        menuUsuario(usuario); 
                    }
                    break;
/*
                case 3:
                	menuReportes();
                    break;
                    */

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 3);
    }
    
    
    
    private static int menuPublico() {

        System.out.println("\n===== E-COMMERCE =====");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione opción: ");

        int opcion = teclado.nextInt();
        teclado.nextLine();

        return opcion;
    }
    
    
    
    private static void registrarUsuario() {

        System.out.println("Email:");
        String email = teclado.nextLine();

        System.out.println("Nombre:");
        String nombre = teclado.nextLine();

        System.out.println("Apellido:");
        String apellido = teclado.nextLine();
        
        Carrito carrito = new Carrito();

        Usuario usuario = new Usuario(email, nombre, apellido,carrito);

        try {
            tienda.agregarUsuario(usuario);
            System.out.println("Usuario registrado correctamente");

        } catch (UsuarioDuplicadoException e) {
            System.out.println("El usuario ya existe");
        }
    }
    
    
    
    private static Usuario iniciarSesion() throws UsuarioNoEncontradoException {

        System.out.println("Ingrese email:");
        String email = teclado.nextLine();

        Usuario usuario = tienda.buscarUsuarioPorCorreo(email);

        if (usuario != null) {
            System.out.println("Bienvenido " + usuario.getNombre());
            return usuario;
        }

        System.out.println("Usuario no encontrado");
        return null;
    }
    
    
    
    
    private static void menuUsuario(Usuario usuario) throws UsuarioNoEncontradoException {

        int opcion;

        do {

            System.out.println("\n===== MENÚ DE " + usuario.getNombre().toUpperCase() + " =====");

            System.out.println("1. Ver catálogo");
            System.out.println("2. Buscar producto");
            System.out.println("3. Ver carrito");
            System.out.println("4. Agregar producto al carrito");
            System.out.println("5. Finalizar compra");
            System.out.println("6. Ver mis órdenes");
            System.out.println("7. Cerrar sesión");

            System.out.print("Seleccione opción: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {

                case 1:
                    String catalogo = tienda.mostrarCatalogo();
                    System.out.println(catalogo);
                    break;

                case 2:
                    buscarProducto();
                    break;

                case 3:
                    System.out.println(usuario.getCarrito().obtenerDetalle());
                    break;

                case 4:
                    agregarAlCarrito(usuario);
                    break;

                case 5:
                    finalizarCompra(usuario);
                    break;

                case 6:
                   List<Orden> ordenes =  tienda.obtenerOrdenesDeUsuario(usuario);
                   if (ordenes.isEmpty()) {
                       System.out.println("No posee órdenes registradas");
                   } else {

                       for (Orden orden : ordenes) {
                           System.out.println(orden.obtenerDetalle());
                       }
                   }
                    break;

                case 7:
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 7);
    }
    
    
    
    private static void mostrarOrdenes(Usuario usuario) {
		
	}



    private static void agregarAlCarrito(Usuario usuario) {

    	System.out.println(tienda.mostrarCatalogo());
    	
        System.out.println("ID producto:");
        int id = teclado.nextInt();

        System.out.println("Cantidad:");
        int cantidad = teclado.nextInt();

        try {

            tienda.agregarProductoAlCarrito(usuario, id, cantidad);

            System.out.println("Producto agregado al carrito");

        } catch (StockInsuficienteException e) {

            System.out.println("Stock insuficiente");

        } catch (ProductoNoEncontradoException e) {

            System.out.println(e.getMessage());

        }
    }
    
    private static void buscarProducto() {
    	System.out.println("Ingrese el id del producto");
    	Integer id = teclado.nextInt();
    	try {
    	Producto productoBuscado = tienda.buscarProductoPorId(id);
    	System.out.println(productoBuscado.obtenerDetalle());
    	} catch (ProductoNoEncontradoException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    
    
    
    private static void finalizarCompra(Usuario usuario) throws UsuarioNoEncontradoException {

        try {
            System.out.println("Seleccione envío:");
            System.out.println("1. Gratis");
            System.out.println("2. Estándar");
            System.out.println("3. Express");

            int opcion = teclado.nextInt();

            Envio envio;

            switch (opcion) {
                case 1:
                    envio = new EnvioGratis();
                    break;
                case 2:
                    envio = new EnvioEstandar();
                    break;
                case 3:
                    envio = new EnvioExpress();
                    break;
                default:
                    System.out.println("Opción inválida");
                    return;
            }
            
            
            System.out.println("Ingrese el código: ");
            String codigo = teclado.nextLine();

            System.out.println("Ingrese el estado de la orden: ");
            
            Orden orden = tienda.generarOrden(usuario.getEmail(), codigo, Estado.CONFIRMADA,envio);

            if(orden != null) {
                System.out.println("Compra realizada con éxito");
                System.out.println(orden.obtenerDetalle());
            }
            

        } catch (CarritoVacioException e) {
            System.out.println("El carrito está vacío");
        }
    }
    
    
    
    private static void cargarDatosIniciales() throws ProductoDuplicadoException, UsuarioDuplicadoException {

    	Inventario inv1 = new Inventario("M1", 10, 0);
        Inventario inv2 = new Inventario("T1", 15, 0);
        Inventario inv3 = new Inventario("A1", 8, 0);

        Producto mouse = new ProductoEstandar(
                "Mouse Gamer",
                Categoria.ELECTRONICA,
                5000.0,
                inv1,
                null);

        Producto teclado = new ProductoEstandar(
                "Teclado Mecánico",
                Categoria.ELECTRONICA,
                12000.0,
                inv2,
                null);

        Producto auriculares = new ProductoEstandar(
                "Auriculares",
                Categoria.ELECTRONICA,
                8000.0,
                inv3,
                null);

        tienda.agregarProducto(mouse);
        tienda.agregarProducto(teclado);
        tienda.agregarProducto(auriculares);

        tienda.agregarUsuario(
                new Usuario("admin@gmail.com", "Admin", "System", null));
    }

    
/*    
    private static void menuReportes() {
    	int opcion;
    	
    	do {

            System.out.println("\n===== REPORTES DE TIENDA =====");

            System.out.println("1. Producto más vendido");
            System.out.println("2. Producto más vendido por categoría");
            System.out.println("3. Envío más utilizado");
            System.out.println("4. Volver");

            opcion = teclado.nextInt();
            teclado.nextLine();


            switch(opcion) {

                case 1:
                    mostrarProductoMasVendido();
                    break;

                case 2:
                    mostrarProductoMasVendidoPorCategoria();
                    break;

                case 3:
                    mostrarEnvioMasUtilizado();
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while(opcion != 4);
    	
    }
    
    
    private static void mostrarProductoMasVendido() {

        Producto producto = tienda.obtenerProductoMasVendido();

        if(producto != null) {

            System.out.println("Producto más vendido: "
                               + producto.getNombre());

        } else {

            System.out.println("No existen ventas todavía");
        }
    }
*/    
}
