package hu.schonherz.restaurant.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.converter.Converter;

public abstract class DefaultConverter<S extends Serializable, T extends Serializable> implements Converter<S, T> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<T> convert(List<S> sources) {
		List<T> res = new ArrayList<>();
		for (S source : sources) {
			res.add(convert(source));
		}
		return res;
	}

	@Override
	public List<S> reverse(List<T> sources) {
		List<S> res = new ArrayList<>();
		for (T source : sources) {
			res.add(reverse(source));
		}
		return res;
	}

}
