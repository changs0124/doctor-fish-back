package com.project.doctor_fish_back.service.user;

import com.project.doctor_fish_back.aspect.annotation.AuthorityAop;
import com.project.doctor_fish_back.aspect.annotation.NotFoundAop;
import com.project.doctor_fish_back.dto.user.response.reservation.RespGetReservationDto;
import com.project.doctor_fish_back.dto.user.response.reservation.RespGetReservationListDto;
import com.project.doctor_fish_back.dto.user.request.reservation.ReqModifyReservationDto;
import com.project.doctor_fish_back.dto.user.request.reservation.ReqRegisterReservationDto;
import com.project.doctor_fish_back.entity.Reservation;
import com.project.doctor_fish_back.exception.ExecutionException;
import com.project.doctor_fish_back.repository.user.UserReservationMapper;
import com.project.doctor_fish_back.security.principal.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserReservationService {

    @Autowired
    private UserReservationMapper reservationMapper;

    public Boolean registerReservation(ReqRegisterReservationDto dto) {
        try {
            PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Reservation reservation = dto.toEntity(principalUser.getId());
            reservationMapper.register(reservation);
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
        return true;
    }

    @NotFoundAop
    public Boolean cancelReservation(Long reservationId) {
        try {
            reservationMapper.cancelById(reservationId);
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
        return true;
    }

    public RespGetReservationListDto getReservationsToUser() {
        try {
            PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Reservation> reservations = reservationMapper.getToUser(principalUser.getId());
            Long totalCount = reservationMapper.getCountToUser(principalUser.getId());

            return RespGetReservationListDto.builder()
                    .reservations(reservations)
                    .totalCount(totalCount)
                    .build();
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
    }

    public RespGetReservationListDto getAllReservationsToUser() {
        try {
            PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Reservation> reservations = reservationMapper.getAllToUser(principalUser.getId());
            Long totalCount = reservationMapper.getCountAllToUser(principalUser.getId());

            return RespGetReservationListDto.builder()
                    .reservations(reservations)
                    .totalCount(totalCount)
                    .build();
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
    }

    @NotFoundAop
    @AuthorityAop
    public RespGetReservationDto getReservationToUser(Long reservationId) {
        try {
            Reservation reservation = reservationMapper.findById(reservationId);

            return RespGetReservationDto.builder()
                    .id(reservation.getId())
                    .userId(reservation.getUserId())
                    .doctorId(reservation.getDoctorId())
                    .status(reservation.getStatus())
                    .reservationDate(reservation.getReservationDate())
                    .registerDate(reservation.getReservationDate())
                    .doctor(reservation.getDoctor())
                    .build();
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
    }

    @NotFoundAop
    @AuthorityAop
    public Boolean modifyReservation(Long reservationId, ReqModifyReservationDto dto) {
        try {
            reservationMapper.modify(dto.toEntity(reservationId));
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
        return true;
    }

    @NotFoundAop
    @AuthorityAop
    public Boolean deleteReservationFromUser(Long reservationId) {
        try {
            reservationMapper.deleteById(reservationId);
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
        return true;
    }

    public RespGetReservationListDto getEndReservation() {
        try {
            PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Reservation> reservations = reservationMapper.getEndAll(principalUser.getId());
            Long totalCount = reservationMapper.getCountEndAll(principalUser.getId());

            return RespGetReservationListDto.builder()
                    .reservations(reservations)
                    .totalCount(totalCount)
                    .build();
        } catch (Exception e) {
            throw new ExecutionException("실행 도중 오류 발생");
        }
    }

}
