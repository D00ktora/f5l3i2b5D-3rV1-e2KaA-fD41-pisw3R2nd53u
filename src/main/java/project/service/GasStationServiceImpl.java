package project.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dto.GasPriceInfo;
import project.dto.GasStation;
import project.repository.GasStationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GasStationServiceImpl implements GasStationService {
    private final GasStationRepository gasStationRepository;
    private final ModelMapper modelMapper;

    public GasStationServiceImpl(GasStationRepository gasStationRepository, ModelMapper modelMapper) {
        this.gasStationRepository = gasStationRepository;
        this.modelMapper = modelMapper;
    }

    public List<GasStation> getStationsByName(String stationName) {
        List<project.model.GasStation> byName = gasStationRepository.findByName(stationName);
        List<GasStation> gasStationList = new ArrayList<>();
        for (project.model.GasStation gasStation : byName) {
            GasStation map = modelMapper.map(gasStation, GasStation.class);
            if (map != null) {
                gasStationList.add(map);
            }
        }
        return gasStationList;
    }

    @Override
    public GasPriceInfo getGasPriceInfo(String type) {
        switch (type) {
            case "e5" -> {
                List<BigDecimal> prices = gasStationRepository.findAllByE5Asc();
                return calculateMinMaxAndMedian(prices);
            }
            case "e10" -> {
                List<BigDecimal> prices = gasStationRepository.findAllByE10Asc();
                return calculateMinMaxAndMedian(prices);
            }
            case "diesel" -> {
                List<BigDecimal> prices = gasStationRepository.findAllByDieselAsc();
                return calculateMinMaxAndMedian(prices);
            }
        }
        throw new NoSuchElementException();
    }

    public static GasPriceInfo calculateMinMaxAndMedian(List<BigDecimal> stationPriceInfos) {
        BigDecimal median;
        int firstIndex = stationPriceInfos.size() / 2 - 1;
        int secondIndex = stationPriceInfos.size() / 2;
        if (stationPriceInfos.size() % 2 == 0) {
            median = BigDecimal.valueOf(
                    stationPriceInfos.
                        get(firstIndex).doubleValue() + stationPriceInfos.get(secondIndex).doubleValue())
                        .divide(BigDecimal.valueOf(2.0)
                        );
        } else {
            median = BigDecimal.valueOf(stationPriceInfos.get(secondIndex).doubleValue());
        }

        return new GasPriceInfo()
                .setMedian(median.setScale(2, RoundingMode.HALF_UP))
                .setMax(stationPriceInfos.get(stationPriceInfos.size() - 1))
                .setMin(stationPriceInfos.get(0));
    }
}
