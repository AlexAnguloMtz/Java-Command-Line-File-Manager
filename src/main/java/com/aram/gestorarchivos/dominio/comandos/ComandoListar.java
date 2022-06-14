package com.alexangulo.gestorarchivos.dominio.comandos;

import java.util.Objects;
import java.util.stream.Stream;

import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.Comando;

public final class ComandoListar implements Comando {

	public interface Listable {
		public Stream<String> contenido();
		public String nombreLista();
		public boolean listadoVacio();
	}
	
	public interface VistaListar {
		void listarContenido(String contenedorActual, Stream<String> contenido);
		void informarListadoVacio(String nombreLista);
	}		
	
	private VistaListar vista;
	private Listable listable;
	
	public ComandoListar(VistaListar vista, Listable listable) {
		this.vista = Objects.requireNonNull(vista);
		this.listable = Objects.requireNonNull(listable);
	}
	
	@Override
	public void ejecutar() {
		if (listable.listadoVacio()) {
			vista.informarListadoVacio(listable.nombreLista());
			return;
		}
		 vista.listarContenido(listable.nombreLista(), listable.contenido());
	}


	@Override
	public String toString() { return "Listar archivos del directorio actual"; }
			
}