package com.hotel.reservation.service.serviceImpl;

import com.hotel.reservation.common.ErrorReservation;
import com.hotel.reservation.common.ReservationResponse;
import com.hotel.reservation.common.SuccessReservation;
import com.hotel.reservation.dto.ReservationDTO;
import com.hotel.reservation.dto.ReservedDatesDTO;
import com.hotel.reservation.model.Reservation;
import com.hotel.reservation.repository.ReservationRepository;
import com.hotel.reservation.service.ReservationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final WebClient webClient;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationServiceImpl(WebClient.Builder webClientBuilder,
                                  ReservationRepository reservationRepository,
                                  ModelMapper modelMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9090/api/v1").build(); // Use service name here
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReservationResponse makeReservation(ReservationDTO reservationDTO) {
        try {
            LocalDate enteredCheckIn = reservationDTO.getCheckIn();
            LocalDate enteredCheckOut = reservationDTO.getCheckOut();

            List<String> responseRoomNumbers = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/rooms")
                            .queryParam("roomType", reservationDTO.getRoomType())
                            .build())
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

                List<Reservation> existingReservations = reservationRepository.getReservationsByRoomNumbers(responseRoomNumbers);
                List<String> availableRooms = getAvailableRooms(responseRoomNumbers, enteredCheckIn, enteredCheckOut, existingReservations);

                if (!availableRooms.isEmpty()){

                    String randomRoomNumber = getRandomAvailableRoom(availableRooms);
                    reservationDTO.setRoomNumber(randomRoomNumber);

                    var madeReservation = reservationRepository.save(modelMapper.map(reservationDTO, Reservation.class));
                    reservationDTO.setReservationId(madeReservation.getReservationId());
                    return new SuccessReservation(reservationDTO);
                }else {
                    return new ErrorReservation("No Available Room at the moment");
                }

        }catch (Exception e){
            e.printStackTrace();
            return new ErrorReservation("An error occurred while making the reservation");
        }
    }

    @Override
    public List<String> getAvailableRooms(List<String> roomNumbers, LocalDate enteredCheckIn, LocalDate enteredCheckOut, List<Reservation> existingReservations) {
        List<String> availableRooms = new ArrayList<>(roomNumbers);

        for (String roomNumber : roomNumbers) {
            boolean isRoomAvailable = true;
            for (Reservation reservation : existingReservations) {
                if (reservation.getRoomNumber().equals(roomNumber)) {
                    LocalDate existingCheckIn = reservation.getCheckIn();
                    LocalDate existingCheckOut = reservation.getCheckOut();

                    if (!(enteredCheckOut.isBefore(existingCheckIn) || enteredCheckIn.isAfter(existingCheckOut))) {
                        isRoomAvailable = false;
                        break;
                    }
                }
            }
            if (!isRoomAvailable) {
                availableRooms.remove(roomNumber);
            }
        }

        return availableRooms.isEmpty() ? null : availableRooms;
    }

    @Override
    public String getRandomAvailableRoom(List<String> availableRooms) {
        if (availableRooms.isEmpty()){
            throw new RuntimeException("No available rooms at this time!");
        }else {
            Random random = new Random();
            int index = random.nextInt(availableRooms.size());
            return availableRooms.get(index);
        }
    }


    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return modelMapper.map(allReservations,new TypeToken<List<ReservationDTO>>(){}.getType());
    }

    @Override
    public List<ReservedDatesDTO> getReservedDates(List<String> responseRoomNumbers) {
        List<Reservation> reservations = reservationRepository.getReservationsByRoomNumbers(responseRoomNumbers);

        return reservations.stream()
                .map(reservation -> new ReservedDatesDTO(
                        reservation.getCheckIn(),
                        reservation.getCheckOut()
                )).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getMyReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.getReservationById(reservationId);
        return modelMapper.map(reservation,new TypeToken<ReservationDTO>(){}.getType());
    }

    @Override
    public ReservationDTO changeMyReservation(ReservationDTO reservationDTO) {
        var reservationId = reservationDTO.getReservationId();
        var isReservationExists = reservationRepository.getReservationById(reservationId);
        if (isReservationExists != null){
            reservationRepository.save(modelMapper.map(reservationDTO, Reservation.class));
            return reservationDTO;
        }else{
            return null;
        }
    }

    @Override
    public Integer deleteMyReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
        return reservationId;
    }
}
