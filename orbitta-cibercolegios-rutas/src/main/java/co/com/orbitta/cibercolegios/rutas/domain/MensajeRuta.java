package co.com.orbitta.cibercolegios.rutas.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mensajeRuta")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MensajeRuta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	@Column(name = "mensaje", length = 200, nullable = false)
	@NotNull
	@Size(max = 200)
	private String mensaje;
	
	@Column(name = "origenMensaje", nullable = false)
	private int origenMensaje;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "padreId")
	private Usuario padre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "monitorId")
	private Usuario monitor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutaId")
	private Ruta ruta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadoId")
	private EstadoRuta estado;

}
