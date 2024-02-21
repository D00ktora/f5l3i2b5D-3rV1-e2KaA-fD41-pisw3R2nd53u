package proect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proect.model.GasStation;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, String> {
    List<GasStation> findByName(String name);
    @Query("select g.e5 from GasStation g where g.e5 is not null order by g.e5 asc")
    List<BigDecimal> findAllByE5Asc();
    @Query("select g.e10 from GasStation g where g.e10 is not null order by g.e10 asc")
    List<BigDecimal> findAllByE10Asc();
    @Query("select g.diesel from GasStation g where g.diesel is not null order by g.diesel asc")
    List<BigDecimal> findAllByDieselAsc();
}
