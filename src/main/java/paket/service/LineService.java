package paket.service;

import java.util.List;

import org.springframework.data.domain.Page;

import paket.model.Line;
import paket.model.Reservation;
import paket.model.User;

public interface LineService {
	
	List<Line> findAll();
	Page<Line> findAll(int pageNum);
	Line getOne(Long id);
	Line save(Line line);
	Line delete(Long id);
	
	Page<Line> search(String destination, String carrierName, Double ticketPrice, int pageNum);
	
	Line alreadyExists(String destination, Long carrierId, String departureTime );
	
	Reservation reserve(Long id, User user);
	
}
