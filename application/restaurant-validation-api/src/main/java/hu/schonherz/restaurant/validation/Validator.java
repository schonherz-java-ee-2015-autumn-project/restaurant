package hu.schonherz.restaurant.validation;

import java.io.Serializable;

public interface Validator<T> extends Serializable {
	void validate(T object) throws ViolationException;
}
