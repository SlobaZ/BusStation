package paket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import paket.model.Line;
import paket.model.Reservation;
import paket.model.User;
import paket.repository.LineRepository;
import paket.repository.ReservationRepository;
import paket.service.LineService;

@Service
public class JpaLineService implements LineService{

	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public List<Line> findAll() {
		return lineRepository.findAll();
	}
	
	@Override
	public Page<Line> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return lineRepository.findAll(pageable);
	}

	@Override
	public Line getOne(Long id) {
		return lineRepository.getOne(id); 
	}

	@Override
	public Line save(Line line) {
		return lineRepository.save(line);
	}

	@Override
	public Line delete(Long id) {
		Line line = lineRepository.getOne(id);
		if(line != null) {
			lineRepository.delete(line);
		}
		return line;
	}

	@Override
	public Page<Line> search(String destination, String carrierName, Double ticketPrice, int pageNum) {
		if(destination != null) {
			destination = '%' + destination + '%';
		}
		if(carrierName != null) {
			carrierName = '%' + carrierName + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return lineRepository.search(destination, carrierName, ticketPrice , pageable);
	}

	@Override
	public Reservation reserve(Long id, User user) {
		Line line = getOne(id);
		if(line != null) {
			Reservation reservation = null;
			if( line.getNumberOfSeats() > 0 ) {
				reservation = new Reservation();
				reservation.setUser(user);
				reservation.setLine(line);
				reservationRepository.save(reservation);
				
				line.setNumberOfSeats(line.getNumberOfSeats()-1);
				lineRepository.save(line);
			}
			return reservation;
		}
		else{
			throw new IllegalArgumentException("Tried to reserve a ticket for non-existant line");
		}
	}

	@Override
	public Line alreadyExists(String destination, Long carrierId, String departureTime) {
		return lineRepository.alreadyExists(destination, carrierId, departureTime);
	}



}
