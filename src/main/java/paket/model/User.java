package paket.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import paket.model.Reservation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty; 

@Entity
@Table(name="user")
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a username")
	@Length(min = 5, message = "*Your user name must have at least 5 characters")
	private String username;
	
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a firstname")
	private String firstname;
	
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a lastname")
	private String lastname;
	
	@Column(nullable=false, unique=true)
	@NotEmpty(message = "*Please provide an email")
	@Email(message="{errors.invalid_email}")
	private String email;
	
	@Column(nullable=false)
	@NotEmpty
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	private String password;
	
	@Column(nullable=false)
	@NotEmpty
	private String city;
	
	@Column(nullable=false)
	@NotEmpty
	private String address;
	
	
	@Column(nullable=false)
	@NotEmpty
	@Length(min = 9,  message = "*Your phone number  must have at least 9 characters")
	private String phone;
	
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();


	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	List<Reservation> reservations = new ArrayList<>();

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
		
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	
	
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
	}
	public void removeRole(Role role) {
		roles.remove(role);
		role.getUsers().remove(this);
    }
	
	
	
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	public void addRezervacija(Reservation reservation) {
		if(reservation.getUser() != this) {
			reservation.setUser(this);
		}
		reservations.add(reservation);
	}
	
	
	
	
}
