package co.com.orbitta.cibercolegios.rutas.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

import org.hibernate.annotations.DynamicUpdate;

import co.com.orbitta.commons.domain.AuditableEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PASAJEROS")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Pasajero extends AuditableEntity<Integer> {

	@Id
	@GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "default_gen", sequenceName = "DEFAULT_SEQ", allocationSize = 1)
	@Column(name = "ID_PASAJERO")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@Column(name = "ID_USUARIO", nullable = false, unique = true)
	@NotNull
	private int usuarioId;

	@Column(name = "SECUENCIA_IDA", nullable = false)
	private int secuenciaIda;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_DIRECCION_IDA", nullable = true)
	private Direccion direccionIda;

	@Column(name = "SECUENCIA_RETORNO", nullable = false)
	private int secuenciaRetorno;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_DIRECCION_RETORNO", nullable = true)
	private Direccion direccionRetorno;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_ESTADO_PASAJERO", nullable = true)
	private EstadoPasajero estado;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "PASAJEROS_ACUDIENTES", joinColumns = @JoinColumn(name = "ID_PASAJERO", nullable = false))
	@Column(name = "ID_ACUDIENTE")
	private List<Integer> acudientes;
}
