package paket.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name="line")
public class Line {
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column(nullable=false)
	@PositiveOrZero(message = "*Only positive number")
	private Integer numberOfSeats;
	@Column(nullable=false) 
	@Positive(message = "*Only positive number")
	private Double ticketPrice;
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a vreme polaska")
	private String departureTime;
	@Column(nullable=false)
	@NotEmpty(message = "*Please provide a destinacija")
	private String destination;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Carrier carrier;
	
	@OneToMany(mappedBy="line", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Reservation> reservations = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
		if(!carrier.getLines().contains(this)){
			carrier.getLines().add(this);
		}
	}

	
	

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setRezervacije(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public void addReservation(Reservation reservation) {
		if(reservation.getLine() != this) {
			reservation.setLine(this);
		}
		reservations.add(reservation);
	}


}
