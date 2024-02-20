package proect.service;

import proect.dto.GasPriceInfoDTO;
import proect.dto.GasStationDTO;
import proect.model.GasStation;

import java.util.List;

public interface GasStationService {
    List<GasStationDTO> getStationByName(String stationName);

    GasPriceInfoDTO getGasPriceInfo(String type);
}
