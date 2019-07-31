package com.tbf.cibercolegios.api.routes.models.routes;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class RutaEditDialogAssignViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private RutaEditTrayectoViewModel trayecto;

	private boolean miembro;

	private boolean adicionado;

	private boolean removido;
}
