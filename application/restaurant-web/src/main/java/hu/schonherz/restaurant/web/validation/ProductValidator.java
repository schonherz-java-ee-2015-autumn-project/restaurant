package hu.schonherz.restaurant.web.validation;

import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.schonherz.restaurant.service.vo.ProductVo;
import hu.schonherz.restaurant.validation.rule.RuleValidator;
import hu.schonherz.restaurant.web.validation.rules.ProductNameRequiredRule;
import hu.schonherz.restaurant.web.validation.rules.ProductPriceNotNegativeRule;
import hu.schonherz.restaurant.web.validation.rules.ProductPriceRequiredRule;

@ViewScoped
@ManagedBean(name = "productValidator")
public class ProductValidator extends RuleValidator<ProductVo> {

	private static final long serialVersionUID = 1L;

	public ProductValidator() {
		super(Arrays.asList(new ProductNameRequiredRule(), new ProductPriceRequiredRule(),
				new ProductPriceNotNegativeRule()));
	}

}
