package co.com.orbitta.cibercolegios.rutas.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.cibercolegios.enums.EmisorMensajeEnum;
import co.com.orbitta.cibercolegios.rutas.domain.readonly.Ruta;
import co.com.orbitta.cibercolegios.rutas.domain.readonly.Usuario;
import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MENSAJE_RUTA")
@SequenceGenerator(name = "mensaje_sequence", allocationSize = 1, sequenceName = "SEQ_MENSAJE_RUTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_MENSAJE_RUTA"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CONVERSACION_RUTA", nullable = false)
	@NotNull
	private Conversacion conversacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_MONITOR", nullable = false)
	@NotNull
	private Usuario monitor;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORIGEN", length = 50, nullable = false)
	@NotNull
	private EmisorMensajeEnum origen;

	@Column(name = "FECHA_HORA", nullable = false)
	@NotNull
	private LocalDateTime fechaHora;

	@Column(name = "MENSAJE", length = 200, nullable = false)
	@NotNull
	@Size(max = 200)
	private String mensaje;
}
