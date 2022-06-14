package com.alexangulo.gestorarchivos.aplicacion;

import java.util.Objects;
import java.util.stream.Stream;

import com.alexangulo.gestorarchivos.dominio.comandos.ComandoSalir.Finalizable;
import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.GestorArchivosApp;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos;

public final class GestorArchivosAppImplementacion 
	implements GestorArchivosApp, Finalizable {
	
	private ServicioComandos servicioComandos;
	private final NavegadorArchivos navegador;
	
	public GestorArchivosAppImplementacion(NavegadorArchivos navegador) {
		this.navegador = Objects.requireNonNull(navegador);
	}

	@Override
	public void ejecutarDecisionUsuario(String decision) {
		ejecutarComando(decision);
	}
	
	@Override
	public void finalizar() {
		System.exit(0);
	}

	public void establecerComandos(ServicioComandos comandos) {
		this.servicioComandos = Objects.requireNonNull(comandos);
	}
	
	@Override
	public Stream<String> opciones() {
		return servicioComandos.opciones();
	}

	private void ejecutarComando(String decision) {
		servicioComandos.ejecutarComando(decision);
	}

	@Override
	public boolean esDecisionValida(String decision) {
		return servicioComandos.esComandoValido(decision);
	}

	@Override
	public String rutaDirectorioActual() {
		return navegador().rutaDirectorioActual();
	}

	private NavegadorArchivos navegador() {
		return navegador;
	}

}