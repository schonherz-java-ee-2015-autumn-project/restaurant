package hu.schonherz.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import hu.schonherz.restaurant.service.DeliveryServiceLocal;
import hu.schonherz.restaurant.service.DeliveryServiceRemote;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

@Stateless(mappedName = "DeliveryService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@Local(DeliveryServiceLocal.class)
@Remote(DeliveryServiceRemote.class)
public class MockingDeliveryService implements DeliveryServiceLocal, DeliveryServiceRemote {

	private static List<DeliveryVo> ds;

	static {
		ds = new ArrayList<>();
		ds.add(new DeliveryVo("a", "FOGLALT", "Maris", 4, 5.0));
		ds.add(new DeliveryVo("b", "FOGLALT", "Marisfdg", 4, 12.0));
		ds.add(new DeliveryVo("c", "SZABAD", "", 4, 45.0));
		ds.add(new DeliveryVo("d", "FOGLALT", "Maradggi", 4, 65.0));
		ds.add(new DeliveryVo("e", "FOGLALT", "Margsgi", 4, 85.0));
		ds.add(new DeliveryVo("f", "FOGLALT", "Mariggg", 4, 25.0));
		ds.add(new DeliveryVo("g", "FOGLALT", "Mari", 4, 35.0));
		ds.add(new DeliveryVo("h", "SZABAD", "", 4, 55.0));
		ds.add(new DeliveryVo("i", "FOGLALT", "Maafsdri", 4, 45.0));
		ds.add(new DeliveryVo("j", "FOGLALT", "Marafdsi", 4, 15.0));
	}

	@Override
	public List<DeliveryVo> getDeliveries(int i, int pageSize, String sortField, int dir, String filter,
			String filterColumnName) {
		int start = i * pageSize;
		return ds.subList(start, Math.min(start + pageSize, ds.size() - 1));
	}

	@Override
	public int getDeliveryCount() {
		return ds.size();
	}

	@Override
	public DeliveryVo getDeliveryByGuid(String guid) {
		for (DeliveryVo deliveryVo : ds) {
			if (deliveryVo.getGuid().equals(guid)) {
				return deliveryVo;
			}
		}
		return null;
	}

}
