package com.alexangulo.gestorarchivos.dominio.comandos;

import java.util.Objects;
import java.util.stream.Stream;

import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.ComandoAutoReiniciable;

public final class ComandoAvanzar implements ComandoAutoReiniciable {

	public interface Avanzable {
		public boolean puedeAvanzar();
		public Stream<String> lugaresParaAvanzar();
		public void avanzar(String lugarParaAvanzar);
		public String ubicacionActual();
		public boolean esUbicacionValida(String ubicacion);
	}
	
	public interface VistaAvanzar {
		public void informarNoPuedeAvanzar(String ubicacionActual);
		public void informarLugaresALosQuePuedeAvanzar(Stream<String> lugaresParaAvanzar);
		public String solicitarLugarParaAvanzarConOpcionDeCancelar
			(String ubicacionActual, String opcionCancelar);
		public void informarDecidioCancelarAvance();
		public void informarAvanceExitoso(String nuevaPosicion);
		public void informarUbicacionElegidaInvalida(String lugarParaAvanzar);
	}
	
	private final Avanzable avanzable;
	private final VistaAvanzar vista;
	private String lugarParaAvanzarOSinoDecisionCancelar;
	
	public ComandoAvanzar(VistaAvanzar vista, Avanzable avanzable) {
		this.avanzable = Objects.requireNonNull(avanzable);
		this.vista = Objects.requireNonNull(vista);
	}
	
	@Override
	public void ejecutarAntesDeReiniciar() {
		avanzar();
	}
	
	@Override
	public void reiniciarComando() {
		this.lugarParaAvanzarOSinoDecisionCancelar = null;
	}

	private void avanzar() {
		if (!puedeAvanzar()) {
			informarNoPuedeAvanzar();
			return;
		}
		informarLugaresALosQuePuedeAvanzar();
		establecerLugarParaAvanzar(solicitarLugarParaAvanzarConOpcionDeCancelar());
		if (decidioCancelar()) {
			informarDecidioCancelarAvance();
			return;
		}
		intentarAvanzar();		
	}

	private void intentarAvanzar() {
		if (!esUbicacionValida(lugarParaAvanzar())) {
			informarUbicacionElegidaInvalida();
			return;
		}
		avanzar(lugarParaAvanzar());
		informarAvanceExitoso();		
	}

	private boolean esUbicacionValida(String ubicacionALaCualDeseaAvanzar) {
		return avanzable.esUbicacionValida(ubicacionALaCualDeseaAvanzar);
	}

	private void informarUbicacionElegidaInvalida() {
		vista.informarUbicacionElegidaInvalida(lugarParaAvanzar());
	}

	@Override
	public String obtenerId() { return "O"; }

	@Override
	public String toString() { return "Abrir sub directorio"; }

	private void informarAvanceExitoso() {
		vista.informarAvanceExitoso(avanzable.ubicacionActual());
	}

	private void avanzar(String lugarParaAvanzar) {
		avanzable.avanzar(lugarParaAvanzar);
	}

	private String lugarParaAvanzar() {
		return lugarParaAvanzarOSinoDecisionCancelar;
	}

	private void informarDecidioCancelarAvance() {
		vista.informarDecidioCancelarAvance();
	}

	private boolean decidioCancelar() {
		return lugarParaAvanzarOSinoDecisionCancelar.equalsIgnoreCase(opcionCancelar());
	}

	private void establecerLugarParaAvanzar(String lugarParaAvanzarOSinoDecisionCancelar) {
		this.lugarParaAvanzarOSinoDecisionCancelar = lugarParaAvanzarOSinoDecisionCancelar;
	}

	private String solicitarLugarParaAvanzarConOpcionDeCancelar() {
		return vista.solicitarLugarParaAvanzarConOpcionDeCancelar
				(ubicacionActual(), opcionCancelar());
	}

	private String ubicacionActual() {
		return avanzable.ubicacionActual();
	}

	private void informarLugaresALosQuePuedeAvanzar() {
		vista.informarLugaresALosQuePuedeAvanzar(lugaresParaAvanzar());
	}

	private Stream<String> lugaresParaAvanzar() {
		return avanzable.lugaresParaAvanzar();
	}

	private void informarNoPuedeAvanzar() {
		vista.informarNoPuedeAvanzar(ubicacionActual());
	}

	private boolean puedeAvanzar() {
		return avanzable.puedeAvanzar();
	}


}
