package com.tbf.cibercolegios.api.routes.models.users;

import java.io.Serializable;

import lombok.Data;

@Data
public class TrayectoViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int correlacion;

	private boolean activo;

	private boolean originalActivo;

	private Integer rutaId;

	private String rutaDescripcion;

	private Integer originalRutaId;
	
	private DireccionViewModel direccionAm;

	private DireccionViewModel direccionPm;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public String getRutaDescripcion() {
		String result;
		if (this.getRutaId() == null) {
			result = "No asignada";
		} else {
			result = this.rutaDescripcion;
		}
		return result;
	}

	public boolean isActivo() {
		if (this.activo) {
			this.activo = !isTrayectoVacio();
		}
		return this.activo;
	}

	public boolean isRutaModificado() {
		return equals(this.getOriginalRutaId(), this.getRutaId());
	}

	public boolean isActivoModificado() {
		return equals(this.isOriginalActivo(), this.isActivo());
	}

	public boolean isModificado() {
		boolean result = isRutaModificado() || isActivoModificado();

		if (!result && isTieneDireccionAm()) {
			result |= getDireccionAm().isModificado();
		}
		if (!result && isTieneDireccionPm()) {
			result |= getDireccionPm().isModificado();
		}

		return result;
	}

	private boolean equals(Object original, Object current) {
		boolean result = false;
		result |= (original == null) && (current != null);
		result |= (original != null) && (current == null);

		if ((original != null) && (current != null)) {
			result |= !(original.equals(current));
		}
		return result;
	}

	public boolean isEditable() {
		return !(this.isActivo() && this.getRutaId() != null);
	}

	public boolean isDeletable() {
		return !(this.isActivo() && this.getRutaId() != null);
	}

	public boolean contiene(DireccionViewModel direccion) {
		return (this.getDireccionAm() == direccion || this.getDireccionPm() == direccion);
	}

	public boolean isTrayectoCompleto() {
		return (isTieneDireccionAm() && isTieneDireccionPm());
	}

	public boolean isTrayectoVacio() {
		return (!isTieneDireccionAm() && !isTieneDireccionPm());
	}

	public boolean isTieneDireccionAm() {
		return this.getDireccionAm() != null;
	}

	public boolean isTieneDireccionPm() {
		return this.getDireccionPm() != null;
	}

}
