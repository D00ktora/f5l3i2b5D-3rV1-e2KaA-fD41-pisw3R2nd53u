package proect.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import proect.dto.GasPriceInfoDTO;
import proect.dto.GasStationDTO;
import proect.model.GasStation;
import proect.repository.GasStationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GasStationServiceImpl implements GasStationService {
    private final GasStationRepository gasStationRepository;
    private final ModelMapper modelMapper;

    public GasStationServiceImpl(GasStationRepository gasStationRepository, ModelMapper modelMapper) {
        this.gasStationRepository = gasStationRepository;
        this.modelMapper = modelMapper;
    }

    public List<GasStationDTO> getStationByName(String stationName) {
        List<GasStation> byName = gasStationRepository.findByName(stationName);
        List<GasStationDTO> gasStationDTOList = new ArrayList<>();
        for (GasStation gasStationDTO : byName) {
            GasStationDTO map = modelMapper.map(gasStationDTO, GasStationDTO.class);
            if (map != null) {
                gasStationDTOList.add(map);
            }
        }
        return gasStationDTOList;
    }

    @Override
    public GasPriceInfoDTO getGasPriceInfo(String type) {
        List<GasStation> all = gasStationRepository.findAll();
        return calculateMinMaxAndMedian(all, type);
    }
    private static GasPriceInfoDTO calculateMinMaxAndMedian(List<GasStation> gasStations, String type) {
        List<BigDecimal> gasValues = new ArrayList<>();
        BigDecimal median;
        for (GasStation gasStation : gasStations) {
            switch (type) {
                case "e5":
                    if (gasStation.getE5() != null) {
                        gasValues.add(gasStation.getE5());
                    }
                    break;
                case "e10":
                    if (gasStation.getE10() != null) {
                        gasValues.add(gasStation.getE10());
                    }
                    break;
                case "diesel":
                    if (gasStation.getDiesel() != null) {
                        gasValues.add(gasStation.getDiesel());
                    }
                    break;
            }
        }
        gasValues.remove(null);
        Collections.sort(gasValues);
        if (gasValues.size() % 2 == 0) {
            BigDecimal firstValue = gasValues.get(gasValues.size() / 2);
            BigDecimal secondValue = gasValues.get((gasValues.size() / 2) + 1);
            median = new BigDecimal((firstValue.doubleValue() + secondValue.doubleValue()) / 2);
        } else {
            median = gasValues.get((gasValues.size() / 2) + 1);
        }
        BigDecimal min = gasValues.get(0);
        BigDecimal max = gasValues.get(gasValues.size() - 1);

        return new GasPriceInfoDTO()
                .setMedian(median)
                .setMax(max)
                .setMin(min);
    }
}
