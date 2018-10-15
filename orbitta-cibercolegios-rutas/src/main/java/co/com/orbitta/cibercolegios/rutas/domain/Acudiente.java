package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import co.com.orbitta.commons.domain.AuditableEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ACUDIENTES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Acudiente extends AuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_ACUDIENTE")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_USUARIO", nullable = false)
	@NotNull
	private int usuarioId;

	@Column(name = "TOKEN", length = 200, nullable = true)
	@Size(max = 200)
	private String token;
}