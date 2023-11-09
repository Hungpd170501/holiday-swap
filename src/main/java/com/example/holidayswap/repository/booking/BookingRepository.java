package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//    @Query(value = "SELECT b.* From booking b join co_owner o on b.property_id = o.property_id and b.room_id = o.room_id where (?1 BETWEEN b.check_in_date AND b.check_out_date) OR( ?2 BETWEEN b.check_in_date AND b.check_out_date) AND b.room_id = ?3 AND b.property_id =?4", nativeQuery = true)
//    List<Booking> checkListBookingByCheckinDateAndCheckoutDateAndRoomIdAndPropertyId(Date checkInDate, Date checkOutDate, String roomId, Long propertyId);
    @Query(value = "SELECT* FROM booking b WHERE (?2 > check_in_date AND ?2 < check_out_date)\n" +
            "   OR (?3 > check_in_date AND ?3 < check_out_date) OR(?2 < check_in_date AND ?3 > check_out_date ) and book_id = ?1", nativeQuery = true)
    List<Booking> checkBookingIsAvailableByCheckinDateAndCheckoutDate(Long availableTimeId, Date checkInDate, Date checkOutDate);
    @Query("select b from Booking b where b.userBookingId = ?1")
    List<Booking> findAllByUserId(Long userId);

    @Query(value = "SELECT b.* FROM booking b where b.owner_id= ?1", nativeQuery = true)
    List<Booking> findAllByOwnerLogin(Long userId);

    List<Booking> findAllByDateBookingContaining(String date);

    List<Booking> findAllByDateBookingBetween(String startDate, String endDate);
}
