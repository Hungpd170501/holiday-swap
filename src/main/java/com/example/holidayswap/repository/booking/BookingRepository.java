package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b where b.availableTimeId = ?1 and b.status = ?2")
    List<TimeHasBooked> findAllByAvailableTimeIdAndStatus(Long availableTimeId,
                                                          EnumBookingStatus.BookingStatus bookingStatus);

    @Query("select " +
            "CASE WHEN count(b) > 0 THEN TRUE ELSE FALSE END" +
            " from Booking b inner join b.availableTime at inner join at.timeFrame tf inner join tf" +
            ".coOwner co" +
            " where b.userBookingId = :userId and co.property.id = :propertyId")
    boolean IsUserBooKed(
            @Param("propertyId") Long propertyId, @Param("userId") Long userId
    );
}
