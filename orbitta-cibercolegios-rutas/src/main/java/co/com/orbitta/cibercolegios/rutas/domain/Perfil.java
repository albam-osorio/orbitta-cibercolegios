package co.com.orbitta.cibercolegios.rutas.domain;

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
@Table(name = "perfil")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Perfil extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoPerfilId")
	private TipoPerfil tipoPerfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	
	@Column(name = "perfilPadreId", nullable = false)
	private int perfilPadreId;

	@Column(name = "perfilMadreId", nullable = false)
	private int  perfilMadreId;
}
