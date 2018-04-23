package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Usuario;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CONVERSACION_RUTA")
@SequenceGenerator(name = "conversacion_sequence", allocationSize = 1, sequenceName = "SEQ_CONVERSACION_RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_CONVERSACION_RUTA"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Conversacion extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ACUDIENTE", nullable = false)
	@NotNull
	private Usuario acudiente;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTUDIANTE", nullable = false)
	@NotNull
	private Usuario estudiante;
}
