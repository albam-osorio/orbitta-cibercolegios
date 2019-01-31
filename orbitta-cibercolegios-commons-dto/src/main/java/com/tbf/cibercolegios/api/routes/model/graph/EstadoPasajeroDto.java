package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.routes.model.enums.PassengerTypeStatus;

import co.com.orbitta.core.dto.AuditableEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoPasajeroDto extends AuditableEntityDto<Integer> {

	@NotNull
	@Size(max = 50)
	private String descripcion;

	@NotNull
	private PassengerTypeStatus tipo;

	private boolean aplicaSentidoIda;

	private boolean aplicaSentidoRetorno;

	public boolean isFinalizado() {
		return PassengerTypeStatus.FIN.equals(tipo);
	}
}