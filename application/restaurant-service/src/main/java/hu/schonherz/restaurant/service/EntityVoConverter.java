package hu.schonherz.restaurant.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.entities.BaseEntity;

public abstract class EntityVoConverter<V extends Serializable, E extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract V toVo(E entity);

	public abstract E toEntity(V vo);

	public final List<V> toVo(List<E> entities) {
		List<V> res = new ArrayList<>();
		if (entities == null) {
			return res;
		}

		for (E entity : entities) {
			res.add(toVo(entity));
		}

		return res;
	}

	public final List<E> toEntity(List<V> vos) {
		List<E> res = new ArrayList<>();
		if (vos == null) {
			return res;
		}

		for (V vo : vos) {
			res.add(toEntity(vo));
		}

		return res;
	}

}
