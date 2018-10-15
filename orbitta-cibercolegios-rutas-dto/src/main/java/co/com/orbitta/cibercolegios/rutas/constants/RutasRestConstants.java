package co.com.orbitta.cibercolegios.rutas.constants;

public class RutasRestConstants {

	final public static String configurationPropertiesPrefix = "co.com.orbitta.cibercolegios.rutas.rest";

	final public static String base = "/api/rutas";

	final public static String estadoRuta = base + "/estado-ruta";

	final public static String logRuta = base + "/log-ruta";

	final public static String estadoPasajero = base + "/estado-pasajero";

	final public static String logPasajero = base + "/log-pasajero";

	final public static String tracking = base + "/tracking";

	final public static String chat = base + "/chat";

	final public static String conversaciones = chat + "/conversaciones";

	final public static String mensajes = "/{conversacionId}/mensajes";

}
