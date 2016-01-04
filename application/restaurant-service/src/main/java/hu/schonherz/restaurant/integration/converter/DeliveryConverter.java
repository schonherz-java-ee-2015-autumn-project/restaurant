package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

public class DeliveryConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemoteCargoDTO toRemote(DeliveryVo vo) {
		if (vo == null) {
			return null;
		}
		RemoteCargoDTO res = new RemoteCargoDTO();

		res.setId(vo.getId());
		res.setRestaurantId(vo.getRestaurant().getGlobalId());
		// TODO
		// res.setOrders(OrderConverter.toRemote(vo.getOrders()));
		res.setState(DeliveryStateConverter.toRemote(vo.getDeliveryState()));
		res.setCourierId(null);

		return res;
	}

	public static List<RemoteCargoDTO> toRemote(List<DeliveryVo> vos) {
		List<RemoteCargoDTO> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (DeliveryVo delivery : vos) {
				res.add(toRemote(delivery));
			}
		}

		return res;
	}

	public static DeliveryVo toLocal(RemoteCargoDTO rdto) {
		if (rdto == null) {
			return null;
		}

		DeliveryVo res = new DeliveryVo();

		res.setDeliveryState(DeliveryStateConverter.toLocal(rdto.getState()));
		res.setId(rdto.getId());
		res.setOrders(OrderConverter.toLocal(rdto.getOrders()));

		return res;
	}

	public static List<DeliveryVo> toLocal(List<RemoteCargoDTO> vos) {
		List<DeliveryVo> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (RemoteCargoDTO cargo : vos) {
				res.add(toLocal(cargo));
			}
		}

		return res;
	}

}
