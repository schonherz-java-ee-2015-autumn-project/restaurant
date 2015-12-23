package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class OrderAddressRequiredRule implements ValidationRule<OrderVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(OrderVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getAddress() == null || object.getAddress().trim().equals("")) {
			Violation v = new Violation("order_address_required", "");
			res.add(v);
		}

		return res;
	}

}
