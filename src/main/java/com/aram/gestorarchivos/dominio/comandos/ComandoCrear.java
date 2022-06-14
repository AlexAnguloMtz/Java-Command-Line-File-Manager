package com.alexangulo.gestorarchivos.dominio.comandos;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.alexangulo.gestorarchivos.dominio.servicioarchivo.Archivo;
import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.ComandoAutoReiniciable;

public final class ComandoCrear extends ComandoArchivo 
	implements ComandoAutoReiniciable {
	
			private final VistaCrear vista;
	
			private String decisionOSinoNombreArchivo;

			private String nombreArchivo;
			
			public ComandoCrear(VistaCrear vista, NavegadorArchivos navegador) {
				super(navegador);
				this.vista = Objects.requireNonNull(vista);
			}
			
			@Override
			public void ejecutarAntesDeReiniciar() {
				intentarCreacion();
			}
			
			@Override
			public void reiniciarComando() {
				this.decisionOSinoNombreArchivo = null;
				this.nombreArchivo = null;
			}

			private void intentarCreacion() {
				if (decidioCancelar(decisionOSinoNombreArchivo())) {
					mostrarEligioCancelar();
					return;
				}
				inicializarNombreArchivo();
				if (existe(nombreArchivo())) {
					informarArchivoYaExiste(nombreArchivo());
					return;
				}
				crear(crearFile(nombreArchivo()));
			}
			
			private void inicializarNombreArchivo() {
				this.nombreArchivo = decisionOSinoNombreArchivo();
			}

			private String nombreArchivo() {
				return nombreArchivo;
			}

			private String decisionOSinoNombreArchivo() {
				if (decisionOSinoNombreArchivo == null) {
					decisionOSinoNombreArchivo = 
						solicitarNombreArchivoNuevoConOpcionDeCancelar();
				}
				return decisionOSinoNombreArchivo;
			}

			private String ruta(String nombreArchivo) {
				return crearFile(nombreArchivo).getAbsolutePath();
			}

			private boolean existe(String nombreArchivo) {
				return crearFile(nombreArchivo).exists();
			}

			private void informarArchivoYaExiste(String nombreArchivo) {
				vista().informarArchivoYaExiste(ruta(nombreArchivo));
			}

			private VistaCrear vista() {
				return vista;
			}

			private void mostrarEligioCancelar() {
				vista().informarEligioCancelarCreacion();
			}

			private String solicitarNombreArchivoNuevoConOpcionDeCancelar() {
				return vista().solicitarNombreArchivoNuevoConOpcionDeCancelar
						(opcionCancelar(), rutaDirectorioActual());
			}

			private String rutaDirectorioActual() {
				return navegador().rutaDirectorioActual();
			}

			private File crearFile(String nombreArchivo) {
				return new File(nombreArchivo);
			}

			private void crear(File archivoParaCrear) {
				try {
					if (Archivo.crear(archivoParaCrear));
						informarCreacionExitosa((archivoParaCrear));
				} catch (IOException e) {
					mostrarOperacionFallida(mensajeError(archivoParaCrear));
				}
			}
	
			private void informarCreacionExitosa(File archivo) {
				vista().informarCreacionExitosa(ruta(archivo));
			}

			private String ruta(File archivo) {
				return archivo.getAbsolutePath();
			}

			private void mostrarOperacionFallida(String ruta) {
				vista().informarCreacionFallida(ruta);
			}

			public String mensajeError(File archivo) {
				return "No se pudo crear archivo " + archivo.getAbsolutePath();
			}
	
			@Override
			public String toString() { return "Crear archivo en directorio actual"; }

			public void reiniciar() {
				this.decisionOSinoNombreArchivo = null;
				this.nombreArchivo = null;
			}
			
			
			public interface VistaCrear {
				
				public String solicitarNombreArchivoNuevoConOpcionDeCancelar
					(String opcionCancelar, String rutaDirectorioActual);
				
				public void informarArchivoYaExiste(String ruta);
				
				public void informarEligioCancelarCreacion();
				
				public void informarCreacionFallida(String ruta);
				
				public void informarCreacionExitosa(String ruta);


			}

}