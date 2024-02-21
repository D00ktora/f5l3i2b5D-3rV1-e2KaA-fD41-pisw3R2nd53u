package project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import project.dto.GasPriceInfoDTO;
import project.dto.GasStationDTO;
import project.model.GasStation;
import project.repository.GasStationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GasStationServiceImplTest {

    private GasStationServiceImpl gasStationService;
    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private GasStationRepository repository;

    @BeforeEach
    void setUp() {
        gasStationService = new GasStationServiceImpl(repository, modelMapper);

    }
    @Test
    void getStationsByName() {
        GasStation testGasStation = createTestGasStation();
        when(repository.findByName(testGasStation.getName())).thenReturn(List.of(testGasStation));
        List<GasStationDTO> stationsByName = gasStationService.getStationsByName("TestName");
        Assertions.assertEquals(1, stationsByName.size());
        Assertions.assertEquals(testGasStation.getName(), stationsByName.get(0).getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"e5", "e10", "diesel"})
    void getGasPriceInfoWith3Stations(String text) {
        GasStation testGasStation1 = createTestGasStation().setE5(BigDecimal.valueOf(1.0)).setE10(BigDecimal.valueOf(1.0)).setDiesel(BigDecimal.valueOf(1.0));
        GasStation testGasStation2 = createTestGasStation().setE5(BigDecimal.valueOf(2.0)).setE10(BigDecimal.valueOf(2.0)).setDiesel(BigDecimal.valueOf(2.0));
        GasStation testGasStation3 = createTestGasStation().setE5(BigDecimal.valueOf(3.0)).setE10(BigDecimal.valueOf(3.0)).setDiesel(BigDecimal.valueOf(3.0));

        when(repository.findAllByE5Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5(), testGasStation3.getE5()));
        when(repository.findAllByE10Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5(), testGasStation3.getE5()));
        when(repository.findAllByDieselAsc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5(), testGasStation3.getE5()));

        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(text);
        Assertions.assertEquals(BigDecimal.valueOf(2.0).setScale(2, RoundingMode.HALF_UP), gasPriceInfo.getMedian());
        Assertions.assertEquals(BigDecimal.valueOf(1.0), gasPriceInfo.getMin());
        Assertions.assertEquals(BigDecimal.valueOf(3.0), gasPriceInfo.getMax());
    }
    @ParameterizedTest
    @ValueSource(strings = {"e5", "e10", "diesel"})
    void getGasPriceInfoWith2Stations(String text) {
        GasStation testGasStation1 = createTestGasStation().setE5(BigDecimal.valueOf(1.0)).setE10(BigDecimal.valueOf(1.0)).setDiesel(BigDecimal.valueOf(1.0));
        GasStation testGasStation2 = createTestGasStation().setE5(BigDecimal.valueOf(2.0)).setE10(BigDecimal.valueOf(2.0)).setDiesel(BigDecimal.valueOf(2.0));
        when(repository.findAllByE5Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5()));
        when(repository.findAllByE10Asc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5()));
        when(repository.findAllByDieselAsc()).thenReturn(List.of(testGasStation1.getE5(), testGasStation2.getE5()));

        GasPriceInfoDTO gasPriceInfo = gasStationService.getGasPriceInfo(text);
        Assertions.assertEquals(BigDecimal.valueOf(1.5).setScale(2, RoundingMode.HALF_UP), gasPriceInfo.getMedian());
        Assertions.assertEquals(BigDecimal.valueOf(1.0), gasPriceInfo.getMin());
        Assertions.assertEquals(BigDecimal.valueOf(2.0), gasPriceInfo.getMax());
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
}