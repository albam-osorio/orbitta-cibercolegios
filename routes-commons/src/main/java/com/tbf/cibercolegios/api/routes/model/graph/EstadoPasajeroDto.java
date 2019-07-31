package com.tbf.cibercolegios.api.routes.model.graph;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;
import com.tbf.cibercolegios.api.model.routes.enums.PassengerTypeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class EstadoPasajeroDto extends SimpleAuditableEntityDto<Integer> {
	
	public static final int ESTADO_INACTIVO = 0;
	
	@NotNull
	@Size(max = 50)
	private String descripcion;

	@NotNull
	private PassengerTypeStatus tipo;

	private boolean aplicaSentidoIda;

	private boolean aplicaSentidoRetorno;
	
	private boolean aplicaGeoCodificacion;

	public boolean isFinalizado() {
		return PassengerTypeStatus.FIN.equals(tipo);
	}
}