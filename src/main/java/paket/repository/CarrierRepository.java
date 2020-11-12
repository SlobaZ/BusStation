package paket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paket.model.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long>{
	
	Carrier findByName(String name);

}
