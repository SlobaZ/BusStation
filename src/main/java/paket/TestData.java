package paket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import paket.model.Line;
import paket.model.Carrier;
import paket.model.Role;
import paket.model.User;
import paket.repository.RoleRepository;
import paket.security.UserService;
import paket.service.LineService;
import paket.service.CarrierService;

@Component
public class TestData {
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private LineService lineService;
	
	@Autowired
	private CarrierService carrierService;
	
	
	@PostConstruct
	public void init() {
		
		Role role1 = new Role();
		role1.setName("ROLE_ADMIN");
		role1 = roleRepository.save(role1);
		
		Role role2 = new Role();
		role2.setName("ROLE_USER");
		role2 = roleRepository.save(role2);
		
		User user1 = new User();
		user1.setUsername("Admin");
		user1.setFirstname("Administrator");
		user1.setLastname("Administrator");
		user1.setEmail("admin@gmail.com");
		user1.setPassword("$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS");
		user1.setCity("AdminCity");
		user1.setAddress("AdminAddress");
		user1.setPhone("064112233");
		user1 = userService.save(user1);

		User user2 = new User();
		user2.setUsername("VasaVasic");
		user2.setFirstname("Vasa");
		user2.setLastname("Vasic");
		user2.setEmail("vasa@gmail.com");
		user2.setPassword("$2a$10$bwQVsArIQJtmkPckmfRZGOEMAGBXcHaziXIEgstc9ePsPG6sYEFK.");
		user2.setCity("Beograd");
		user2.setAddress("Vojvode Milenka 30");
		user2.setPhone("065112233");
		user2 = userService.save(user2);
		
		User user3 = new User();
		user3.setUsername("PeraPeric");
		user3.setFirstname("Pera");
		user3.setLastname("Peric");
		user3.setEmail("pera@gmail.com");
		user3.setPassword("$2a$10$Locf9fRBO84ejEc/bQFEROChVsd2ixjv4M2kYX6KSLp74iacK.N3W");
		user3.setCity("Nis");
		user3.setAddress("Cara Dusana 45");
		user3.setPhone("063112233");
		user3 = userService.save(user3);
		
	
		user1.addRole(role1);
		user1.addRole(role2);
		user2.addRole(role2);
		user3.addRole(role2);
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);

		
		Carrier c1 = carrierService.save(new Carrier("Subotica trans","Subotica","105056123"));
		Carrier c2 = carrierService.save(new Carrier("Nis ekspres", "Nis", "102364547"));
		Carrier c3 = carrierService.save(new Carrier("Lasta", "Beograd", "1569887"));
		Carrier c4 = carrierService.save(new Carrier("Farlang","Valjevo","17654233"));
		
		Line l1 = new Line();
		l1.setNumberOfSeats(48);
		l1.setTicketPrice(640.0); 
		l1.setDepartureTime("08:00");
		l1.setDestination("Novi Sad");
		l1.setCarrier(c1);
		lineService.save(l1);
		
		Line l2 = new Line();
		l2.setNumberOfSeats(51);
		l2.setTicketPrice(1200.0); 
		l2.setDepartureTime("09:00");
		l2.setDestination("Beograd");
		l2.setCarrier(c2);
		lineService.save(l2);
		
		Line l3 = new Line();
		l3.setNumberOfSeats(36);
		l3.setTicketPrice(300.0); 
		l3.setDepartureTime("10:00");
		l3.setDestination("Tavankut");
		l3.setCarrier(c3);
		lineService.save(l3);
		
		Line l4 = new Line();
		l4.setNumberOfSeats(44);
		l4.setTicketPrice(1100.0); 
		l4.setDepartureTime("11:00");
		l4.setDestination("Kraljevo");
		l4.setCarrier(c4);
		lineService.save(l4);
	}
	
}
