package hu.schonherz.restaurant.integration.converter;

import hu.schonherz.administrator.RemoteCargoState;
import hu.schonherz.restaurant.service.vo.DeliveryState;

public class DeliveryStateConverter {

	public static RemoteCargoState toRemote(DeliveryState deliveryState) {
		switch (deliveryState) {
		case DELIVERED:
			return RemoteCargoState.DELIVERED;
		case FREE:
			return RemoteCargoState.FREE;
		case RECEIVED:
			return RemoteCargoState.DELIVERING;
		case RESERVED:
			return RemoteCargoState.TAKEN;
		}
		return null;
	}

	public static DeliveryState toLocal(RemoteCargoState state) {
		switch (state) {
		case DELIVERED:
			return DeliveryState.DELIVERED;
		case DELIVERING:
			return DeliveryState.RECEIVED;
		case FREE:
			return DeliveryState.FREE;
		case TAKEN:
			return DeliveryState.RESERVED;
		}
		return null;
	}

}
