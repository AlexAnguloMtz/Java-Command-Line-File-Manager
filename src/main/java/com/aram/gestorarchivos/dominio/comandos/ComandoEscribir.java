package com.alexangulo.gestorarchivos.dominio.comandos;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.alexangulo.gestorarchivos.dominio.servicioarchivo.Archivo;
import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.ComandoAutoReiniciable;

public final class ComandoEscribir extends ComandoArchivo 
	implements ComandoAutoReiniciable {

		private final VistaEscribir vista;
		private String decisionOSinoNombreArchivo;
		private String nombreArchivoParaEscribir;

		public ComandoEscribir(VistaEscribir vista, NavegadorArchivos navegador) {
			super(navegador);
			this.vista = Objects.requireNonNull(vista);
		}
	
		@Override
		public void ejecutarAntesDeReiniciar() {
			intentarEscribir();
		}

		@Override
		public void reiniciarComando() {
			this.decisionOSinoNombreArchivo = null;
			this.nombreArchivoParaEscribir = null;
		}

		private void intentarEscribir() {
			informarArchivosEnLosQueSePuedeEscribir();
			if (decidioCancelar(decisionOSinoNombreArchivo())) {
				informarEligioCancelarEscritura();
				return;
			}
			inicializarNombreArchivoParaEscribir();
			if (!existe() && !deseaEscribirArchivoInexistente()) {
				informarEligioCancelarEscrituraEnArchivoInexistente();
				return;
			}
			if (!sePuedeEscribir()) {
				informarNoSePuedeEscribirEnEsteArchivo();
				return;
			}
			escribir(crearFile(nombreArchivoParaEscribir()));			
		}

		private boolean existe() {
			return navegador().existe(nombreArchivoParaEscribir());
		}

		private void informarEligioCancelarEscrituraEnArchivoInexistente() {
			vista.informarEligioCancelarEscrituraEnArchivoInexistente(nombreArchivoParaEscribir());
		}

		private boolean deseaEscribirArchivoInexistente() {
			return vista.deseaEscribirArchivoInexistente(nombreArchivoParaEscribir());
		}

		private void informarNoSePuedeEscribirEnEsteArchivo() {
			vista.informarNoSePuedeEscribirEnEsteArchivo(ruta(nombreArchivoParaEscribir()));
		}

		private boolean sePuedeEscribir() {
			if (!existe()) {
				return true;
			}
			return navegador().sePuedeEscribir(nombreArchivoParaEscribir());
		}

		private File crearFile(String nombreArchivoParaEscribir) {
			return navegador().crearFile(nombreArchivoParaEscribir);
		}

		private String nombreArchivoParaEscribir() {
			return nombreArchivoParaEscribir;
		}

		private void inicializarNombreArchivoParaEscribir() {
			this.nombreArchivoParaEscribir = decisionOSinoNombreArchivo();
		}

		private String decisionOSinoNombreArchivo() {
			if (decisionOSinoNombreArchivo == null) {
				decisionOSinoNombreArchivo = 
					solicitarNombreArchivoParaEscribirConOpcionDeCancelar();
			}
			return decisionOSinoNombreArchivo;
		}

		private void informarEligioCancelarEscritura() {
			vista().informarEligioCancelarEscritura();
		}

		private String solicitarNombreArchivoParaEscribirConOpcionDeCancelar() {
			return vista().solicitarNombreArchivoParaEscribirConOpcionDeCancelar
					(nombreArchivoParaEscribir(), opcionCancelar());
		}

		private void informarArchivosEnLosQueSePuedeEscribir() {
			vista().informarArchivosEnLosQueSePuedeEscribir(archivosEnLosQueSePuedeEscribir());
		}

		private Stream<String> archivosEnLosQueSePuedeEscribir() {
			return navegador().archivosEnLosQueSePuedeEscribir();
		}

		private void escribir(File archivoParaEscribir) {
			try {
				Archivo.escribir(archivoParaEscribir, solicitarTextoParaEscribir());
				informarEscrituraExitosa(archivoParaEscribir);
			} catch (IOException e) {
				informarEscrituraFallida(archivoParaEscribir);
			}
		}

		private String solicitarTextoParaEscribir() {
			return vista().solicitarTextoParaEscribir(nombreArchivoParaEscribir());
		}

		private void informarEscrituraFallida(File archivo) {
			vista().informarEscrituraFallida(ruta(archivo));
		}

		private VistaEscribir vista() {
			return vista;
		}

		private void informarEscrituraExitosa(File archivo) {
			vista().informarEscrituraExitosa(ruta(archivo));
		}

		private String ruta(File archivo) {
			return navegador().ruta(archivo);
		}
		
		private String ruta(String nombreArchivo) {
			return navegador().ruta(nombreArchivo);
		}

		public String mensajeError(File archivo) {
			return "No se pudo escribir en archivo " + archivo.getAbsolutePath();
		}

		@Override
		public String obtenerId() { return "W"; }

		@Override
		public String toString() { return "Escribir en un archivo del directorio actual"; }

		public interface VistaEscribir {
			
			public void informarArchivosEnLosQueSePuedeEscribir(Stream<String> string);
			
			public void informarEligioCancelarEscrituraEnArchivoInexistente(String nombreArchivoParaEscribir);

			public void informarNoSePuedeEscribirEnEsteArchivo(String string);

			public boolean deseaEscribirArchivoInexistente(String rutaArchivo);
			
			public String solicitarTextoParaEscribir(String rutaArchivo);
			
			public String solicitarNombreArchivoParaEscribirConOpcionDeCancelar
				(String rutaArchivo, String opcionCancelar);
			
			public void informarEligioCancelarEscritura();
			
			public void informarEscrituraExitosa(String rutaArchivo);
			
			public void informarEscrituraFallida(String rutaArchivo);

		}

		
	}