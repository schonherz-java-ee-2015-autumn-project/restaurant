package hu.schonherz.restaurant.service;

import java.util.List;

import hu.schonherz.restaurant.service.vo.ItemVo;

public interface ItemServiceLocal {

	List<ItemVo> getItemsByDeliveryId(Long id);

}
