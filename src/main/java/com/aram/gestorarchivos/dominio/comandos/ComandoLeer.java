package com.alexangulo.gestorarchivos.dominio.comandos;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.alexangulo.gestorarchivos.dominio.servicioarchivo.Archivo;
import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.Comando;

public final class ComandoLeer extends ComandoArchivo implements Comando {
	
		public interface VistaLeer {
			
			public void informarTextoDeArchivo(String archivo, String texto);
			public void informarNoSePudoLeerPorqueElDirectorioEstaVacio(String directorio);
			public void informarArchivoNoExiste(String ruta);
			public void informarEligioCancelarLectura();
			public void informarNoSePudoLeerArchivo(String ruta);
			void informarArchivosQueSePuedenLeer(Stream<String> nombresArchivos);
			String solicitarNombreArchivoParaLeerConOpcionDeCancelar(String directorio, String opcionCancelar);
			public void informarNoHayArchivosConPermisoDeLectura(String rutaDirectorioActual);
		
		}
	
			private final VistaLeer vista;

			public ComandoLeer(VistaLeer vista, NavegadorArchivos navegador) {
				super(navegador);
				this.vista = Objects.requireNonNull(vista);
			}
	
			@Override
			public void ejecutar() {
				if(!puedeLeer()) {
					informarMotivoPorElCualNoPuedeLeer();
					return;
				}
				informarArchivosQueSePuedenLeer();
				String nombreArchivoOSinoDecisionCancelar = 
						solicitarNombreArchivoParaLeerConOpcionDeCancelar();
				if (seDebeAbortarLectura(nombreArchivoOSinoDecisionCancelar)) {
					informarMotivoPorElCualSeDebeAbortarLectura(nombreArchivoOSinoDecisionCancelar);
					return;
				}
				String nombreArchivoConfirmado = nombreArchivoOSinoDecisionCancelar;
				leer(crearFile(nombreArchivoConfirmado));
			}
			
			private void informarMotivoPorElCualSeDebeAbortarLectura
			(String nombreArchivoOSinoDecisionCancelar) {
				if (decidioCancelar(nombreArchivoOSinoDecisionCancelar)) {
					informarEligioCancelarLectura();
					return;
				}
				String nombreArchivoConfirmado = nombreArchivoOSinoDecisionCancelar;
				if (!existe(nombreArchivoConfirmado)) {
					informararArchivoNoExiste(ruta(nombreArchivoConfirmado));
					return;
				}			
				throw new IllegalStateException
					("Imposible verificar motivo por el cual se abortó lectura de archivo");
			}

			private boolean seDebeAbortarLectura(String nombreArchivoOSinoDecisionCancelar) {
				return      ((decidioCancelar(nombreArchivoOSinoDecisionCancelar)) 
						||  (!existe(nombreArchivoOSinoDecisionCancelar)));
			}

			private void informarMotivoPorElCualNoPuedeLeer() {
				if (!hayArchivosConPermisoDeLectura()) {
					informarNoHayArchivosConPermisoDeLectura();
					return;
				}	
				if (directorioVacio()) {
					informarNoSePudoLeerPorqueElDirectorioEstaVacio();
					return;
				}
				throw new IllegalStateException
					("Imposible verificar motivo por el cual no se puede leer archivo");
			}

			private boolean puedeLeer() {
				return ( ! directorioVacio() && hayArchivosConPermisoDeLectura() );
			}

			private void informarNoHayArchivosConPermisoDeLectura() {
				vista.informarNoHayArchivosConPermisoDeLectura(navegador().rutaDirectorioActual());
			}

			private boolean hayArchivosConPermisoDeLectura() {
				return navegador().hayArchivosConPermisoDeLectura();
			}

			private boolean directorioVacio() {
				return navegador().directorioVacio();
			}

			private void informarNoSePudoLeerPorqueElDirectorioEstaVacio() {
				vista.informarNoSePudoLeerPorqueElDirectorioEstaVacio
					(navegador().rutaDirectorioActual());
			}

			private String ruta(String nombreArchivo) {
				return navegador().ruta(nombreArchivo);
			}

			private boolean existe(String nombreArchivo) {
				return navegador().existe(nombreArchivo);
			}

			private File crearFile(String nombreArchivo) {
				return navegador().crearFile(nombreArchivo);
			}
			
			private void informararArchivoNoExiste(String ruta) {
				vista().informarArchivoNoExiste(ruta);
			}

			private void informarEligioCancelarLectura() {
				vista().informarEligioCancelarLectura();
			}

			private String solicitarNombreArchivoParaLeerConOpcionDeCancelar() {
				return vista().solicitarNombreArchivoParaLeerConOpcionDeCancelar
						(navegador().posicionActual(), opcionCancelar());
			}

			private void informarArchivosQueSePuedenLeer() {
				vista().informarArchivosQueSePuedenLeer
					(navegador().archivosQueSePuedenLeer());
			}

			private VistaLeer vista() {
				return vista;
			}

			private void leer(File archivoParaLeer) {
				try {
					informarTextoDeArchivo(ruta(archivoParaLeer),Archivo.leer(archivoParaLeer));
				} catch (IOException e) {
					informarNoSePudoLeerArchivo(archivoParaLeer.getAbsolutePath());
				}				
			}

			private String ruta(File archivoParaLeer) {
				return navegador().ruta(archivoParaLeer);
			}

			private void informarNoSePudoLeerArchivo(String ruta) {
				vista().informarNoSePudoLeerArchivo(ruta);
			}

			private void informarTextoDeArchivo(String archivo, String texto) {
				vista().informarTextoDeArchivo(archivo, texto);
			}

			public String mensajeError(File archivo) {
				return "No se pudo leer archivo " + archivo.getAbsolutePath();
			}

			@Override
			public String obtenerId() { return "R"; }

			@Override
			public String toString() { return "Leer un archivo del directorio actual"; }

		}