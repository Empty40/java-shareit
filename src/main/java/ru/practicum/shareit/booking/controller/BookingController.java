package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @Valid @RequestBody BookingRequestDto bookingDto) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long bookingId, @RequestParam boolean approved) {
        return bookingService.approveBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto findBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PathVariable long bookingId) {
        return bookingService.findBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingResponseDto> findBookingsByBooker(@RequestHeader("X-Sharer-User-Id") long userId,
                                                         @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                                         @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                         @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.findBookingsByBooker(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingResponseDto> findBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                                        @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                                        @RequestParam(value = "size", defaultValue = "30") Integer size,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.findBookingsByOwner(ownerId, state, from, size);

    }
}
