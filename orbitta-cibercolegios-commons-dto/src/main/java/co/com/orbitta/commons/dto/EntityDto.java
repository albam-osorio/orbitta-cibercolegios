package co.com.orbitta.commons.dto;

import java.io.Serializable;

import co.com.orbitta.core.domain.IdentifiedDomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityDto<ID extends Serializable> implements IdentifiedDomainObject<ID> {
	
	private ID id;

}
