package proect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proect.model.GasStation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, String> {
    List<GasStation> findByName(String name);
//    GasStation findAllOrder;
//    GasStation findFirstOrderByE5Desc();
//    GasStation findFirstOrderByDieselAsc();
//    GasStation findFirstOrderByDieselDesc();
//    GasStation findFirstOrderByE10Asc();
//    GasStation findFirstOrderByE10Desc();
}
