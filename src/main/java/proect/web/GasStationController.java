package proect.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import proect.dto.GasPriceInfoDTO;
import proect.dto.GasStationDTO;
import proect.model.GasStation;
import proect.service.GasStationServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController()
public class GasStationController {
    private final GasStationServiceImpl gasStationService;

    public GasStationController(GasStationServiceImpl gasStationService) {
        this.gasStationService = gasStationService;
    }

    @GetMapping("/station/{stationName}")
    public ResponseEntity<List<GasStationDTO>> getGasStationByName(@PathVariable("stationName") String stationName) {
        List<GasStationDTO> stationsByName = gasStationService.getStationByName(stationName);
        if (stationsByName.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(stationsByName);
        }
    }

    @GetMapping("/station/fuel-price/info/{type}")
    public ResponseEntity<GasPriceInfoDTO> getGasPriceInfo(@PathVariable("type") String type) {
        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(type);
        if (gasPriceInfo != null) {
            return ResponseEntity.ok(gasPriceInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
