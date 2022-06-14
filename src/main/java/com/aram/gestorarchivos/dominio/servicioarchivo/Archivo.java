package com.alexangulo.gestorarchivos.dominio.servicioarchivo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public final class Archivo {
	
	private Archivo() { throw new RuntimeException("No habrá instancias de Archivo"); }
	
	public static boolean crear(File archivo) throws IOException {
		Objects.requireNonNull(archivo);
		boolean sePudoCrearArchivo = archivo.createNewFile();
		return sePudoCrearArchivo;
	}
	
	public static String leer(File archivo) throws IOException {
		Objects.requireNonNull(archivo);
		try (var fileReader = new FileReader(archivo)) {
		 	 var stringBuilder = new StringBuilder();
			 int caracter;
			 while ((caracter = fileReader.read()) != -1) {
		 		 stringBuilder.append((char)caracter);
			 }
			 return stringBuilder.toString();
		}
	}
	
	public static void escribir (File archivo, String texto) throws IOException {
		Objects.requireNonNull(archivo); 
		Objects.requireNonNull(texto);
		try (var fileWriter = new FileWriter(archivo)) {
			 fileWriter.write(texto);
		}
	}
	
	public static Stream<String> listarDirectorio(File archivo) {
		Objects.requireNonNull(archivo);
		return Arrays.stream(archivo.list());
	}

}
