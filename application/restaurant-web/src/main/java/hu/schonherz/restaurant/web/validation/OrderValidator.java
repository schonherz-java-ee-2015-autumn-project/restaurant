
package hu.schonherz.restaurant.web.validation;

import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.schonherz.restaurant.service.vo.OrderVo;
import hu.schonherz.restaurant.validation.rule.RuleValidator;
import hu.schonherz.restaurant.web.validation.rules.OrderAddressRequiredRule;
import hu.schonherz.restaurant.web.validation.rules.OrderDeadlineNotBeforeNowRule;
import hu.schonherz.restaurant.web.validation.rules.OrderDeadlineRequiredRule;
import hu.schonherz.restaurant.web.validation.rules.OrderItemsNotEmptyRule;

@ViewScoped
@ManagedBean(name = "orderValidator")
public class OrderValidator extends RuleValidator<OrderVo> {

	private static final long serialVersionUID = 1L;

	public OrderValidator() {
		super(Arrays.asList(new OrderAddressRequiredRule(), new OrderDeadlineRequiredRule(),
				new OrderDeadlineNotBeforeNowRule(), new OrderItemsNotEmptyRule()));
	}

}

