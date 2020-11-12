package paket.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import paket.model.Role;
import paket.model.User;
import paket.repository.RoleRepository;
import paket.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
	
	public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
	
	public User checkByEmail(String email) {
		return userRepository.checkByEmail(email);
		
	}; 
	
	
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    
	public User getOne(Long id) {
		return userRepository.getOne(id);
	};
	
	public List<User> findAll(){
		return userRepository.findAll();
	};
	
	public Page<User> findAll(int pageNum){
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return userRepository.findAll(pageable);
	};
	
	public User delete(Long id) {
		User user = userRepository.getOne(id);
		if(user!=null) {
			userRepository.delete(user);
		}
		return user;
	};
	
	public Page<User> search( @Param("username") String username,  @Param("mesto") String city,  int pageNum){
		if( username != null) {
			username = '%' + username + '%';
		}
		if(city != null) {
			city = '%' + city + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return userRepository.search(username, city, pageable);
	};
    
    
    
    
    
    
	
}
