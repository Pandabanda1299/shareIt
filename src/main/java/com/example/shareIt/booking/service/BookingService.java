package com.example.shareIt.booking.service;

import com.example.shareIt.booking.dto.BookingDtoRequest;
import com.example.shareIt.booking.dto.BookingDtoResponse;
import com.example.shareIt.booking.model.enums.State;

import java.util.List;

public interface BookingService {

    BookingDtoResponse create(BookingDtoRequest dto, Long userId);

    BookingDtoResponse approve(Long bookingId, Boolean status, Long userId);

    BookingDtoResponse findById(Long bookingId, Long userId);

    List<BookingDtoResponse> findAllByUser(Long userId, State state);

    List<BookingDtoResponse> findAllByUserItems(Long userId, State state);
}