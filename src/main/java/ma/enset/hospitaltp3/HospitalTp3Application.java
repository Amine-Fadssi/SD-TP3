package ma.enset.hospitaltp3;

import ma.enset.hospitaltp3.entities.Patient;
import ma.enset.hospitaltp3.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HospitalTp3Application {

    public static void main(String[] args) {
        SpringApplication.run(HospitalTp3Application.class, args);
    }
    // @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            // Add Patients
            // with set method
            Patient patient = new Patient();
            patient.setNom("Alex");
            patient.setMalade(true);
            patient.setDateNaissance(new Date());
            patient.setScore(558);
            // with constructor
            Patient patient1 = new Patient(null, "Smith",
                    new Date(), false, 881);
            // with builder
            Patient patient2 = Patient.builder()
                    .nom("Tiger")
                    .dateNaissance(new Date())
                    .malade(true)
                    .score(841)
                    .build();

//            patientRepository.save(patient);
//            patientRepository.save(patient1);
//            patientRepository.save(patient2);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
