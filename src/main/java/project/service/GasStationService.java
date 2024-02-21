package project.service;

import project.dto.GasPriceInfoDTO;
import project.dto.GasStationDTO;

import java.util.List;

public interface GasStationService {
    List<GasStationDTO> getStationsByName(String stationName);

    GasPriceInfoDTO getGasPriceInfo(String type);
}
