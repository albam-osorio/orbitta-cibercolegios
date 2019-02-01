package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.RouteTypeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoRutaDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String descripcion;

	@NotNull
	private RouteTypeStatus tipo;

	private boolean predeterminado;

	public boolean isActivo() {
		switch (tipo) {
		case INICIO:
		case RECORRIDO:
			return true;
		default:
			return false;
		}
	}

	public boolean isFinalizado() {
		return RouteTypeStatus.FIN.equals(tipo);
	}
}