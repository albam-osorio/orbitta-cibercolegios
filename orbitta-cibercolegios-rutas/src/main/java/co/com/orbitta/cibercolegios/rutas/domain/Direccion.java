package co.com.orbitta.cibercolegios.rutas.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "DIRECCIONES")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Direccion extends AuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_DIRECCION")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@Column(name = "ID_INSTITUCION", nullable = false)
	@NotNull
	private int institucionId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTADO_DIRECCION", nullable = false)
	@NotNull
	private EstadoDireccion estado;

	@Column(name = "ID_PAISES", nullable = false)
	private int paisId;
	
	@Column(name = "ID_ESTADO", nullable = false)
	private int departamentoId;
	
	@Column(name = "ID_CIUDAD", nullable = false)
	@NotNull
	private int ciudadId;

	@Column(name = "DIRECCION", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String direccion;

	@Column(name = "X", nullable = true, precision = 9, scale = 6)
	private BigDecimal x;

	@Column(name = "Y", nullable = true, precision = 9, scale = 6)
	private BigDecimal y;
}