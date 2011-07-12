package ch.ralscha.e4ds.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.ralscha.e4ds.entity.Role;
import ch.ralscha.e4ds.entity.User;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private ImmutableSet<GrantedAuthority> authorities;
	private String password;
	private String username;
	private boolean enabled;

	public JpaUserDetails(User user) {
		this.password = user.getPasswordHash();
		this.username = user.getUserName();
		this.enabled = user.isEnabled();

		Builder<GrantedAuthority> builder = ImmutableSet.builder();
		for (Role role : user.getRoles()) {
			builder.add(new SimpleGrantedAuthority(role.getName()));
		}

		this.authorities = builder.build();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
