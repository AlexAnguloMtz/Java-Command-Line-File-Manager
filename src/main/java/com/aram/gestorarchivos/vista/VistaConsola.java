package com.alexangulo.gestorarchivos.vistas;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;
import com.alexangulo.gestorarchivos.aplicacion.VistaAplicacion;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.GestorArchivosApp;

public class VistaConsola implements VistaAplicacion {
	
	private static final String SI = "Y";
	private static final String NO = "N";
	
	private final Scanner scanner;
	private GestorArchivosApp gestorArchivos;
	
	public VistaConsola() {
		this.scanner = new Scanner(System.in);
	}
	
	@Override
	public void informarFinalizacion() {
		imprimir(Mensajes.FINALIZANDO);
	}

	private void imprimir(String string) {
		System.out.println(string);
	}

	@Override
	public void listarContenido(String directorio, Stream<String> contenido) {
		imprimir(Mensajes.SEPARADOR);
		imprimirTodos(contenido);
		imprimir(Mensajes.SEPARADOR);
		imprimir(Mensajes.seMostroListado(directorio));
		aceptarInput();
	}

	private void imprimirTodos(Stream<String> nombres) {
		nombres.forEach(this::imprimir);
	}

	@Override
	public String solicitarNombreArchivoParaLeerConOpcionDeCancelar
	(String directorio, String opcionCancelar) {
		imprimir(Mensajes.solicitarNombreArchivoParaLeerConOpcionDeCancelar(directorio, opcionCancelar));
		return aceptarInput();
	}

	@Override
	public void informarArchivosQueSePuedenLeer(Stream<String> nombresArchivos) {
		imprimir(Mensajes.SEPARADOR);
		imprimirTodos(nombresArchivos);
		imprimir(Mensajes.SEPARADOR);
	}

	@Override
	public void informarTextoDeArchivo(String archivo, String texto) {
		imprimir(Mensajes.textoDeArchivo(archivo, texto));
		aceptarInput();
	}

	@Override
	public void informarArchivoNoExiste(String rutaArchivo) {
		imprimir(Mensajes.archivoNoExiste(rutaArchivo));
		aceptarInput();
	}

	@Override
	public void informarEligioCancelarLectura() {
		imprimir(Mensajes.ELIGIO_CANCELAR_LECTURA);
		aceptarInput();
	}

	@Override
	public void informarNoSePudoLeerArchivo(String rutaArchivo) {
		imprimir(Mensajes.noSePudoLeerArchivo(rutaArchivo));
	}

	@Override
	public void informarArchivosEnLosQueSePuedeEscribir(Stream<String> archivos) {
		imprimir(Mensajes.ARCHIVOS_EN_LOS_QUE_SE_PUEDE_ESCRIBIR);
		imprimirTodos(archivos);
	}

	@Override
	public boolean deseaEscribirArchivoInexistente(String rutaArchivo) {
		imprimir(Mensajes.deseaEscribirArchivoInexistente(rutaArchivo));
		String decision = null;
		while (!decisionDualValida(decision = aceptarInput())) {
			imprimir(Mensajes.decisionInvalida(decision));
			imprimir(Mensajes.deseaEscribirArchivoInexistente(rutaArchivo));
		}
		return esUn(SI, decision);
	}

	private boolean decisionDualValida(String input) {
		return esUn(SI, input) || esUn(NO, input);
	}

	private boolean esUn(String valorEsperado, String inputParaValidar) {
		return valorEsperado.equalsIgnoreCase(inputParaValidar);
	}

	private String aceptarInput() {
		return scanner.nextLine();
	}

	@Override
	public String solicitarTextoParaEscribir(String rutaArchivo) {
		imprimir(Mensajes.solicitarTextoParaEscribir(rutaArchivo));
		return aceptarInput();
	}

	@Override
	public String solicitarNombreArchivoParaEscribirConOpcionDeCancelar
	(String rutaArchivo, String opcionCancelar) {
		imprimir(Mensajes.solicitarNombreArchivoParaEscribirConOpcionDeCancelar(rutaArchivo, opcionCancelar));
		return aceptarInput();	
	}

	@Override
	public void informarEligioCancelarEscritura() {
		imprimir(Mensajes.ELIGIO_CANCELAR_ESCRITURA);
		aceptarInput();
	}

	@Override
	public void informarEscrituraExitosa(String rutaArchivo) {
		imprimir(Mensajes.informarEscrituraExitosa(rutaArchivo));	
		aceptarInput();
	}

	@Override
	public void informarEscrituraFallida(String rutaArchivo) {
		imprimir(Mensajes.informarEscrituraFallida(rutaArchivo));
		aceptarInput();
	}

	@Override
	public void informarNoSePudoRetroceder() {
		imprimir(Mensajes.NO_SE_PUDO_RETROCEDER);
		aceptarInput();
	}

	@Override
	public void informarRetrocedioConExito(String nuevaPosicion) {
		imprimir(Mensajes.retrocedioConExito(nuevaPosicion));
	}

	@Override
	public String solicitarNombreArchivoNuevoConOpcionDeCancelar
	(String opcionCancelar, String rutaDirectorioActual) {
		imprimir(Mensajes.solicitarNombreArchivoNuevoConOpcionDeCancelar
					(opcionCancelar, rutaDirectorioActual));
		return aceptarInput();
	}

	@Override
	public void informarArchivoYaExiste(String ruta) {
		imprimir(Mensajes.archivoYaExiste(ruta));
		aceptarInput();
	}

	@Override
	public void informarEligioCancelarCreacion() {
		imprimir(Mensajes.ELIGIO_CANCELAR_CREACION);
		aceptarInput();
	}

	@Override
	public void informarCreacionFallida(String ruta) {
		imprimir(Mensajes.informarCreacionFallida(ruta));
		aceptarInput();
	}

	@Override
	public void informarCreacionExitosa(String ruta) {
		imprimir(Mensajes.informarCreacionExitosa(ruta));
		aceptarInput();
	}

	public void informarDirectorioActualYMenu() {
		imprimir(Mensajes.directorioActual(rutaDirectorioActual()));
		imprimirTodos(opciones());
		imprimir(Mensajes.INSTRUCCIONES);
		String decision = aceptarInput();
		if (!esDecisionValida(decision)) {
			imprimir(Mensajes.decisionInvalida(decision));
			return;
		}
		ejecutarDecisionUsuario(decision);
	}

	private boolean esDecisionValida(String decision) {
		return gestorArchivos.esDecisionValida(decision);
	}

	private String rutaDirectorioActual() {
		return gestorArchivos.rutaDirectorioActual();
	}

	private Stream<String> opciones() {
		return gestorArchivos.opciones();
	}

	@Override
	public void establecerGestorArchivos(GestorArchivosApp gestorArchivos) {
		this.gestorArchivos = Objects.requireNonNull(gestorArchivos);
	}
	
	@Override
	public void ejecutarDecisionUsuario(String decision) {
		gestorArchivos.ejecutarDecisionUsuario(decision);
	}

	@Override
	public void informarNoSePuedeEscribirEnEsteArchivo(String nombreArchivo) {
		imprimir(Mensajes.noSePuedeEscribirEnEsteArchivo(nombreArchivo));
		aceptarInput();
	}

	
	public void informarSaludoInicial() {
		imprimir(Mensajes.SALUDO);
	}

	@Override
	public void informarNoPuedeAvanzar(String directorioActual) {
		imprimir(Mensajes.noExistenSubdirectorios(directorioActual));
		aceptarInput();
	}

	@Override
	public void informarLugaresALosQuePuedeAvanzar(Stream<String> subDirectorios) {
		imprimir(Mensajes.SEPARADOR);
		imprimirTodos(subDirectorios);
		imprimir(Mensajes.SEPARADOR);
	}

	@Override
	public String solicitarLugarParaAvanzarConOpcionDeCancelar(String directorio, String opcionCancelar) {
		imprimir(Mensajes.solicitarLugarParaAvanzarConOpcionDeCancelar(directorio, opcionCancelar));
		return aceptarInput();
	}

	@Override
	public void informarDecidioCancelarAvance() {
		imprimir(Mensajes.DECIDIO_CANCELAR_AVANCE);
	}

	@Override
	public void informarAvanceExitoso(String nuevaPosicion) {
		imprimir(Mensajes.avanceExitoso(nuevaPosicion));
	}

	@Override
	public void informarUbicacionElegidaInvalida(String directorioInvalido) {
		imprimir(Mensajes.noExisteElSubdirectorio(directorioInvalido));
		aceptarInput();
	}

	@Override
	public void informarNoSePudoLeerPorqueElDirectorioEstaVacio(String directorio) {
		imprimir(Mensajes.noSePuedeLeerPorqueElDirectorioEstaVacio(directorio));
		aceptarInput();
	}

	@Override
	public void informarNoHayArchivosConPermisoDeLectura(String rutaDirectorioActual) {
		imprimir(Mensajes.noHayArchivosConPermisoDeLectura(rutaDirectorioActual));
		aceptarInput();
	}

	@Override
	public void informarListadoVacio(String nombreDirectorio) {
		imprimir(Mensajes.informarDirectorioVacio(nombreDirectorio));
		aceptarInput();
	}

	@Override
	public void informarEligioCancelarEscrituraEnArchivoInexistente(String nombreArchivoParaEscribir) {
		imprimir(Mensajes.eligioCancelarEscrituraEnArchivoInexistente(nombreArchivoParaEscribir));
		aceptarInput();
	}
	
}