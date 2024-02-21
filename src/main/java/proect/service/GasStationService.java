package proect.service;

import proect.dto.GasPriceInfoDTO;
import proect.dto.GasStationDTO;

import java.util.List;

public interface GasStationService {
    List<GasStationDTO> getStationsByName(String stationName);

    GasPriceInfoDTO getGasPriceInfo(String type);
}
