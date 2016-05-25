package com.ohadr.cbenchmarkr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import com.ohadr.cbenchmarkr.interfaces.BenchmarkrUserDetails;

public class BenchmarkrUserDetailsImpl implements BenchmarkrUserDetails 
{
	private boolean 	isMale;
	private Date 		lastLoginDate;
	private String firstName;
	private String lastName;
	private Collection<? extends GrantedAuthority> authorities;
	private String email;
	
	public BenchmarkrUserDetailsImpl(String username,
			String firstName, 
			String lastName,
			Collection<? extends GrantedAuthority> authorities,
			boolean isMale,
			Date lastLoginDate) 
	{
		this.email = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isMale = isMale;
		this.lastLoginDate = lastLoginDate;
	}

	@Override
	public boolean isMale()
	{
		return isMale;
	}

	@Override
	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}
	
    /**
     * copied from org.springframework.security.core.userdetails.User
     * 
     * @param authorities
     * @return
     */
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}

	@Override
	public String getUsername() 
	{
		return email;
	}

	@Override
	public String getFirstName()
	{
		return firstName;
	}

	@Override
	public String getLastName()
	{
		return lastName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}