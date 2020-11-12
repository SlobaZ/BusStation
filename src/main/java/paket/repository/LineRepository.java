package paket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import paket.model.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long>{

	@Query("SELECT l FROM Line l WHERE "
			+ "(:destination IS NULL or l.destination like :destination ) AND "
			+ "(:carrierName IS NULL or l.carrier.name like :carrierName ) AND"
			+ "(:ticketPrice IS NULL or l.ticketPrice <= :ticketPrice ) " 
			)
	Page<Line> search(
			@Param("destination") String destination, 
			@Param("carrierName") String carrierName,
			@Param("ticketPrice") Double ticketPrice,
			Pageable pageRequest);
	
	
	@Query("SELECT l FROM Line l WHERE "
			+ " l.destination = :destination  AND "
			+ " l.carrier.id = :carrierId AND "
			+ " l.departureTime = :departureTime  " 
			)
	Line alreadyExists(
			@Param("destination") String destination, 
			@Param("carrierId") Long carrierId,
			@Param("departureTime") String departureTime
			);
	
	
}
