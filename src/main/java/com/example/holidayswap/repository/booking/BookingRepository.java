package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT b.* From booking b join co_owner o on b.property_id = o.property_id and b.room_id = o.room_id where (b.check_in_date BETWEEN ?1 AND ?2) AND(b.check_out_date BETWEEN ?1 AND ?2) AND b.room_id = ?3 AND b.property_id =?4", nativeQuery = true)
    List<Booking> checkListBookingByCheckinDateAndCheckoutDateAndRoomIdAndPropertyId(Date checkInDate, Date checkOutDate, String roomId, Long propertyId);
}
