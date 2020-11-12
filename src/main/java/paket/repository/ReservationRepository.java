package paket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import paket.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
	@Query("SELECT r FROM Reservation r WHERE "
			+ "(:username IS NULL or r.user.username like :username ) AND "
			+ "(:destination IS NULL or r.line.destination like :destination ) " 
			)
	Page<Reservation> search(
			@Param("username") String username, 
			@Param("destination") String destination,
			Pageable pageRequest);

}
