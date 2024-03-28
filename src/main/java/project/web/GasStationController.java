package project.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.dto.GasPriceInfo;
import project.dto.GasStation;
import project.service.GasStationServiceImpl;

import java.util.List;

@RestController()
public class GasStationController {
    private final GasStationServiceImpl gasStationService;

    public GasStationController(GasStationServiceImpl gasStationService) {
        this.gasStationService = gasStationService;
    }

    @GetMapping("/gas-stations/{name}")
    public ResponseEntity<List<GasStation>> getGasStationByName(@PathVariable("name") String stationName) {
        List<GasStation> stationsByName = gasStationService.getStationsByName(stationName);
        if (stationsByName.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(stationsByName);
        }
    }

    @GetMapping("/fuel-prices/{fuelType}")
    public ResponseEntity<GasPriceInfo> getGasPriceInfo(@PathVariable("fuelType") String type) {
        GasPriceInfo gasPriceInfo = gasStationService.getGasPriceInfo(type);
        if (gasPriceInfo != null) {
            return ResponseEntity.ok(gasPriceInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
