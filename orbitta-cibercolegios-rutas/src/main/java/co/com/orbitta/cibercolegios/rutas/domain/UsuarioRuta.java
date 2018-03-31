package co.com.orbitta.cibercolegios.rutas.domain;

import java.time.LocalDate;

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
@Table(name = "usuarioRuta")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRuta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rutaId")
	private Ruta ruta;

	@Column(name = "fecha")
	private LocalDate fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "direccionUsuarioId")
	private DireccionUsuario direccionUsuario;
}
