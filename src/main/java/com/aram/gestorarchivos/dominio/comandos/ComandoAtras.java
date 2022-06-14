package com.alexangulo.gestorarchivos.dominio.comandos;

import java.util.Objects;

import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.Comando;

public final class ComandoAtras implements Comando {

	public interface Retrocedible {
		public void retroceder();
		public boolean puedeRetroceder();
		public String posicionActual();
	}
	
	public interface VistaAtras {
		void informarNoSePudoRetroceder();
		void informarRetrocedioConExito(String nuevaPosicion);
	}

	
	private final VistaAtras vista;
	private final Retrocedible retrocedible;
	
	public ComandoAtras(VistaAtras vista, Retrocedible retrocedible) {
		this.vista = Objects.requireNonNull(vista);
		this.retrocedible = Objects.requireNonNull(retrocedible);
	}
	
	@Override
	public void ejecutar() {
		 if (!puedeRetroceder()) {
			informarNoSePudoRetroceder();
		 	return;
	     }
		 retroceder();
		 informarRetrocedioConExito();
	}

	private void retroceder() {
		retrocedible().retroceder();
	}

	private boolean puedeRetroceder() {
		return retrocedible().puedeRetroceder();
	}

	private void informarRetrocedioConExito() {
		vista().informarRetrocedioConExito(posicionActual());
	}

	private String posicionActual() {
		return retrocedible().posicionActual();
	}

	private Retrocedible retrocedible() {
		return retrocedible;
	}

	private void informarNoSePudoRetroceder() {
		vista().informarNoSePudoRetroceder(); 
	}

	private VistaAtras vista() {
		return vista;
	}

	@Override
	public String obtenerId() { return "B"; }

	@Override
	public String toString() { return "Regresar un directorio atrás"; }

}