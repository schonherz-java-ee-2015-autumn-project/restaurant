package hu.schonherz.restaurant.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.entities.BaseEntity;

public abstract class EntityVoConverter<V extends Serializable, E extends BaseEntity> extends DefaultConverter<V, E> {

	private static final long serialVersionUID = 1L;

	public final V toVo(E entity) {
		if (entity == null) {
			return null;
		}
		return reverse(entity);
	}

	public final E toEntity(V vo) {
		if (vo == null) {
			return null;
		}
		return convert(vo);
	}

	public final List<V> toVo(List<E> entities) {
		if (entities == null) {
			return new ArrayList<>();
		}
		return reverse(entities);
	}

	public final List<E> toEntity(List<V> vos) {
		if (vos == null) {
			return new ArrayList<>();
		}
		return convert(vos);
	}

}
