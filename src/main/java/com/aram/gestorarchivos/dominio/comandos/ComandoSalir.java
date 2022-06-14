package com.alexangulo.gestorarchivos.dominio.comandos;

import java.util.Objects;

import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.Comando;

public final class ComandoSalir implements Comando {

	public interface Finalizable {
		public void finalizar();
	}
	
	public interface VistaSalir {
		public void informarFinalizacion();
	}
	
	private final Finalizable finalizable;
	private final VistaSalir vista;
	
	public ComandoSalir(VistaSalir vista, Finalizable finalizable) {
		this.vista = Objects.requireNonNull(vista);
		this.finalizable = Objects.requireNonNull(finalizable);
	}

	@Override
	public void ejecutar() {
		informarFinalizacion();
		finalizar();
	}

	private void finalizar() {
		finalizable.finalizar();
	}

	private void informarFinalizacion() {
		vista.informarFinalizacion();
	}
	
	public String obtenerId() { return "E"; }

	@Override
	public String toString() { return "Salir"; }
	

}
