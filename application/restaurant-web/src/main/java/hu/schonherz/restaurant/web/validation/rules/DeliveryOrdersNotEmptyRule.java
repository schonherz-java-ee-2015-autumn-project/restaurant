package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class DeliveryOrdersNotEmptyRule implements ValidationRule<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(DeliveryVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getOrders().isEmpty()) {
			Violation violation = new Violation("orders_empty", "");
			res.add(violation);
		}

		return res;
	}

}
