package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.ProductVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class ProductNameRequiredRule implements ValidationRule<ProductVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(ProductVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getName() == null || object.getName().trim().equals("")) {
			Violation violation = new Violation("product_name_required", "");
			res.add(violation);
		}

		return res;
	}

}
