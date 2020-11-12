package paket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservation")
public class Reservation {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Line line;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
		if(line != null && !line.getReservations().contains(this)) {
			line.getReservations().add(this);
		}
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
		if(!user.getReservations().contains(this)){
			user.getReservations().add(this);
		}
	}
	
	
}
