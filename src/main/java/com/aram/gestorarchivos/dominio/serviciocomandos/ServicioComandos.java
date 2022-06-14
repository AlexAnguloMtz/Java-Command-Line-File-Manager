package com.alexangulo.gestorarchivos.dominio.serviciocomandos;

import static java.util.stream.Collectors.toMap;
import static java.util.Optional.ofNullable;
import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

public final class ServicioComandos {
	

		private final Map<String, Comando> comandos;
		
		private ServicioComandos(Collection <? extends Comando> comandos) {
			this.comandos = unmodifiableMap(mapaDeComandos(comandos));
		}
		
		public void ejecutarComando(String claveComando) {
			obtenerComando(claveComando)
			  .ifPresentOrElse(Comando::ejecutar,UnsupportedOperationException::new);
		}
	
		public Stream<String> stringsComandos() {
			return comandos.entrySet().stream()
						   			  .map(ServicioComandos::comandoString);
		}
		
		public boolean esComandoValido(String claveComando) {
			return obtenerComando(claveComando).isPresent();
		}
		
		private Optional<Comando> obtenerComando(String claveComando) {
			return ofNullable(comandos.get(claveComando.toUpperCase()));
		}
		
		private static String comandoString(Entry<String, ? extends Comando> par) {
			return par.getValue().formatearString();
		}
		
		private Map<String, Comando> mapaDeComandos(Collection<? extends Comando> comandos) {
			return comandos.stream()
						   .collect(toMap
						 	(Comando::obtenerId, 
						     comando -> comando,           	
							(comandoExistente, comandoNuevo) -> comandoExistente, 
							 LinkedHashMap::new));          
		}
		
		public Stream<String> opciones() {
			return stringsComandos();
		}
		
		
		public interface Comando {
			
			public void ejecutar();

	        @Override
	        public abstract String toString();
			
			default String formatearString() {
				if (obtenerId().length() != 1) {
					throw new IllegalStateException
						("Longitud invalida para obtenerId(). Esperado: longitud 1, "
					   + "Encontrado: longitud " + obtenerId().length());
				}
				return obtenerId() + espaciado() + toString();
			}

			default String obtenerId() { return String.valueOf(toString().charAt(0)); }

			default String espaciado() { return "  "; }
			
			default String opcionCancelar() { return "C"; }
			
			default boolean decidioCancelar(String cadena) {
				return opcionCancelar().equalsIgnoreCase(cadena);
			}
			
		}
		
		public interface ComandoAutoReiniciable extends Comando {
			
			public void reiniciarComando();

			public void ejecutarAntesDeReiniciar();
			
			@Override
			public default void ejecutar() {
				ejecutarAntesDeReiniciar();
				reiniciarComando();
			}

			
		}
		
		public static final class ServicioComandosBuilder {
			
			private  final List<Comando> comandos;
			
			public ServicioComandosBuilder() {
				this.comandos = new ArrayList<>();
			}
			
			public ServicioComandosBuilder conComando(Comando comando) {
				comandos.add(Objects.requireNonNull(comando));
				return this;
			}
			
			public ServicioComandos crear() {
				return new ServicioComandos(comandos);
			}
		}
		
}