package hu.schonherz.restaurant.web.delivery.faceconverter;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import hu.schonherz.restaurant.service.vo.OrderVo;

@ManagedBean(name = "orderConverter")
@RequestScoped
public class OrderConverter implements Converter, Serializable {

	@EJB
	private OrderServiceLocal orderService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return orderService.getOrderById(Long.valueOf(value));
			} catch (NumberFormatException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "#{out.error}", ""));
			}
		}
		return null;

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return value == null ? null : String.valueOf(((OrderVo) value).getId());
	}

}
