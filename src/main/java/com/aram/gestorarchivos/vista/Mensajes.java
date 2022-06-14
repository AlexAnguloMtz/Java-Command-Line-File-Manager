package com.alexangulo.gestorarchivos.vistas;

public class Mensajes {

	public static final String SALUDO = 
			"Bienvenido al programa Gestor de Archivos\n";
	
	public static final String INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR = 
			"\nIngrese cualquier caracter para regresar al men�";
	
	public static final String FINALIZANDO = 
			"Eligi� SALIR. Cerrando programa...se ha cerrado el programa";
	
	public static final String ELIGIO_CANCELAR_ESCRITURA = 
			"Eligi� CANCELAR la ESCRITURA en el archivo"
					+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;

	public static final String ARCHIVOS_EN_LOS_QUE_SE_PUEDE_ESCRIBIR = 
			"A continuacion se muestra la lista de archivos en los que puede ESCRIBIR";

	public static final String ELIGIO_CANCELAR_LECTURA = 
			"Eligi� CANCELAR la LECTURA del archivo"
					+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;

	public static final String ELIGIO_CANCELAR_CREACION = 
			"Eligi� CANCELAR la CREACION del archivo"
					+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;

	public static final String NO_SE_PUDO_RETROCEDER = 
			"\nNo se pudo RETROCEDER. Ya est� en el directorio ra�z!"
					+  INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;

	public static final String INSTRUCCIONES = 
			"\nIngrese la letra de la opci�n deseada y presione ENTER";

	public static final String DECIDIO_CANCELAR_AVANCE = 
			"\nEligi� CANCELAR la APERTURA de un sub directorio";

	public static final String SEPARADOR = 
			" \n - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";

	public static String solicitarNombreArchivoParaLeerConOpcionDeCancelar
		(String directorio, String opcionCancelar) {
		return
			    "\nEligi� LEER un archivo del directorio ---> " + directorio + 
			 "\n - Se listaron los archivos con permiso de lectura." +
			 "\n - Ingrese NombreArchivo.extension del archivo que desea LEER. --- Ejemplo: archivo.txt" +
			 "\n - Ingrese la letra " + opcionCancelar + " para CANCELAR y regresar al men�.";
	}

	public static String archivoNoExiste(String rutaArchivo) {
		return "\nEl archivo --> " + rutaArchivo + "\nno existe." 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noSePudoLeerArchivo(String rutaArchivo) {
		return "Error. No se pudo leer el archivo " + rutaArchivo;
	}

	public static String deseaEscribirArchivoInexistente(String rutaArchivo) {
		return  "\nEl archivo " + rutaArchivo + " NO EXISTE. "
		    + "\nDesea CREARLO y ESCRIBIR en �l de todas formas?" 
			+ "\nY - CREAR archivo y ESCRIBIR en �l." 
		    + "\nN - CANCELAR y volver al menu";
	}

	public static String decisionInvalida(String decision) {
		return "Decision inv�lida: " + decision;
	}

	public static String solicitarTextoParaEscribir(String rutaArchivo) {
		return "Ingrese el texto que desea ESCRIBIR en el archivo ---> " + rutaArchivo;
	}

	public static String solicitarNombreArchivoParaEscribirConOpcionDeCancelar
	(String rutaArchivo, String opcionCancelar) {
		return "- Ingrese el nombre del archivo en el cual desea ESCRIBIR"
	    	+"\n- Ingrese la letra '" + opcionCancelar + "' para CANCELAR y regresar al men�";
	}

	public static String informarCreacionExitosa(String rutaArchivo) {
		return "\nSe CRE� exitosamente el archivo " + rutaArchivo  
		  + INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String archivoYaExiste(String ruta) {
		return "\nYa existe el archivo ---> " + ruta + "\nNo se puede crear de nuevo" 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String retrocedioConExito(String nuevaPosicion) {
		return "\nSe RETROCEDI� al directorio ---> " + nuevaPosicion;
	}

	public static String informarEscrituraExitosa(String rutaArchivo) {
		return "\nSe ESCRIBI� exitosamente en el archivo ---> " + rutaArchivo 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String informarCreacionFallida(String ruta) {
		return "\nError. No se pudo CREAR el archivo " + ruta 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String informarEscrituraFallida(String rutaArchivo) {
		return "\nError. No se pudo ESCRIBIR en el archivo " + rutaArchivo + 
				INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String solicitarNombreArchivoNuevoConOpcionDeCancelar(String opcionCancelar,
			String rutaDirectorioActual) {
		
		return "\nEligi� CREAR un archivo en el directorio ---> " + rutaDirectorioActual + 
				 "\n - Ingrese 'NombreArchivo.extension' del archivo que desea CREAR. --- Ejemplo: archivo.txt" +
				 "\n - Ingrese la letra '" + opcionCancelar + "' para CANCELAR y regresar al men�.";
	}

	public static String directorioActual(String rutaDirectorioActual) {
		return "\nDirectorio actual ------> " + rutaDirectorioActual + "\n";
	}

	public static String textoDeArchivo(String archivo, String texto) {
		return SEPARADOR + "\n" + texto + SEPARADOR +
				"\n\nSe ley� el archivo ---> " + archivo
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noSePuedeEscribirEnEsteArchivo(String nombreArchivo) {
		return "\nError. No tiene permiso para escribir en este archivo" + 
					INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String solicitarLugarParaAvanzarConOpcionDeCancelar
	(String directorio, String opcionCancelar) {
		return "\nSe listaron los sub directorios en el directorio ---> " + directorio 
		   + "\nIngrese el nombre del directorio que desea ABRIR --- Ejemplo: Un_Directorio"
		   + "\nIngrese la letra " + opcionCancelar + " para CANCELAR y regresar al men�";
	}

	public static String avanceExitoso(String nuevaPosicion) {
		return "\nSe AVANZ� al directorio ---> " + nuevaPosicion;
	}
	
	public static String seMostroListado(String directorio) {
		return "\nSe mostr� el LISTADO de archivos en el directorio ---> " + directorio + 
			   "\nDIR = Directorio     \nFILE = Archivo (No directorio)"   
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noExisteElSubdirectorio(String directorioInvalido) {
		return "\nNo existe el sub directorio ---> " + directorioInvalido
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noSePuedeLeerPorqueElDirectorioEstaVacio(String directorio) {
		return "\nEl directorio ---> " + directorio
			+  "\nest� VACIO. No hay archivos para LEER" + INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noHayArchivosConPermisoDeLectura(String rutaDirectorioActual) {
		return "\nNo existen archivos con permiso de lectura en el directorio ---> "
				+ rutaDirectorioActual + INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String informarDirectorioVacio(String nombreDirectorio) {
		return "El directorio ---> " + nombreDirectorio
			+"\nest� VACIO. No hay contenido para LISTAR" 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String eligioCancelarEscrituraEnArchivoInexistente(String nombreArchivoParaEscribir) {
		return "Eligi� NO CREAR el archivo --->" + nombreArchivoParaEscribir + "\npara poder ESCRIBIR en �l" 
				+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

	public static String noExistenSubdirectorios(String directorioActual) {
		return      "No EXISTEN sub directorios en el directorio ---> " + directorioActual
							+ INGRESE_CUALQUIER_CARACTER_PARA_REGRESAR;
	}

}
