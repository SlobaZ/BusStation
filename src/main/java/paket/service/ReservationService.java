package paket.service;

import java.util.List;

import org.springframework.data.domain.Page;
import paket.model.Reservation;

public interface ReservationService {
	
	List<Reservation> findAll();
	Page<Reservation> findAll(int pageNum);
	Reservation getOne(Long id);
	Reservation save(Reservation reservation);
	Reservation delete(Long id);
	
	Page<Reservation> search(String username, String destination, int pageNum);
	

}
