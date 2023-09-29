package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @FutureOrPresent(message = "Дата/время старта бронирования не может быть в прошлом!")
    @NotNull(message = "Дата/время старта бронирования не может быть пустой!")
    private LocalDateTime start;

    @FutureOrPresent(message = "Дата/время окончания бронирования не может быть в прошлом!")
    @NotNull(message = "Дата/время окончания бронирования не может быть пустой!")
    private LocalDateTime end;

    private long itemId;
}
