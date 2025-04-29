package com.example.shareIt.booking.mapper;

import com.example.shareIt.booking.dto.BookingDtoRequest;
import com.example.shareIt.booking.dto.BookingDtoResponse;
import com.example.shareIt.booking.model.Booking;
import com.example.shareIt.booking.model.enums.Status;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BookingMapper {

    public Booking mapDtoToNewBooking(BookingDtoRequest dto, User user, Item item) {
        if (dto == null || user == null || item == null) {
            throw new IllegalArgumentException("Один или несколько параметров являются null");
        }
        Booking booking = new Booking();
        booking.setStatus(Status.WAITING);
        booking.setUser(user);
        booking.setItem(item);
        booking.setStart(dto.getStart());
        booking.setEnd(dto.getEnd());
        return booking;
    }

    public BookingDtoResponse mapBookingToDto(Booking booking) {
        BookingDtoResponse dto = new BookingDtoResponse();
        dto.setId(booking.getId());
        dto.setStatus(booking.getStatus());
        dto.setBooker(booking.getUser());
        dto.setItem(booking.getItem());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        return dto;
    }

    public static List<BookingDtoResponse> mapBookingToDto(Iterable<Booking> bookings) {
        List<BookingDtoResponse> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(mapBookingToDto(booking));
        }
        return dtos;
    }
}
