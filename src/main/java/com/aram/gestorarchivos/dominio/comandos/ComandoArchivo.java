package com.alexangulo.gestorarchivos.dominio.comandos;

import java.util.Objects;

import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.Comando;

abstract class ComandoArchivo implements Comando {
	
	private final NavegadorArchivos navegador;
	
	public ComandoArchivo(NavegadorArchivos navegador) {
		this.navegador = Objects.requireNonNull(navegador);
	}
	
	protected NavegadorArchivos navegador() {
		return navegador;
	}
	
}
