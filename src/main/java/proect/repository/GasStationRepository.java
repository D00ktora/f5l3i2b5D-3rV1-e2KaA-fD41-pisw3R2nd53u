package proect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proect.model.GasStation;

import java.util.UUID;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, String> {
}
