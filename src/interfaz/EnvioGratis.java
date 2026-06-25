package interfaz;

import dominio.Envio;

public class EnvioGratis implements Envio {

	@Override
    public Double calcularCosto() {
        return 0.0;
    }

    @Override
    public String getTipo() {
        return "Gratis";
    }

}
