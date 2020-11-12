package paket.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="carrier")
public class Carrier {

	@Id
	@GeneratedValue 
	@Column
	private Long id;
	@Column(nullable=false, unique=true)
	private String name;
	@Column
	private String address;
	@Column(nullable=false)
	private String pib;
	
	@OneToMany(mappedBy="carrier", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Line> lines = new ArrayList<>();
	
	public Carrier() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	
	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	
	public void addLinija(Line line) {
		if(line.getCarrier() != this) {
			line.setCarrier(this);
		}
		lines.add(line);
	}


	public Carrier(String name, String address, String pib) {
		super();
		this.name = name;
		this.address = address;
		this.pib = pib;
	}

	
	
	
	
	
	
}
