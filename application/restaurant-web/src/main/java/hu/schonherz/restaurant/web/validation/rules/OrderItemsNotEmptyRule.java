package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class OrderItemsNotEmptyRule implements ValidationRule<OrderVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(OrderVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getItems().isEmpty()) {
			Violation violation = new Violation("products_empty", "");
			res.add(violation);
		}

		return res;
	}

}
