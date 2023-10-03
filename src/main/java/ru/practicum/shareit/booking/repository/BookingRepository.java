package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStartAfterAndEndBetweenAndItemId(LocalDateTime start, LocalDateTime startTwo, LocalDateTime end, Long id);

    List<Booking> findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long bookerId, LocalDateTime start,
                                                                              LocalDateTime end);

    List<Booking> findByBookerIdAndEndIsBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findByBookerIdAndStartIsAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    List<Booking> findByBookerIdAndStatus(long bookerId, BookingStatus status);

    List<Booking> findByItemOwnerIdOrderByStartDesc(long ownerId);

    List<Booking> findByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long ownerId, LocalDateTime start,
                                                                                 LocalDateTime end);

    List<Booking> findByItemOwnerIdAndEndIsBeforeOrderByStartDesc(long ownerId, LocalDateTime end);

    List<Booking> findByItemOwnerIdAndStartIsAfterOrderByStartDesc(long ownerId, LocalDateTime start);

    List<Booking> findByItemOwnerIdAndStatus(long ownerId, BookingStatus status);

    Booking findFirstByItemIdAndStartLessThanEqualAndStatusOrderByStartDesc(long itemId, LocalDateTime start,
                                                                            BookingStatus status);

    Booking findFirstByItemIdAndStartGreaterThanEqualAndStatusOrderByStartAsc(long itemId, LocalDateTime start,
                                                                              BookingStatus status);

    List<Booking> findByItemIdAndBookerIdAndEndIsBeforeAndStatus(long itemId, long bookerId, LocalDateTime end,
                                                                 BookingStatus status);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long bookerId, Pageable page);

    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId, Pageable page);
}
