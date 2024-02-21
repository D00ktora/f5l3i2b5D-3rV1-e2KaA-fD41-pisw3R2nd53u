package project.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dto.GasPriceInfoDTO;
import project.dto.GasStationDTO;
import project.model.GasStation;
import project.repository.GasStationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class GasStationServiceImpl implements GasStationService {
    private final GasStationRepository gasStationRepository;
    private final ModelMapper modelMapper;

    public GasStationServiceImpl(GasStationRepository gasStationRepository, ModelMapper modelMapper) {
        this.gasStationRepository = gasStationRepository;
        this.modelMapper = modelMapper;
    }

    public List<GasStationDTO> getStationsByName(String stationName) {
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
        return null;
    }
    private static GasPriceInfoDTO calculateMinMaxAndMedian(List<BigDecimal> stationPriceInfos) {
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

        return new GasPriceInfoDTO()
                .setMedian(median.setScale(2, RoundingMode.HALF_UP))
                .setMax(stationPriceInfos.get(stationPriceInfos.size() - 1))
                .setMin(stationPriceInfos.get(0));
    }
}
