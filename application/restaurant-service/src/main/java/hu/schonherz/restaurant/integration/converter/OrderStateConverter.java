package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;

import hu.schonherz.administrator.DeliveryStateWeb;
import hu.schonherz.restaurant.service.vo.OrderState;

public class OrderStateConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static DeliveryStateWeb toRemote(OrderState orderState) {
		switch (orderState) {
		case DELIVERED:
			return DeliveryStateWeb.DELIVERED;
		case FAILED:
			return DeliveryStateWeb.FAILED;
		case IN_PROGRESS:
			return DeliveryStateWeb.IN_PROGRESS;
		}

		return null;
	}

	public static OrderState toLocal(DeliveryStateWeb orderState) {
		switch (orderState) {
		case DELIVERED:
			return OrderState.DELIVERED;
		case FAILED:
			return OrderState.FAILED;
		case IN_PROGRESS:
			return OrderState.IN_PROGRESS;
		}

		return null;
	}

}
