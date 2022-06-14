package com.alexangulo.gestorarchivos.dominio.servicioarchivo;

import static java.util.Optional.ofNullable;
import static java.util.Arrays.stream;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAtras.Retrocedible;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAvanzar.Avanzable;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoListar.Listable;

public final class NavegadorArchivos implements Listable, Avanzable, Retrocedible {

	private static final char SEPARADOR = File.separatorChar;

	private static final String PREFIJO_DIRECTORIO = "DIR    ";

	private static final String PREFIJO_ARCHIVO    = "FILE   ";
	
	private File directorioActual;
	
	public NavegadorArchivos() {
		this.directorioActual = inicializarDirectorioActual();
	}

	public File directorioActual() {
		return directorioActual;
	}

	public void moverseAlDirectorio(File directorio) {
		establecerDirectorioActual(directorio);		
	}

	public String rutaDirectorioActual() {
		return directorioActual().getAbsolutePath();
	}
	
	private Stream<String> nombresArchivosEnDirectorioActual() {
		return stream(archivosEnDirectorioActual())
			  .map(this::formatearString);
	}
	
	private String formatearString(File archivo) {
		return  archivo.isDirectory() ? PREFIJO_DIRECTORIO + archivo.getName() 
									  : PREFIJO_ARCHIVO + archivo.getName();
	}
	
	public Stream<String> archivosEnLosQueSePuedeEscribir() {
		return filtrar((archivo -> archivo.isFile() && archivo.canRead()));
	}
	
	public File crearFile(String nombreArchivo) {
		return new File(rutaDirectorioActual() + SEPARADOR + nombreArchivo);
	}

	public boolean existe(String nombreArchivo) {
		return crearFile(nombreArchivo).exists();
	}

	public String ruta(String nombreArchivo) {
		return rutaDirectorioActual() + SEPARADOR + nombreArchivo;
	}

	public String ruta(File archivo) {
		return archivo.getAbsolutePath();
	}

	@Override
	public void retroceder() {
		retrocederAlDirectorioAnterior();
	}

	@Override
	public boolean puedeRetroceder() {
		return directorioPadre().isPresent();
	}

	@Override
	public String posicionActual() {
		return rutaDirectorioActual();
	}
	
	@Override
	public Stream<String> contenido() {
		return nombresArchivosEnDirectorioActual();
	}

	private File inicializarDirectorioActual() {
		return new File("").getAbsoluteFile();
	}
	
	private void establecerDirectorioActual(File directorio) {
		if (!Objects.requireNonNull(directorio).exists()) {
			throw new IllegalArgumentException("El directorio " + directorio + " no existe");
		}
		this.directorioActual = directorio;		
	}

	private Optional<File> directorioPadre() {
		return ofNullable(directorioActual().getParentFile());
	}
	
	private File[] archivosEnDirectorioActual() {
		return directorioActual().listFiles();
	}

	private void retrocederAlDirectorioAnterior() {
		moverseAlDirectorio(directorioPadre().orElseThrow(UnsupportedOperationException::new));
	}

	public Stream<String> archivosQueSePuedenLeer() {
		return filtrar(archivo -> archivo.isFile() && archivo.canRead());
	}
	
	public Stream<String> filtrar(Predicate<File> selector) {
		return stream(archivosEnDirectorioActual())
			  .filter(selector)
			  .map(this::formatearString);
	}

	public boolean sePuedeEscribir(String nombreArchivoParaEscribir) {
		return crearFile(nombreArchivoParaEscribir).canWrite();
	}

	@Override
	public boolean puedeAvanzar() {
		return directorioActualContieneDirectorios();
	}

	private boolean directorioActualContieneDirectorios() {
		return alMenosUno(File::isDirectory);
	}

	@Override
	public Stream<String> lugaresParaAvanzar() {
		return filtrar(File::isDirectory);
	}

	@Override
	public void avanzar(String directorioSiguiente) {
		establecerDirectorioActual(crearFile((directorioSiguiente)));
	}

	@Override
	public String ubicacionActual() {
		return rutaDirectorioActual();
	}

	@Override
	public String nombreLista() {
		return rutaDirectorioActual();
	}

	@Override
	public boolean esUbicacionValida(String posibleDirectorio) {
		return (posibleDirectorio.length() > 0) && esDirectorio(posibleDirectorio);
	}

	private boolean esDirectorio(String posibleDirectorio) {
		return crearFile(posibleDirectorio).isDirectory();
	}

	public boolean directorioVacio() {
		return 	stream(archivosEnDirectorioActual())
			   .noneMatch(File::exists); 
	}

	public boolean hayArchivosConPermisoDeLectura() {
		return alMenosUno(archivo -> (archivo.isFile() && archivo.canRead()));
	}
	
	private boolean alMenosUno(Predicate<File> selector) {
		return stream(archivosEnDirectorioActual())
			  .anyMatch(selector);
	}

	@Override
	public boolean listadoVacio() {
		return directorioVacio();
	}
	
}

