package learn.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	   private static final long serialVersionUID = 1L;

	    private Integer id;
	    private String username;
	    private String email;
	    private String password;
	    private Date birthday;
	    private String address;
	    private String phone;
	    private String sex;
	    private boolean enabled;

	    public User(String username, String password, String email) {
	        this.username = username;
	        this.password = password;
	        this.email = email;

	    }
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        // Trả về quyền của người dùng. Nếu bạn không quản lý role, có thể trả về rỗng
	        return Collections.emptyList();
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
	        return this.enabled;
	    }
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
