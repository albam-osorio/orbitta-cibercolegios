package co.com.orbitta.cibercolegios.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.core.data.jpa.domain.ReadOnlyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PASAJEROS")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Pasajero extends ReadOnlyEntity<Integer> {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@Column(name = "SECUENCIA", nullable = false)
	private int secuencia;

	@Column(name = "ID_USUARIO", nullable = false)
	@NotNull
	private int usuarioId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_DIRECCIONES_X_USUARIO", nullable = false)
	@NotNull
	private Direccion direccion;

	@Builder
	public Pasajero(Integer id, @NotNull Ruta ruta, int secuencia, @NotNull int usuarioId,
			@NotNull Direccion direccion) {
		super(id);
		this.ruta = ruta;
		this.secuencia = secuencia;
		this.usuarioId = usuarioId;
		this.direccion = direccion;
	}
}
