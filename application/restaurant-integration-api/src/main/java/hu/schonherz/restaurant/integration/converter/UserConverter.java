package hu.schonherz.restaurant.integration.converter;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import hu.schonherz.administrator.WebUserDTO;
import hu.schonherz.restaurant.service.vo.UserVo;

public class UserConverter {

	public static UserVo toVo(WebUserDTO user){
		UserVo userVo = new UserVo();
		userVo.setBanned(user.isRemove());
		userVo.setId(user.getId());
		userVo.setModDate(user.getModdate().toGregorianCalendar().getTime());
		userVo.setUsername(user.getUsername());
		userVo.setName(user.getName());
		userVo.setPassword(user.getPassword());		
		return userVo;
	}
	
	public static WebUserDTO toWebDTO(UserVo user){
		WebUserDTO userDto = new WebUserDTO();

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(user.getModDate());
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userDto.setId(user.getId());
		userDto.setModdate(date2);
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		userDto.setRemove(user.getBanned());
		userDto.setUsername(user.getUsername());
		
		return userDto;
	}
	
	public static List<UserVo> toVo(List<WebUserDTO> users){
		List<UserVo> result = new ArrayList<UserVo>();
		for (WebUserDTO user :users){
			result.add(toVo(user));
		}
		return result;
	}
	
	public static List<WebUserDTO> toWebDto(List<UserVo> users){
		List<WebUserDTO> result = new ArrayList<WebUserDTO>();
		for (UserVo user :users){
			result.add(toWebDTO(user));
		}
		return result;
	}
	
}
