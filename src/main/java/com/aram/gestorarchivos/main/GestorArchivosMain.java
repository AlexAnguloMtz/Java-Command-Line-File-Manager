package com.alexangulo.gestorarchivos.main;

import com.alexangulo.gestorarchivos.aplicacion.GestorArchivosAppImplementacion;
import com.alexangulo.gestorarchivos.aplicacion.VistaAplicacion;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAtras;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAvanzar;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoCrear;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoEscribir;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoLeer;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoListar;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoSalir;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoSalir.Finalizable;
import com.alexangulo.gestorarchivos.dominio.servicioarchivo.NavegadorArchivos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.ServicioComandos.ServicioComandosBuilder;
import com.alexangulo.gestorarchivos.vistas.VistaConsola;

public final class GestorArchivosMain {
	
	public static void main(String[] args) {
		inicializar();
	}
	
	private static void inicializar() {
		VistaAplicacion vistaConsola = new VistaConsola();
		montarDependencias(vistaConsola);
		iniciarLoopDeAplicacion(vistaConsola);
	}

	private static void iniciarLoopDeAplicacion(VistaAplicacion vista) {
		vista.informarSaludoInicial();
		while (true) {
			vista.informarDirectorioActualYMenu();
		}		
	}

	private static void montarDependencias(VistaAplicacion vista) {
		NavegadorArchivos navegador = new NavegadorArchivos();
		GestorArchivosAppImplementacion gestorArchivos 
				= new GestorArchivosAppImplementacion(navegador);
		vista.establecerGestorArchivos(gestorArchivos);
		ServicioComandos comandos = inicializarComandos(vista, gestorArchivos, navegador);
		gestorArchivos.establecerComandos(comandos);
	}

	private static ServicioComandos inicializarComandos
		(VistaAplicacion vista, Finalizable finalizable, NavegadorArchivos navegador) {
		
		return new ServicioComandosBuilder()
						.conComando(new ComandoCrear(vista, navegador))
						.conComando(new ComandoLeer(vista, navegador))
						.conComando(new ComandoEscribir(vista, navegador))
						.conComando(new ComandoListar(vista, navegador))
						.conComando(new ComandoAtras(vista, navegador))
						.conComando(new ComandoAvanzar(vista, navegador))
						.conComando(new ComandoSalir(vista, finalizable))
						.crear();
	}
}