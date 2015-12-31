package hu.schonherz.restaurant.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.dao.ItemDao;
import hu.schonherz.restaurant.entities.Item;
import hu.schonherz.restaurant.service.EntityVoConverter;
import hu.schonherz.restaurant.service.ItemServiceLocal;
import hu.schonherz.restaurant.service.ItemServiceRemote;
import hu.schonherz.restaurant.service.vo.ItemVo;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(ItemServiceLocal.class)
@Remote(ItemServiceRemote.class)
public class ItemService implements ItemServiceLocal, ItemServiceRemote {

	@Autowired
	private EntityVoConverter<ItemVo, Item> itemConverter;

	@Autowired
	private ItemDao itemDao;

	@Override
	public List<ItemVo> getItemsByDeliveryId(Long id) {
		return itemConverter.toVo(itemDao.findByDeliveryId(id));
	}

}
