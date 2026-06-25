package dominio;

public class OfertaMontoFijo implements Oferta {
    private Double montoDescuento;

    public OfertaMontoFijo(Double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    @Override
    public Double aplicarDescuento(Double precio) {
        return Math.max(0.0, precio - montoDescuento);
    }

    @Override
    public String obtenerDescripcion() {
        return "Descuento de $" + montoDescuento;
    }
}