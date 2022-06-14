package com.alexangulo.gestorarchivos.dominio.serviciocomandos;

import java.util.stream.Stream;

public interface GestorArchivosApp {
	
	public void ejecutarDecisionUsuario(String decision);
	public Stream<String> opciones();
	public boolean esDecisionValida(String decision);
	public String rutaDirectorioActual();
	
}