package com.tbf.cibercolegios.api.model.routes.enums;

public enum CourseType {
	SENTIDO_IDA, SENTIDO_RETORNO;

	public String getDescripcion() {
		switch (this) {
		case SENTIDO_IDA:
			return "AM";
		case SENTIDO_RETORNO:
			return "PM";
		default:
			return "??";
		}
	}

	public Integer getIntValue() {
		switch (this) {
		case SENTIDO_IDA:
			return 1;
		case SENTIDO_RETORNO:
			return 2;
		default:
			return null;
		}
	}

	public static CourseType asEnum(Integer value) {
		if (SENTIDO_IDA.getIntValue() == value) {
			return SENTIDO_IDA;
		}
		if (SENTIDO_RETORNO.getIntValue() == value) {
			return SENTIDO_RETORNO;
		}
		return null;
	}
}
