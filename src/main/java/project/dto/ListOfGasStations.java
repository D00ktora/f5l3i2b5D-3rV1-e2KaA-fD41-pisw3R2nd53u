package project.dto;

import java.util.List;

public class ListOfGasStations {
    private List<GasStation> stations;

    public List<GasStation> getStations() {
        return stations;
    }

    public ListOfGasStations setStations(List<GasStation> stations) {
        this.stations = stations;
        return this;
    }
}
