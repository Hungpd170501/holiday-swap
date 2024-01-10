package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = """
            SELECT* FROM booking b
                            WHERE (
                                ( date (?2) > date (check_in_date) AND date (?2) < date (check_out_date))
                                OR (date (?3) > date (check_in_date) AND date (?3) < date (check_out_date))
                                OR( date  (?2) <= date (check_in_date) AND date (?3) >= date (check_out_date) ))
                            
                              and available_time_id = ?1
                              and (b.status = 5 OR b.status = 6)
                              
            """, nativeQuery = true)
    List<Booking> checkBookingIsAvailableByCheckinDateAndCheckoutDate(Long availableTimeId, Date checkInDate, Date checkOutDate);

    @Query(value = "SELECT* FROM booking b WHERE ?1 = check_in_date AND ?2 = check_out_date AND available_time_id = ?3 and b.status = 5", nativeQuery = true)
    Booking checkBookingIsAvailableByCheckinDateAndCheckoutDateAndAvailableId(Date checkInDate, Date checkOutDate, Long availableTimeId);

    @Query("select b from Booking b where b.userBookingId = ?1")
    List<Booking> findAllByUserId(Long userId);

    @Query(value = "SELECT b.* FROM booking b where b.owner_id= ?1", nativeQuery = true)
    List<Booking> findAllByOwnerLogin(Long userId);

    List<Booking> findAllByDateBookingContaining(String date);

    @Query("select b from Booking b where ?1 <= b.dateBooking and ?2 >= b.dateBooking and b.status = 5")
    List<Booking> findAllByDateBookingBetween(String startDate, String endDate);

    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b where b.availableTimeId = ?1 and b.status = ?2")
    List<TimeHasBooked> findAllByAvailableTimeIdAndStatus(Long availableTimeId,
                                                          EnumBookingStatus.BookingStatus bookingStatus);

    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b inner join b.availableTime at inner join  at.coOwner co inner join co.timeFrames tf" +
            " where tf.id = ?1  and b.status = ?2 ")
    List<TimeHasBooked> findAllByTimeFrameIdAndStatus(Long timeFrameId,
                                                      EnumBookingStatus.BookingStatus bookingStatus);

    @Query("""
            select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)
            from Booking b
            inner join b.availableTime at
            inner join at.coOwner co
            where at.coOwnerId = :coOwnerId and extract(year from b.checkInDate) = :year""")
    List<TimeHasBooked> getTimeHasBooked(@Param("coOwnerId") Long coOwnerId,
                                         @Param("year") int year);

    @Query("select " +
            "CASE WHEN count(b) > 0 THEN TRUE ELSE FALSE END" +
            " from Booking b inner join b.availableTime at inner join at.coOwner co inner join co" +
            ".timeFrames tf" +
            " where b.userBookingId = :userId and at.id = :availableTimeId")
    boolean IsUserBooKed(
            @Param("availableTimeId") Long availableTimeId, @Param("userId") Long userId
    );

    @Query("select " +
            "CASE WHEN date(:now) > date(b.checkOutDate) THEN TRUE ELSE FALSE END" +
            " from Booking b inner join b.availableTime at inner join at.coOwner co inner join co.timeFrames tf" +
            " where b.id = :bookingId")
    boolean isDoneTraveled(
            @Param("bookingId") Long bookingId, @Param("now") Date now
    );

    @Query(value = """
            select case
                        when :now < ((b.check_out_date + interval '7 days')) then true
                        else false
                        end
             from booking b
                      inner join available_time av on
                 b.available_time_id = av.available_time_id
                      inner join co_owner co on co.co_owner_id = av.co_owner_id
                      inner join time_frame tf on
                 co.co_owner_id = tf.co_owner_id
             where b.book_id = :bookingId
             """, nativeQuery = true)
    boolean isOutDateRating(@Param("bookingId") Long bookingId, @Param("now") Date now);

    @Query(value = """
            SELECT b.*
                    FROM booking b
                             join available_time a on b.available_time_id = a.available_time_id
                             join
                         co_owner co on a.co_owner_id = co.co_owner_id
                             join property on property.property_id = co.property_id
                             join resort ON resort.resort_id = property.resort_id
                    where property.resort_id = ?1
                      and resort.resort_status != 'DEACTIVE'
                      and b.status = 5
                      and b.type_of_booking = 'RENT'
                      and ((date(?2) > date(check_in_date) AND date(?2) < date(check_out_date))
                        OR (date(?3) > date(check_in_date) AND date(?3) < date(check_out_date))
                        OR (date(?2) <= date(check_in_date) AND date(?3) >= date(check_out_date)))
                    """, nativeQuery = true)
    List<Booking> getListBookingByResortIdAndDate(Long resortId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            SELECT b.*
            FROM booking b
                     join available_time a on b.available_time_id = a.available_time_id
                     join co_owner co on a.co_owner_id = co.co_owner_id
                     join property on property.property_id = co.property_id
            where property.property_id = ?1
              and property.is_deleted = false
              and property.status = 'ACTIVE'
              and b.status = 5
              and b.type_of_booking = 'RENT'
              and ((date(?2) > date(check_in_date) AND date(?2) < date(check_out_date))
                OR (date(?3) > date(check_in_date) AND date(?3) < date(check_out_date))
                OR (date(?2) <= date(check_in_date) AND date(?3) >= date(check_out_date)))
            """, nativeQuery = true)
    List<Booking> getListBookingByPropertyIdAndDate(Long propertyId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select b from Booking b where b.uuid = ?1")
    Booking findByUuid(String uuid);

    @Query(value = """
            SELECT b.* FROM booking b where date (b.check_out_date) <= date(?1) and status = ?2 and transfer_status = ?3 
             """, nativeQuery = true)
    List<Booking> getListBookingByDateAndStatusAndTransferStatus(LocalDate date, int status, int transferStatus);

    @Query(value = """
            SELECT b.*
            FROM booking b
                     join available_time a on b.available_time_id = a.available_time_id
                     join co_owner co on a.co_owner_id = co.co_owner_id
                     join property on property.property_id = co.property_id
                     join resort ON resort.resort_id = property.resort_id
            where property.resort_id = ?1
              and resort.resort_status != 'DEACTIVE'
              and b.status = 5
              and b.type_of_booking = 'RENT'
              and (date(?2) <= date(check_in_date))
            """, nativeQuery = true)
    List<Booking> getListBookingHasCheckinAfterDeactiveDate(Long resortId, LocalDateTime startDate);

    @Query(value = """
            SELECT b.*
            FROM booking b
                     join available_time a on b.available_time_id = a.available_time_id
                     join co_owner co on a.co_owner_id = co.co_owner_id
                     join property on property.property_id = co.property_id
            where property.property_id = ?1
              and property.is_deleted = false
              and property.status = 'ACTIVE'
              and b.status = 5
              and b.type_of_booking = 'RENT'
              and (date(?2) <= date(check_in_date))
            """, nativeQuery = true)
    List<Booking> getListBookingPropertyHasCheckinAfterDeactiveDate(Long property, LocalDateTime startDate);


    @Query("select new com.example.holidayswap.domain.dto.response.booking.TimeHasBooked(b.checkInDate, b.checkOutDate)" +
            "from Booking b " +
            "inner join b.availableTime at " +
            " where at.coOwnerId = :co_owner_id  and b.status = 5 ")
    List<TimeHasBooked> getTimeHasBookedByCoOwnerId(@Param("co_owner_id") Long coOwnerId);
}
