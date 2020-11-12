package paket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import paket.model.Reservation;
import paket.repository.ReservationRepository;
import paket.service.ReservationService;

@Service
public class JpaReservationService implements ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public Page<Reservation> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return reservationRepository.findAll(pageable);
	}

	@Override
	public Reservation getOne(Long id) {
		return reservationRepository.getOne(id);
	}

	@Override
	public Reservation save(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	@Override
	public Reservation delete(Long id) {
		Reservation reservation = reservationRepository.getOne(id);
		if(reservation != null) {			
			reservationRepository.delete(reservation);
		}
		return reservation;
	}

	@Override
	public Page<Reservation> search(String username, String destination, int pageNum) {
		if(username != null) {
			username = '%' + username + '%';
		}
		if(destination != null) {
			destination = '%' + destination + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return reservationRepository.search(username, destination, pageable); 
	}



}
