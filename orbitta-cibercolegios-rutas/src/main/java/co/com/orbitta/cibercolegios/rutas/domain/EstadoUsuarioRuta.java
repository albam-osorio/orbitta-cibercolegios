package co.com.orbitta.cibercolegios.rutas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "estadoUsuarioRuta")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EstadoUsuarioRuta extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "descripcion", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String descripcion;

	@Column(name = "aplica_ida", nullable = false)
	private boolean aplicaIda;

	@Column(name = "aplica_retorno", nullable = false)
	private boolean aplicaRetorno;
}