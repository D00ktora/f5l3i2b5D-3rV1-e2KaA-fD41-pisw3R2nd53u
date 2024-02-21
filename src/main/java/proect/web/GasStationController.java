package proect.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import proect.dto.GasPriceInfoDTO;
import proect.dto.GasStationDTO;
import proect.service.GasStationServiceImpl;

import java.util.List;

@RestController()
public class GasStationController {
    private final GasStationServiceImpl gasStationService;

    public GasStationController(GasStationServiceImpl gasStationService) {
        this.gasStationService = gasStationService;
    }

    @GetMapping("/gas-stations/{name}")
    public ResponseEntity<List<GasStationDTO>> getGasStationByName(@PathVariable("name") String stationName) {
        List<GasStationDTO> stationsByName = gasStationService.getStationsByName(stationName);
        if (stationsByName.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(stationsByName);
        }
    }

    @GetMapping("/fuel-prices/{fuelType}")
    public ResponseEntity<GasPriceInfoDTO> getGasPriceInfo(@PathVariable("fuelType") String type) {
        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(type);
        if (gasPriceInfo != null) {
            return ResponseEntity.ok(gasPriceInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
