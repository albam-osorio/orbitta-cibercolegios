package co.com.orbitta.cibercolegios.rutas.domain.chat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import co.com.orbitta.cibercolegios.rutas.domain.Ruta;
import co.com.orbitta.cibercolegios.rutas.enums.EmisorMensajeEnum;
import co.com.orbitta.commons.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MENSAJES")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Mensaje extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(generator = "mensajes_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "mensajes_gen", sequenceName = "MENSAJES_SEQ", allocationSize = 1)
	@Column(name = "ID_MENSAJE")
	@Setter(value = AccessLevel.PROTECTED)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CONVERSACION", nullable = false)
	@NotNull
	private Conversacion conversacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_RUTA", nullable = false)
	@NotNull
	private Ruta ruta;

	@Column(name = "ID_MONITOR", nullable = false)
	@NotNull
	private int monitorId;

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

	@Builder
	public Mensaje(Integer id, @NotNull Conversacion conversacion, @NotNull Ruta ruta, @NotNull int monitorId,
			@NotNull EmisorMensajeEnum origen, @NotNull LocalDateTime fechaHora,
			@NotNull @Size(max = 200) String mensaje) {
		super();
		this.id = id;
		this.conversacion = conversacion;
		this.ruta = ruta;
		this.monitorId = monitorId;
		this.origen = origen;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
	}
}
