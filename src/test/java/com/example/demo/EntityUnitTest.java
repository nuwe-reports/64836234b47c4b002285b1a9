package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

    private Doctor d1;
    private Doctor d2;
    private Patient p1;
    private Patient p2;
    private Room r1;
    private Room r2;
    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeEach
    void setUp() {
        d1 = new Doctor("Marcos", "Zaragoza",43,"m.zaragoza@ejemplo.com");
        d2 = new Doctor("Martha", "Jimenez",43,"m.jimenez@ejemplo.com");
        p1 = new Patient("Sandra", "Gracia",45,"s.gracia@ejemplo.com");
        p2 = new Patient("Miguel", "Espino",45,"m.espino@ejemplo.com");
        r1 = new Room("Room Name 1");
        r2 = new Room("Room Name 2");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 1, 1, 10, 0), LocalDateTime.of(2023, 1, 1, 11, 0));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 1, 2, 12, 0), LocalDateTime.of(2023, 1, 2, 13, 0));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 1, 3, 14, 0), LocalDateTime.of(2023, 1, 3, 15, 0));

        entityManager.persistAndFlush(d1);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(r1);
        entityManager.persistAndFlush(a1);
        entityManager.persistAndFlush(a2);
        entityManager.persistAndFlush(a3);
    }

    @Test
    void testDoctorEntity() {
        Doctor retrievedDoctor = entityManager.find(Doctor.class, d1.getId());
        assertThat(retrievedDoctor).isEqualTo(d1);
        assertThat(retrievedDoctor.getFirstName()).isEqualTo(d1.getFirstName());
        assertThat(retrievedDoctor.getLastName()).isEqualTo(d1.getLastName());
        assertThat(retrievedDoctor.getAge()).isEqualTo(d1.getAge());
        assertThat(retrievedDoctor.getEmail()).isEqualTo(d1.getEmail());

        retrievedDoctor.setId(3);
        retrievedDoctor.setFirstName("Karla");
        retrievedDoctor.setLastName("Puyol");
        retrievedDoctor.setAge(30);
        retrievedDoctor.setEmail("k.puyol@ejemplo.com");

        assertThat(retrievedDoctor.getFirstName()).isEqualTo(d1.getFirstName());
        assertThat(retrievedDoctor.getLastName()).isEqualTo(d1.getLastName());
        assertThat(retrievedDoctor.getAge()).isEqualTo(d1.getAge());
        assertThat(retrievedDoctor.getEmail()).isEqualTo(d1.getEmail());
    }

    @Test
    void testPatientEntity() {
        Patient retrievedPatient = entityManager.find(Patient.class, p1.getId());
        assertThat(retrievedPatient).isEqualTo(p1);
        assertThat(retrievedPatient.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(retrievedPatient.getLastName()).isEqualTo(p1.getLastName());
        assertThat(retrievedPatient.getAge()).isEqualTo(p1.getAge());
        assertThat(retrievedPatient.getEmail()).isEqualTo(p1.getEmail());

        retrievedPatient.setId(3);
        retrievedPatient.setFirstName("Carlos");
        retrievedPatient.setLastName("Gracia");
        retrievedPatient.setAge(36);
        retrievedPatient.setEmail("c.gracia@jemplo.com");

        assertThat(retrievedPatient.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(retrievedPatient.getLastName()).isEqualTo(p1.getLastName());
        assertThat(retrievedPatient.getAge()).isEqualTo(p1.getAge());
        assertThat(retrievedPatient.getEmail()).isEqualTo(p1.getEmail());

    }

    @Test
    void testRoomEntity() {
        Room retrievedRoom = entityManager.find(Room.class, r1.getRoomName());
        assertThat(retrievedRoom).isEqualTo(r1);
        assertThat(retrievedRoom.getRoomName()).isEqualTo(r1.getRoomName());

        Room room = new Room();
        assertThat(room.getRoomName()).isEqualTo(null);

    }

    @Test
    void testAppointmentEntity() {
        Appointment retrievedAppointment = entityManager.find(Appointment.class, a1.getId());
        assertThat(retrievedAppointment).isEqualTo(a1);
        assertThat(retrievedAppointment.getPatient()).isEqualTo(a1.getPatient());
        assertThat(retrievedAppointment.getDoctor()).isEqualTo(a1.getDoctor());
        assertThat(retrievedAppointment.getRoom()).isEqualTo(a1.getRoom());
        assertThat(retrievedAppointment.getStartsAt()).isEqualTo(a1.getStartsAt());
        assertThat(retrievedAppointment.getFinishesAt()).isEqualTo(a1.getFinishesAt());

        retrievedAppointment.setId(2);
        retrievedAppointment.setPatient(p2);
        retrievedAppointment.setDoctor(d2);
        retrievedAppointment.setRoom(r2);
        retrievedAppointment.setStartsAt(LocalDateTime.of(2023, 2, 2, 10, 0));
        retrievedAppointment.setFinishesAt(LocalDateTime.of(2023, 2, 2, 10, 0));

        assertThat(retrievedAppointment.getId()).isEqualTo(2);
        assertThat(retrievedAppointment.getPatient()).isEqualTo(p2);
        assertThat(retrievedAppointment.getDoctor()).isEqualTo(d2);
        assertThat(retrievedAppointment.getRoom()).isEqualTo(r2);
        assertThat(retrievedAppointment.getStartsAt()).isEqualTo(a1.getStartsAt());
        assertThat(retrievedAppointment.getFinishesAt()).isEqualTo(a1.getFinishesAt());
    }

    @Test
    void testAppointmentEntityOverlaps() {
        Appointment overlappingAppointment = new Appointment(p1, d1, r1,
                LocalDateTime.of(2023, 1, 1, 10, 30),
                LocalDateTime.of(2023, 1, 1, 11, 30));

        assertThat(a1.overlaps(overlappingAppointment)).isTrue();

        Appointment nonOverlappingAppointment = new Appointment(p1, d1, r1,
                LocalDateTime.of(2023, 1, 1, 11, 30),
                LocalDateTime.of(2023, 1, 1, 12, 0));

        assertThat(a1.overlaps(nonOverlappingAppointment)).isFalse();

        Appointment sameDatesAppointment = new Appointment(p1, d1, r1,
                LocalDateTime.of(2023, 1, 2, 12, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30));

        assertThat(a2.overlaps(sameDatesAppointment)).isTrue();

        Appointment problemsWhitDatesAppointment = new Appointment(p1, d1, r1,
                LocalDateTime.of(2023, 1, 3, 12, 0),
                LocalDateTime.of(2023, 1, 3, 14, 30));

        assertThat(a3.overlaps(problemsWhitDatesAppointment)).isTrue();
    }
}
