package co.com.orbitta.cibercolegios.rutas.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.com.orbitta.core.data.jpa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "logUsuario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogUsuario extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logId")
	private Log log;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioRutaId")
	private UsuarioRuta usuarioRuta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadoId")
	private Estado estado;

}
