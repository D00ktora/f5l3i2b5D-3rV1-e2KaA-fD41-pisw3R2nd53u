package project.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.dto.GasPriceInfoDTO;
import project.dto.GasStationDTO;
import project.model.GasStation;
import project.service.GasStationServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class GasStationControllerTest {

    @Mock
    GasStationServiceImpl gasStationService;
    @InjectMocks
    GasStationController gasStationController;


    @Test
    void getGasStationByNameTestWithValidInput() {
        String stationName = "Test Station";
        List<GasStationDTO> stations = setupResultsFromGetStationByNameToReturnListWithOneStation(stationName);

        ResponseEntity<List<GasStationDTO>> response = gasStationController.getGasStationByName(stationName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stations, response.getBody());
    }

    @Test
    void getGasStationByNameTestWithInvalidInput() {
        String stationName = "Non-existent Station";
        when(gasStationService.getStationsByName(stationName)).thenReturn(new ArrayList<>());

        ResponseEntity<List<GasStationDTO>> response = gasStationController.getGasStationByName(stationName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getGasPriceInfoTestWithValidInfo() {
        String fuelType = "Gasoline";
        GasPriceInfoDTO gasPriceInfo = createTestGasPriceInfoDTO();
        when(gasStationService.getGasPriceInfo(fuelType)).thenReturn(gasPriceInfo);

        ResponseEntity<GasPriceInfoDTO> response = gasStationController.getGasPriceInfo(fuelType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gasPriceInfo, response.getBody());
    }

    @Test
    void getGasPriceInfoTestWithInvalidInput() {
        String fuelType = "Diesel";
        when(gasStationService.getGasPriceInfo(fuelType)).thenReturn(null);

        ResponseEntity<GasPriceInfoDTO> response = gasStationController.getGasPriceInfo(fuelType);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private List<GasStationDTO> setupResultsFromGetStationByNameToReturnListWithOneStation(String stationName) {
        List<GasStationDTO> stations = new ArrayList<>();
        stations.add(createTestGasStationDTO());
        when(gasStationService.getStationsByName(stationName)).thenReturn(stations);
        return stations;
    }

    private static GasStationDTO createTestGasStationDTO() {
        return new GasStationDTO()
                .setBrand("Test Brand")
                .setDiesel(BigDecimal.valueOf(1.0))
                .setE5(BigDecimal.valueOf(1.5))
                .setE10(BigDecimal.valueOf(2.0))
                .setId("TestId")
                .setHouseNumber("TestHouse")
                .setLat(BigDecimal.valueOf(10.0))
                .setLng(BigDecimal.valueOf(11.0))
                .setName("TestName")
                .setPlace("TestPlace")
                .setPostCode(7000)
                .setStreet("TestStreet")
                .setIsOpen(true);
    }

    private static GasPriceInfoDTO createTestGasPriceInfoDTO() {
        return new GasPriceInfoDTO()
                .setMax(BigDecimal.valueOf(1.0))
                .setMax(BigDecimal.valueOf(2.0))
                .setMedian(BigDecimal.valueOf(1.5));
    }
}
