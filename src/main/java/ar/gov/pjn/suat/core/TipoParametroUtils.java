package ar.gov.pjn.suat.core;

public class TipoParametroUtils {

	public static final String PERIODICO = "P";
	public static final String DIARIO 	 = "D";
	public static final String SEMANAL 	 = "S";
	public static final String MENSUAL 	 = "M";
	public static final String DEFINE_TAREA 	 = "T";
	public static final String CRON = "C";

	public static final String PERIODICO_DESCRIPCION = "Periódico";
	public static final String DIARIO_DESCRIPCION    = "Diario";
	public static final String SEMANAL_DESCRIPCION   = "Semanal";
	public static final String MENSUAL_DESCRIPCION   = "Mensual";
	public static final String DEFINE_TAREA_DESCRIPCION   = "Define la Tarea";
	public static final String CRON_DESCRIPCION   = "Define por Cron";

	public static final String[] daysOfTheWeek = {"Domingo",
													"Lunes",
												   "Martes",
												"Miércoles",
												   "Jueves",
												  "Viernes",
												   "Sábado"};

	public static String getDescricion(String tipo_parametro) {

		if (tipo_parametro.equals(PERIODICO)) {
			return PERIODICO_DESCRIPCION;
		} else if (tipo_parametro.equals(DIARIO)) {
			return DIARIO_DESCRIPCION;
		} else if (tipo_parametro.equals(SEMANAL)) {
			return SEMANAL_DESCRIPCION;
		} else if (tipo_parametro.equals(MENSUAL)) {
			return MENSUAL_DESCRIPCION;
		} else if (tipo_parametro.equals(DEFINE_TAREA)) {
			return DEFINE_TAREA_DESCRIPCION;
		} else if (tipo_parametro.equals(CRON)) {
			return CRON_DESCRIPCION;
		}

		return "";
	}

	public static String formatParametroForTabla(String tipo_parametro, String parametro) {
		if (tipo_parametro.equals(PERIODICO)) {
			return parametro + " min.";
		} else if (tipo_parametro.equals(DIARIO)) {
			return parametro + " hs";
		} else if (tipo_parametro.equals(SEMANAL)) {
			String[] split = parametro.split(":");
			return  getDayOfTheWeek(Integer.valueOf(split[0])) + " " + split[1] + ":" + split[2] + " hs";
		} else if (tipo_parametro.equals(MENSUAL)) {
			String[] split = parametro.split(":");
			return  Integer.valueOf(split[0]) + " - " + split[1] + ":" + split[2] + " hs";
		}

		return parametro;
	}

	private static String getDayOfTheWeek(int dia) {
		return daysOfTheWeek[dia-1];
	}
}
