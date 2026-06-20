package interfaz;

import dominio.Envio;

public class EnvioEstandar implements Envio {

	 private static final Double COSTO = 2500.0;

	    @Override
	    public Double calcularCosto() {
	        return COSTO;
	    }

	    @Override
	    public String getTipo() {
	        return "Estándar";
	    }

}
