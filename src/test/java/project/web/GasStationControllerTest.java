package project.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.dto.GasPriceInfo;
import project.dto.GasStation;
import project.service.GasStationServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class GasStationControllerTest {

    @Mock
    GasStationServiceImpl gasStationService;
    @InjectMocks
    GasStationController gasStationController;


    @Test
    void getGasStationByNameTestWithValidInput() {
        // GIVEN
        String stationName = "Test Station";
        List<GasStation> stations = setupResultsFromGetStationByNameToReturnListWithOneStation(stationName);

        // WHEN
        ResponseEntity<List<GasStation>> gasStationsResponse = gasStationController.getGasStationByName(stationName);

        // THEN
        assertThat(gasStationsResponse)
                .extracting("status")
                .isEqualTo(HttpStatus.OK);
        assertThat(gasStationsResponse)
                .extracting("body")
                .isEqualTo(stations);
    }

    @Test
    void getGasStationByNameTestWithInvalidInput() {
        // GIVEN
        String stationName = "Non-existent Station";
        when(gasStationService.getStationsByName(stationName)).thenReturn(new ArrayList<>());

        // WHEN
        ResponseEntity<List<GasStation>> gasStationResponse = gasStationController.getGasStationByName(stationName);

        // THEN
        assertThat(gasStationResponse)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getGasPriceInfoTestWithValidInfo() {
        // GIVEN
        String fuelType = "Gasoline";
        GasPriceInfo gasPriceInfo = createTestGasPriceInfoDTO();
        when(gasStationService.getGasPriceInfo(fuelType)).thenReturn(gasPriceInfo);

        // WHEN
        ResponseEntity<GasPriceInfo> gasPriceInfoResponse = gasStationController.getGasPriceInfo(fuelType);

        // THEN
        assertThat(gasPriceInfoResponse)
                .extracting("status")
                .isEqualTo(HttpStatus.OK);
        assertThat(gasPriceInfoResponse)
                .extracting("body")
                .isEqualTo(gasPriceInfo);
    }

    @Test
    void getGasPriceInfoTestWithInvalidInput() {
        // GIVEN
        String fuelType = "Diesel";
        when(gasStationService.getGasPriceInfo(fuelType)).thenReturn(null);

        // WHEN
        ResponseEntity<GasPriceInfo> gasPrice = gasStationController.getGasPriceInfo(fuelType);

        // THEN
        assertThat(gasPrice)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    private List<GasStation> setupResultsFromGetStationByNameToReturnListWithOneStation(String stationName) {
        List<GasStation> stations = new ArrayList<>();
        stations.add(createTestGasStationDTO());
        when(gasStationService.getStationsByName(stationName)).thenReturn(stations);
        return stations;
    }

    private static GasStation createTestGasStationDTO() {
        return new GasStation()
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

    private static GasPriceInfo createTestGasPriceInfoDTO() {
        return new GasPriceInfo()
                .setMax(BigDecimal.valueOf(1.0))
                .setMax(BigDecimal.valueOf(2.0))
                .setMedian(BigDecimal.valueOf(1.5));
    }
}
