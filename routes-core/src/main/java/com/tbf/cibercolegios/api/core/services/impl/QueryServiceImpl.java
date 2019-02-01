package com.tbf.cibercolegios.api.core.services.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tbf.cibercolegios.api.core.data.jpa.entities.AuditableEntity;
import com.tbf.cibercolegios.api.core.data.jpa.entities.SimpleAuditableEntity;
import com.tbf.cibercolegios.api.core.domain.IdentifiedDomainObject;
import com.tbf.cibercolegios.api.core.model.graph.AuditableEntityDto;
import com.tbf.cibercolegios.api.core.model.graph.SimpleAuditableEntityDto;
import com.tbf.cibercolegios.api.core.services.api.QueryService;

import lombok.val;

public abstract class QueryServiceImpl<E extends IdentifiedDomainObject<ID>, M extends IdentifiedDomainObject<ID>, ID>
		implements QueryService<M, ID> {

	public QueryServiceImpl() {
		super();
	}

	protected abstract JpaRepository<E, ID> getRepository();

	@Override
	public M findOneById(ID id) {
		E entity = findOneEntityById(id);

		val result = asModel(entity);
		return result;
	}

	@Override
	public Optional<M> findById(ID id) {
		Optional<E> optional = findEntityById(id);

		val result = asModel(optional);
		return result;
	}

	@Override
	public List<M> findAllById(List<ID> ids) {
		List<E> entities;

		if (ids.isEmpty()) {
			entities = new ArrayList<E>();
		} else {
			entities = getRepository().findAllById(ids);
		}

		val result = asModels(entities);
		return result;
	}

	protected E findOneEntityById(ID id) {
		val result = getRepository().findById(id);
		if (!result.isPresent()) {
			throw new EntityNotFoundException("id = " + String.valueOf(id));
		}
		return result.get();
	}

	protected Optional<E> findEntityById(ID id) {
		val result = getRepository().findById(id);
		return result;
	}

	protected abstract M asModel(E entity);

	protected Optional<M> asModel(Optional<E> optional) {
		Optional<M> result;
		if (optional.isPresent()) {
			result = Optional.of(asModel(optional.get()));
		} else {
			result = Optional.empty();
		}
		return result;
	}

	protected List<M> asModels(Collection<E> entities) {
		val result = entities.stream().map(e -> asModel(e)).collect(toList());
		return result;
	}

	protected Slice<M> asModels(Slice<E> slice, Pageable pageable) {
		val models = asModels(slice.getContent());
		val result = new SliceImpl<>(models, pageable, slice.hasNext());
		return result;
	}

	protected void mapModel(SimpleAuditableEntity<ID> entity, SimpleAuditableEntityDto<ID> model) {
		model.setId(entity.getId());
		model.setVersion(entity.getVersion());
		model.setFechaCreacion(entity.getFechaCreacion());
		model.setFechaModificacion(entity.getFechaModificacion());
	}

	protected void mapModel(AuditableEntity<ID> entity, AuditableEntityDto<ID> model) {
		SimpleAuditableEntity<ID> e = entity;
		SimpleAuditableEntityDto<ID> m = model;
		mapModel(e, m);
		model.setCreadoPor(entity.getCreadoPor());
		model.setModificadoPor(entity.getModificadoPor());
	}

	abstract protected M newModel();
}
