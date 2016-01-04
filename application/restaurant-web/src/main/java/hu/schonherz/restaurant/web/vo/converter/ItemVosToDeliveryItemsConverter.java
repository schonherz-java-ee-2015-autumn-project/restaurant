package hu.schonherz.restaurant.web.vo.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.web.vo.DeliveryItem;

public class ItemVosToDeliveryItemsConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<DeliveryItem> toDeliveryItems(List<ItemVo> items) {
		Map<String, Integer> tmpMap = fillMap(items);
		List<DeliveryItem> res = getFromMap(tmpMap);
		return res;
	}

	private static Map<String, Integer> fillMap(List<ItemVo> items) {
		Map<String, Integer> res = new TreeMap<>();

		for (ItemVo itemVo : items) {
			Integer count;

			if (res.get(itemVo.getProduct().getName()) == null) {
				res.put(itemVo.getProduct().getName(), 0);
			}

			count = res.get(itemVo.getProduct().getName());
			res.put(itemVo.getProduct().getName(), count + itemVo.getQuantity());

		}

		return res;
	}

	private static List<DeliveryItem> getFromMap(Map<String, Integer> tmpMap) {
		List<DeliveryItem> res = new ArrayList<>(tmpMap.size());
		for (String name : tmpMap.keySet()) {
			res.add(new DeliveryItem(name, tmpMap.get(name)));
		}
		return res;
	}

}
