package co.com.orbitta.cibercolegios.ciber.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.com.orbitta.commons.dto.EntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class InstitucionDto extends EntityDto<Integer> {

	@NotNull
	@Size(max = 100)
	private String nombre;

	@Builder
	public InstitucionDto(Integer id, @NotNull @Size(max = 100) String nombre) {
		super(id);
		this.nombre = nombre;
	}
}
