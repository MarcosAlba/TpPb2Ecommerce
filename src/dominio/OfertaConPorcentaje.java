package dominio;

public class OfertaConPorcentaje implements Oferta{
	private Double porcentaje;
	
	
	public OfertaConPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	
	@Override
	public Double aplicarDescuento(Double precio) {
		return precio - (precio * this.porcentaje / 100);
	}

	@Override
	public String obtenerDescripcion() {
		return null;
	}

}
