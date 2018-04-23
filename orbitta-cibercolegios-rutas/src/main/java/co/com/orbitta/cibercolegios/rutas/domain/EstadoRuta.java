package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.enums.TipoEstadoRutaEnum;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESTADO_RUTA")
@SequenceGenerator(name = "estado_ruta_sequence", allocationSize = 1, sequenceName = "SEQ_ESTADO_RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_ESTADO_RUTA"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EstadoRuta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", length = 50, nullable = false)
	@NotNull
	private TipoEstadoRutaEnum tipo;

	@Column(name = "PREDETERMINADO", nullable = false)
	private boolean predeterminado;

	@Column(name = "DESCRIPCION", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String descripcion;
}
