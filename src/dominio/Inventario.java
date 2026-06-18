package dominio;

public class Inventario {
	private String sku;
	private Integer cantidadDisponible;
	private Integer cantidadReservada;

	public Inventario(String sku, Integer cantidadDisponible, Integer cantidadReservada) {
		this.sku = sku;
		this.cantidadDisponible = cantidadDisponible;
		this.cantidadReservada = cantidadReservada;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getUnidades() {
		return cantidadDisponible;
	}

	public void setUnidades(Integer unidades) {
		this.cantidadDisponible = unidades;
	}

	public Integer getReservadas() {
		return cantidadReservada;
	}

	public void setReservadas(Integer cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}

	public Boolean hayStock(Integer cantidad) {
		Boolean resultado = false;

		if (this.cantidadDisponible >= cantidad) {
			resultado = true;
		}

		return resultado;
	}

	public void reservar(Integer cantidad) {
		this.cantidadDisponible -= cantidad;
		this.cantidadReservada += cantidad;
	}

}
