package dominio;

import java.util.ArrayList;
import java.util.List;

public class Orden {
	private Usuario usuario;
	private String codigo;
	private Estado estado;
	private List<LineasDeOrden> lineas;
	private Envio envio;
	
	public Orden(Usuario usuario,String codigo, Estado estado,Envio envio) {
		this.usuario = usuario;
		this.codigo = codigo;
		this.estado = Estado.PENDIENTE;
		this.lineas = new ArrayList<>();
		this.envio = envio;
	}
	
	public boolean agregarLineaDeOrden(LineasDeOrden linea) {
		return this.lineas.add(linea);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	public Double obtenerSubtotalProductos() {

	    Double subtotal = 0.0;

	    for (LineasDeOrden linea : this.lineas) {
	        subtotal += linea.obtenerSubtotal();
	    }

	    return subtotal;
	}
	
	
	public Double calcularTotal() {

	    return obtenerSubtotalProductos()
	            + this.envio.calcularCosto();
	}
	
	public String obtenerDetalle() {

	    String detalle = "";

	    detalle += "===== ORDEN =====\n";
	    detalle += "Codigo: " + this.codigo + "\n";
	    detalle += "Cliente: " + this.usuario.getNombre() + "\n";
	    detalle += "Estado: " + this.estado + "\n";
	    detalle += "Envio: " + this.envio.getTipo() + "\n\n";

	    detalle += "Productos:\n";

	    for (LineasDeOrden linea : this.lineas) {
	        detalle += linea + "\n";
	    }

	    detalle += "\nSubtotal: $" + obtenerSubtotalProductos();
	    detalle += "\nCosto envio: $" + this.envio.calcularCosto();
	    detalle += "\nTOTAL: $" + calcularTotal();

	    return detalle;
	}
}
