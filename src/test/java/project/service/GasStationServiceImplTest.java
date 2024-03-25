package project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.dto.GasPriceInfoDTO;
import project.dto.GasStationDTO;
import project.model.GasStation;
import project.repository.GasStationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GasStationServiceImplTest {
    @InjectMocks
    private GasStationServiceImpl gasStationService;
    @Mock
    private GasStationRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        this.gasStationService = new GasStationServiceImpl(repository, modelMapper);
    }

    @Test
    void getStationsByName() {
        GasStation testGasStation = createTestGasStation();
        when(repository.findByName(testGasStation.getName())).thenReturn(List.of(testGasStation));

        List<GasStationDTO> stationsByName = gasStationService.getStationsByName("TestName");

        assertEquals(1, stationsByName.size());
        assertEquals(testGasStation.getName(), stationsByName.get(0).getName());
    }
    @Test
    void getStationsByNameWithInvalidName() {
        when(repository.findByName("TestName")).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> gasStationService.getStationsByName("TestName"));
    }
    @Test
    void verifyOneCallOfFindByNameInGetStationByName() {
        GasStation testGasStation = createTestGasStation();
        when(repository.findByName("TestName")).thenReturn(List.of(testGasStation));

        List<GasStationDTO> listOfGasStations = gasStationService.getStationsByName("TestName");

        verify(repository).findByName("TestName");
        verifyNoMoreInteractions(repository);
    }
    @ParameterizedTest
    @ValueSource(strings = {"e5", "e10", "diesel"})
    void getGasPriceInfoWith3Stations(String fuelType) {
        setupFunctionalityOfFindAllByFuelTypeWithThreeGasStations(fuelType);

        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(fuelType);

        assertEquals(BigDecimal.valueOf(2.0).setScale(2, RoundingMode.HALF_UP), gasPriceInfo.getMedian());
        assertEquals(BigDecimal.valueOf(1.0), gasPriceInfo.getMin());
        assertEquals(BigDecimal.valueOf(3.0), gasPriceInfo.getMax());
    }
    @ParameterizedTest
    @ValueSource(strings = {"e5", "e10", "diesel"})
    void getGasPriceInfoWith2Stations(String text) {
        setupFunctionalityOfFindAllByFuelTypeWithTwoGasStations(text);

        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(text);

        assertEquals(BigDecimal.valueOf(1.5).setScale(2, RoundingMode.HALF_UP), gasPriceInfo.getMedian());
        assertEquals(BigDecimal.valueOf(1.0), gasPriceInfo.getMin());
        assertEquals(BigDecimal.valueOf(2.0), gasPriceInfo.getMax());
    }
    @Test
    void calculateMinMaxAndMedianTest() {
        List<BigDecimal> testListWithOddNumberOfValues = setupUnorderedListWithOddNumberOfValues();
        BigDecimal expectedResult = BigDecimal.valueOf(1.4).setScale(2, RoundingMode.HALF_DOWN);

        GasPriceInfoDTO result = GasStationServiceImpl.calculateMinMaxAndMedian(testListWithOddNumberOfValues);

        assertEquals(expectedResult, result.getMedian());
    }
    @Test
    void calculateMinMaxAndMedianTestWithEven() {
        List<BigDecimal> testListWithEvenNumberOfValues = setupUnorderedListWithEvenValues();
        BigDecimal expectedResult = BigDecimal.valueOf(1.35);

        GasPriceInfoDTO result = GasStationServiceImpl.calculateMinMaxAndMedian(testListWithEvenNumberOfValues);

        assertEquals(expectedResult, result.getMedian());
    }
    @Test
    void getGasPriceInfoWithInvalidInput() {
        String invalidInput = "Invalid gas type";

        assertThrows(NoSuchElementException.class, () -> gasStationService.getGasPriceInfo(invalidInput));
        verifyNoMoreInteractions(repository);
    }

    private static GasStation createTestGasStation() {
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
                .setOpen(true)
                .setPlace("TestPlace")
                .setPostCode(7000)
                .setStreet("TestStreet");
    }
    private void setupFunctionalityOfFindAllByFuelTypeWithThreeGasStations(String text) {
        GasStation testGasStation1 = createTestGasStation().setE5(BigDecimal.valueOf(1.0)).setE10(BigDecimal.valueOf(1.0)).setDiesel(BigDecimal.valueOf(1.0));
        GasStation testGasStation2 = createTestGasStation().setE5(BigDecimal.valueOf(2.0)).setE10(BigDecimal.valueOf(2.0)).setDiesel(BigDecimal.valueOf(2.0));
        GasStation testGasStation3 = createTestGasStation().setE5(BigDecimal.valueOf(3.0)).setE10(BigDecimal.valueOf(3.0)).setDiesel(BigDecimal.valueOf(3.0));
        switch (text) {
            case "e5":
                when(repository.findAllByE5Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5(), testGasStation3.getE5()));
                break;
            case "e10":
                when(repository.findAllByE10Asc()).thenReturn(List.of(testGasStation1.getE10(), testGasStation2.getE10(), testGasStation3.getE10()));
                break;
            case "diesel":
                when(repository.findAllByDieselAsc()).thenReturn(List.of(testGasStation1.getDiesel(), testGasStation2.getDiesel(), testGasStation3.getDiesel()));
                break;
        }
    }
    private void setupFunctionalityOfFindAllByFuelTypeWithTwoGasStations(String text) {
        GasStation testGasStation1 = createTestGasStation().setE5(BigDecimal.valueOf(1.0)).setE10(BigDecimal.valueOf(1.0)).setDiesel(BigDecimal.valueOf(1.0));
        GasStation testGasStation2 = createTestGasStation().setE5(BigDecimal.valueOf(2.0)).setE10(BigDecimal.valueOf(2.0)).setDiesel(BigDecimal.valueOf(2.0));
        switch (text) {
            case "e5":
                when(repository.findAllByE5Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5()));
                break;
            case "e10":
                when(repository.findAllByE10Asc()).thenReturn(List.of(testGasStation1.getE10(), testGasStation2.getE10()));
                break;
            case "diesel":
                when(repository.findAllByDieselAsc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5()));
                break;
        }
    }
    private static List<BigDecimal> setupUnorderedListWithOddNumberOfValues() {
        List<BigDecimal> testList = new ArrayList<>();
        testList.add(BigDecimal.valueOf(1.2));
        testList.add(BigDecimal.valueOf(1.1));
        testList.add(BigDecimal.valueOf(2.1));
        testList.add(BigDecimal.valueOf(2.3));
        testList.add(BigDecimal.valueOf(1.4));
        testList.add(BigDecimal.valueOf(2.2));
        testList.add(BigDecimal.valueOf(1.3));
        testList.sort(BigDecimal::compareTo);
        return testList;
    }
    private static List<BigDecimal> setupUnorderedListWithEvenValues() {
        List<BigDecimal> testList = new ArrayList<>();
        testList.add(BigDecimal.valueOf(1.2));
        testList.add(BigDecimal.valueOf(1.1));
        testList.add(BigDecimal.valueOf(2.1));
        testList.add(BigDecimal.valueOf(1.4));
        testList.add(BigDecimal.valueOf(2.2));
        testList.add(BigDecimal.valueOf(1.3));
        testList.sort(BigDecimal::compareTo);
        return testList;
    }
}
