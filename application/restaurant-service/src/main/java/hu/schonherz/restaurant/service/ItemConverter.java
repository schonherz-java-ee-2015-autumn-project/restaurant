package hu.schonherz.restaurant.service;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.entities.Item;
import hu.schonherz.restaurant.service.vo.ItemVo;

@Service("itemConverter")
public class ItemConverter extends EntityVoConverter<ItemVo, Item> {

	private static final long serialVersionUID = 1L;

	private static Mapper mapper = new DozerBeanMapper();

	@Override
	public ItemVo toVo(Item entity) {
		if (entity == null) {
			return null;
		}
		return mapper.map(entity, ItemVo.class);
	}

	@Override
	public Item toEntity(ItemVo vo) {
		if (vo == null) {
			return null;
		}
		return mapper.map(vo, Item.class);
	}

}
