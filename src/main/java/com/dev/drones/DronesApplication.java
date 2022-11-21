package com.dev.drones;

import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.contract.to.MedicationTO;
import com.dev.drones.model.type.Model;
import com.dev.drones.model.type.State;
import com.dev.drones.repository.DroneRepository;
import com.dev.drones.repository.MedicationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.util.Arrays;
import java.util.List;

import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;
import static com.dev.drones.contract.MedicationBinder.MEDICATION_BINDER;

@SpringBootApplication
@EnableScheduling
public class DronesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesApplication.class, args);
	}

	@Bean
	ApplicationRunner init(DroneRepository droneRepository,
						   MedicationRepository medicationRepository) {
		return (ApplicationArguments args) ->  dataSetup(droneRepository, medicationRepository);
	}

	public void dataSetup(DroneRepository droneRepository,
						  MedicationRepository medicationRepository){
		// Adding two drones
		DroneTO droneTO1 = new DroneTO();
		droneTO1.setSerialNumber("DRONE80060724927");
		droneTO1.setBatteryCapacity(100);
		droneTO1.setModel(Model.Cruiserweight);
		droneTO1.setState(State.IDLE);
		droneTO1.setWeightLimit(100);

		DroneTO droneTO2 = new DroneTO();
		droneTO2.setSerialNumber("DRONE800607249270009876");
		droneTO2.setBatteryCapacity(100);
		droneTO2.setModel(Model.Heavyweight);
		droneTO2.setState(State.IDLE);
		droneTO2.setWeightLimit(100);

		DroneTO droneTO3 = new DroneTO();
		droneTO3.setSerialNumber("DRONE800607249888888888");
		droneTO3.setBatteryCapacity(100);
		droneTO3.setModel(Model.Heavyweight);
		droneTO3.setState(State.IDLE);
		droneTO3.setWeightLimit(100);

		List<DroneTO> drones = Arrays.asList(droneTO1, droneTO2, droneTO3);

		drones.forEach(droneTO -> droneRepository.save(DRONE_BINDER.bind(droneTO)));

		MedicationTO medicationTO1 = new MedicationTO();
		medicationTO1.setName("Aspirin");
		medicationTO1.setCode("INV836483");
		medicationTO1.setWeight(40);

		MedicationTO medicationTO2 = new MedicationTO();
		medicationTO2.setName("Dipyrone");
		medicationTO2.setCode("INV7475364");
		medicationTO2.setWeight(40);

		List<MedicationTO> medications = Arrays.asList(medicationTO1, medicationTO2);

		medications.forEach(medicationTO -> medicationRepository.save(MEDICATION_BINDER.bind(medicationTO)));
	}

}
