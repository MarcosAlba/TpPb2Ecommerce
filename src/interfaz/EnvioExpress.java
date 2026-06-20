package interfaz;

import dominio.Envio;

public class EnvioExpress implements Envio {

	private static final Double COSTO = 5000.0;

    @Override
    public Double calcularCosto() {
        return COSTO;
    }

    @Override
    public String getTipo() {
        return "Express";
    }

}
