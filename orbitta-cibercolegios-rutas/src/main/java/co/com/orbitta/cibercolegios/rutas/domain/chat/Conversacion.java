package co.com.orbitta.cibercolegios.rutas.domain.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import co.com.orbitta.commons.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CONVERSACIONES")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Conversacion extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "conversaciones_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "conversaciones_gen", sequenceName = "CONVERSACIONES_SEQ", allocationSize = 1)
	@Column(name = "ID_CONVERSACION")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_ACUDIENTE", nullable = false)
	@NotNull
	private int acudienteId;

	@Column(name = "ID_ESTUDIANTE", nullable = false)
	@NotNull
	private int estudianteId;

	@Builder
	public Conversacion(Integer id, @NotNull int acudienteId, @NotNull int estudianteId) {
		super();
		this.id = id;
		this.acudienteId = acudienteId;
		this.estudianteId = estudianteId;
	}
}