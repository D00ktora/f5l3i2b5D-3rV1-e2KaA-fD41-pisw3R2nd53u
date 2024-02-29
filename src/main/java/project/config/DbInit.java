package project.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.dto.GasStationDTO;
import project.dto.ListOfGasStationsDTO;
import project.model.GasStation;
import project.repository.GasStationRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInit implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final GasStationRepository repository;
    private final ModelMapper modelMapper;
    public DbInit(RestTemplate restTemplate, RestTemplate restTemplate1, GasStationRepository repository, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void run(String... args) throws Exception {
        ListOfGasStationsDTO forObject = restTemplate.getForObject("https://wejago.de/assets/data/gas-stations-data", ListOfGasStationsDTO.class);
        if (forObject == null) {
            return;
        }
        List<GasStationDTO> stationsDTO = forObject.getStations();
        List<GasStation> gasStations = new ArrayList<>();
        for (GasStationDTO station : stationsDTO) {
            GasStation mapped = modelMapper.map(station, GasStation.class);
            if (mapped != null && mapped.getOpen()) {
                gasStations.add(mapped);
            }
        }
        repository.saveAll(gasStations);
    }
}
