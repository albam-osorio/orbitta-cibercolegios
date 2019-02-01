package com.tbf.cibercolegios.api.core.model.graph;

import com.tbf.cibercolegios.api.core.domain.IdentifiedDomainObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityDto<ID> implements IdentifiedDomainObject<ID> {

	private ID id;

}
