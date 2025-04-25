package com.example.shareIt.booking.service;

import com.example.shareIt.booking.dto.BookingDtoRequest;
import com.example.shareIt.booking.dto.BookingDtoResponse;
import com.example.shareIt.booking.mapper.BookingMapper;
import com.example.shareIt.booking.model.Booking;
import com.example.shareIt.booking.model.enums.State;
import com.example.shareIt.booking.model.enums.Status;
import com.example.shareIt.booking.repository.BookingRepository;
import com.example.shareIt.exception.NotFoundException;
import com.example.shareIt.exception.ValidationException;
import com.example.shareIt.item.model.Item;
import com.example.shareIt.item.storage.ItemRepository;
import com.example.shareIt.user.model.User;
import com.example.shareIt.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.shareIt.booking.model.enums.Status.CURRENT;
import static com.example.shareIt.booking.model.enums.Status.PAST;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingDtoResponse create(BookingDtoRequest dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь " + userId + " не найден"));

        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(()
                -> new NotFoundException("Вещь " + dto.getItemId() + " не найдена"));

        if (!item.getAvailable()) {
            throw new ValidationException("Вещь " + item.getId() + " недоступна для бронирования");
        }

        Booking booking = bookingRepository.save(BookingMapper.mapDtoToNewBooking(dto, user, item));
        return BookingMapper.mapBookingToDto(booking);
    }

    @Override
    public BookingDtoResponse approve(Long bookingId, Boolean status, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new NotFoundException("Бронирование с id = " + bookingId + " не найдено"));

        Long itemId = booking.getItem().getOwner().getId();

        if (!Objects.equals(itemId, userId)) {
            throw new ValidationException("Пользователь " + userId + " не является владельцем вещи " + itemId);
        }

        if (!Objects.equals(booking.getStatus(), Status.WAITING)) {
            throw new ValidationException("Бронирование подтверждено или отклонено");
        }

        if (status) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }

        bookingRepository.save(booking);
        return BookingMapper.mapBookingToDto(booking);
    }

    @Override
    public BookingDtoResponse findById(Long bookingId, Long userId) {
        userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь " + userId + " не найден"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new NotFoundException("Бронирование " + bookingId + " не найдено"));
        return BookingMapper.mapBookingToDto(booking);
    }

    @Override
    public List<BookingDtoResponse> findAllByUser(Long userId, State state) {
        userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь " + userId + " не найден"));

        LocalDateTime now = LocalDateTime.now();
        List<Booking> result = switch (state) {
            case CURRENT -> bookingRepository.findAllByUserIdAndEndAfterOrderByStartDesc(userId, now);
            case PAST -> bookingRepository.findAllByUserIdAndEndBeforeOrderByStartDesc(userId, now);
            case FUTURE -> bookingRepository.findAllByUserIdAndStartAfterOrderByStartDesc(userId, now);
            case WAITING -> bookingRepository.findAllByUserIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case REJECTED -> bookingRepository.findAllByUserIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default -> bookingRepository.findAllByUserId(userId);
        };

        return BookingMapper.mapBookingToDto(result);
    }

    @Override
    public List<BookingDtoResponse> findAllByUserItems(Long userId, State state) {
        userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь " + userId + " не найден"));

        List<Booking> bookings;

        Status status = switch (state) {
            case CURRENT -> CURRENT;
            case PAST -> PAST;
            case FUTURE -> Status.FUTURE;
            case WAITING -> Status.WAITING;
            case REJECTED -> Status.REJECTED;
            default -> null;
        };

        if (status == null) {
            bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
        } else {
            bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, status);
        }

        return bookings
                .stream()
                .map(BookingMapper::mapBookingToDto)
                .collect(Collectors.toList());
    }
}