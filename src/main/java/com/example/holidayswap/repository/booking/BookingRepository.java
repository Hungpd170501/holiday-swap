package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//    @Query(value = "SELECT b.* From booking b join co_owner o on b.property_id = o.property_id and b.room_id = o.room_id where (?1 BETWEEN b.check_in_date AND b.check_out_date) OR( ?2 BETWEEN b.check_in_date AND b.check_out_date) AND b.room_id = ?3 AND b.property_id =?4", nativeQuery = true)
//    List<Booking> checkListBookingByCheckinDateAndCheckoutDateAndRoomIdAndPropertyId(Date checkInDate, Date checkOutDate, String roomId, Long propertyId);
@Query(value = """
        SELECT* FROM booking b
        WHERE ((?2 > check_in_date AND ?2 < check_out_date)
        OR (?3 > check_in_date AND ?3 < check_out_date) 
        OR(?2 <= check_in_date AND ?3 >= check_out_date )) 
        and available_time_id = ?1""", nativeQuery = true)
    List<Booking> checkBookingIsAvailableByCheckinDateAndCheckoutDate(Long availableTimeId, Date checkInDate, Date checkOutDate);

    @Query(value = "SELECT* FROM booking b WHERE ?1 = check_in_date AND ?2 = check_out_date AND available_time_id = ?3", nativeQuery = true)
    Booking checkBookingIsAvailableByCheckinDateAndCheckoutDateAndAvailableId(Date checkInDate, Date checkOutDate, Long availableTimeId);
    @Query("select b from Booking b where b.userBookingId = ?1")
    List<Booking>findAllByUserId (Long userId);

    @Query(value = "SELECT b.* FROM booking b where b.owner_id= ?1", nativeQuery = true)
    List<Booking> findAllByOwnerLogin(Long userId);

    List<Booking> findAllByDateBookingContaining(String date);

    List<Booking> findAllByDateBookingBetween(String startDate, String endDate);
    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b where b.availableTimeId = ?1 and b.status = ?2")
    List<TimeHasBooked> findAllByAvailableTimeIdAndStatus(Long availableTimeId,
                                                          EnumBookingStatus.BookingStatus bookingStatus);

    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b inner join b.availableTime at inner join  at.timeFrame tf" +
            " where tf.id = ?1  and b.status = ?2 and tf.isDeleted = false ")
    List<TimeHasBooked> findAllByTimeFrameIdAndStatus(Long timeFrameId,
                                                          EnumBookingStatus.BookingStatus bookingStatus);

    @Query("""
            select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)
            from Booking b
            inner join b.availableTime at
            inner join at.timeFrame tf 
             where at.timeFrameId = :timeFameId and extract(year from b.checkInDate) = :year """)
    List<TimeHasBooked> getTimeHasBooked(@Param("timeFameId") Long availableTimeId,
                                         @Param("year") int year);
    @Query("select " +
            "CASE WHEN count(b) > 0 THEN TRUE ELSE FALSE END" +
            " from Booking b inner join b.availableTime at inner join at.timeFrame tf inner join tf" +
            ".coOwner co" +
            " where b.userBookingId = :userId and at.id = :availableTimeId")
    boolean IsUserBooKed(
            @Param("availableTimeId") Long availableTimeId, @Param("userId") Long userId
    );

    @Query("select " +
            "CASE WHEN date(:now) > date(b.checkOutDate) THEN TRUE ELSE FALSE END" +
            " from Booking b inner join b.availableTime at inner join at.timeFrame tf inner join tf" +
            ".coOwner co" +
            " where b.id = :bookingId")
    boolean isDoneTraveled(
            @Param("bookingId") Long bookingId, @Param("now") Date now
    );

    @Query(value = """
            select
                                    	case
                                    		when :now < ((b.check_out_date + interval '7 days') ) then true
                                    		else false
                                    	end
                                    from
                                    	booking b
                                    inner join available_time av on
                                    	b.available_time_id = av.available_time_id
                                    inner join time_frame tf on
                                    	tf.time_frame_id = av.time_frame_id
                                    inner join co_owner co on
                                    	co.property_id = tf.property_id
                                    	and co.room_id = tf.room_id
                                    	and co.user_id = tf.user_id
                                    where
                                    	b.book_id = :bookingId
             """, nativeQuery = true)
    boolean isOutDateRating(@Param("bookingId") Long bookingId, @Param("now") Date now);

    @Query(value = """

            select b.book_id,
                           b.check_in_date,
                           b.check_out_date,
                           b.price,
                           b.status,
                           b.actual_price,
                           b.available_time_id,
                           b.commission,
                           b.owner_id,
                           b.total_days,
                           b.user_booking_id,
                           b.date_booking,
                           b.status_check_return,
                           b.total_member
                    from booking b
                             inner join available_time at on
                        at.available_time_id = b.available_time_id
                             inner join time_frame tf on
                        tf.time_frame_id = at.time_frame_id
                    where tf.time_frame_id = :time_frame_id
                      and (date(b.check_in_date),
                           date(b.check_out_date)) overlaps (date(:check_in_date),
                                                             date(:check_out_date))
                    """, nativeQuery = true)
    List<Booking> checkTimeFrameIsHaveAnyBookingYetInTheTimeYet(@Param("check_in_date") Date check_in_date,
                                                                @Param("check_out_date") Date check_out_date,
                                                                @Param("time_frame_id") Long time_frame_id);

    @Query(value = """
            SELECT b.* FROM booking b join available_time a on b.available_time_id = a.available_time_id join
                                            time_frame ON time_frame.time_frame_id = a.time_frame_id join property on property.property_id = time_frame.property_id
                                            join resort ON resort.resort_id = property.resort_id
                                            where property.resort_id = ?1 and b.check_in_date > date(?2) and resort.resort_status= 'ACTIVE' and b.status = 5
                    """, nativeQuery = true)
    List<Booking> getListBookingByResortIdAndDate(Long resortId, ZonedDateTime date);

    @Query(value = """
        SELECT b.* FROM booking b join available_time a on b.available_time_id = a.available_time_id join
        time_frame ON time_frame.time_frame_id = a.time_frame_id join property on property.property_id = time_frame.property_id
                    where property.property_id = ?1 and property.is_deleted = false and b.check_in_date > date(?2) and property.status = 'ACTIVE' and b.status = 5
        """, nativeQuery = true)
    List<Booking> getListBookingByPropertyIdAndDate(Long propertyId,ZonedDateTime date);
}
