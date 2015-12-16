package hu.schonherz.restaurant.service.converter;

import java.io.Serializable;
import java.util.List;

public interface Converter<S extends Serializable, T extends Serializable> extends Serializable {

	T convert(S source);

	S reverse(T source);

	List<T> convert(List<S> sources);

	List<S> reverse(List<T> sources);
}
