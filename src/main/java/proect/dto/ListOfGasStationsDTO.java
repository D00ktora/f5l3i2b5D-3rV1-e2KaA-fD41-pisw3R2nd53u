package proect.dto;

import java.util.List;

public class ListOfGasStationsDTO {
    private List<GasStationDTO> stations;

    public List<GasStationDTO> getStations() {
        return stations;
    }

    public ListOfGasStationsDTO setStations(List<GasStationDTO> stations) {
        this.stations = stations;
        return this;
    }
}
