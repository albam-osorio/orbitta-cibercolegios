package com.tbf.cibercolegios.api.core.model.graph;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.tbf.cibercolegios.api.core.domain.ObjectAuditableByTime;
import com.tbf.cibercolegios.api.core.domain.VersionableObject;

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
