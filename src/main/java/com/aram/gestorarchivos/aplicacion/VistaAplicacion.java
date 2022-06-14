package com.alexangulo.gestorarchivos.aplicacion;

import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAtras.VistaAtras;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoAvanzar.VistaAvanzar;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoCrear.VistaCrear;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoEscribir.VistaEscribir;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoLeer.VistaLeer;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoListar.VistaListar;
import com.alexangulo.gestorarchivos.dominio.comandos.ComandoSalir.VistaSalir;
import com.alexangulo.gestorarchivos.dominio.serviciocomandos.GestorArchivosApp;

public interface VistaAplicacion extends 
	VistaCrear, VistaLeer, VistaEscribir, 
	VistaListar, VistaAtras, VistaSalir, VistaAvanzar {
	
	void informarSaludoInicial();
	void informarDirectorioActualYMenu();
	void establecerGestorArchivos(GestorArchivosApp gestorArchivos);
	void ejecutarDecisionUsuario(String decision);

}
