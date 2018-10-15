package co.com.orbitta.commons.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import co.com.orbitta.core.domain.ObjectAuditableByTime;
import co.com.orbitta.core.domain.VersionableObject;
import co.com.orbitta.core.dto.EntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public abstract class SimpleAuditableEntityDto<ID> extends EntityDto<ID>
		implements VersionableObject, ObjectAuditableByTime {

	@NumberFormat
	private int version;

	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaCreacion;

	@DateTimeFormat(style = "M-")
	private LocalDateTime fechaModificacion;
}
