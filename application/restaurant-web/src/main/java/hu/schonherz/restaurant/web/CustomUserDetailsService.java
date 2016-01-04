package hu.schonherz.restaurant.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.service.UserServiceLocal;
import hu.schonherz.restaurant.service.vo.RoleVo;
import hu.schonherz.restaurant.service.vo.UserVo;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@EJB
	UserServiceLocal userService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Validate.notNull(username);
		UserVo user;
		try {
			user = userService.findUserByName(username);

			if (user == null || user.getBanned() || user.getRestaurant().getBanned()) {
				throw new UsernameNotFoundException(username);
			}
			List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

			return buildUserForAuthentication(user, authorities);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}

	}

	private User buildUserForAuthentication(UserVo user, List<GrantedAuthority> authorities) {
		Validate.notNull(user);
		Validate.noNullElements(authorities);
		return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<RoleVo> userRoles) {
		Validate.noNullElements(userRoles);
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (RoleVo userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}


}