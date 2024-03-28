package project.service;

import project.dto.GasPriceInfo;
import project.dto.GasStation;

import java.util.List;

public interface GasStationService {
    List<GasStation> getStationsByName(String stationName);

    GasPriceInfo getGasPriceInfo(String type);
}
