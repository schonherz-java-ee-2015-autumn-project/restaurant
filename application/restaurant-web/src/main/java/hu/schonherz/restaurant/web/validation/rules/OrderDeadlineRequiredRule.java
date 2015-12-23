package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class OrderDeadlineRequiredRule implements ValidationRule<OrderVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(OrderVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getDeadline() == null) {
			Violation violation = new Violation("order_deadline_required", "");
			res.add(violation);
		}

		return res;
	}

}
