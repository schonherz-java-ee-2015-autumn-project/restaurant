package hu.schonherz.restaurant.validation;

public interface Validator<T> {
	void validate(T object) throws ViolationException;
}
