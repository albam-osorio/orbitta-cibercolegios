package co.com.orbitta.cibercolegios.rutas.domain.readonly;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.core.data.jpa.domain.ReadOnlyEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO_RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_USUARIO_RUTA"))
@Getter
@Setter(value = AccessLevel.PROTECTED)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pasajero extends ReadOnlyEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	@NotNull
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_USUARIO_DIRECCION", nullable = false)
	@NotNull
	private Direccion direccion;

	@Column(name = "FECHA_HORA")
	private LocalDateTime fechaHora;

	@Column(name = "SECUENCIA", nullable = false)
	private int secuencia;
}
