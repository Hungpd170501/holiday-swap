package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.booking.*;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.booking.BookingMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.booking.IssueBookingRepository;
import com.example.holidayswap.repository.booking.UserOfBookingRepository;
import com.example.holidayswap.repository.property.PropertyMaintenanceRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerMaintenanceRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.resort.ResortMaintanceRepository;
import com.example.holidayswap.service.EmailService;
import com.example.holidayswap.service.FileService;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.utils.Helper;
import com.example.holidayswap.utils.RedissonLockUtils;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class BookingServiceImpl implements IBookingService {
    private final FileService fileService;
    private final AvailableTimeRepository availableTimeRepository;
    private final BookingRepository bookingRepository;
    private final ITransferPointService transferPointService;
    private final IUserOfBookingService userOfBookingService;
    private final UserOfBookingRepository userOfBookingRepository;
    private final PushNotificationService pushNotificationService;

    private final CoOwnerRepository coOwnerRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final IssueBookingRepository issueBookingRepository;
    private final IIssueBookingService issueBookingService;
    private final BookingMapper bookingMapper;
    private final PropertyMaintenanceRepository propertyMaintenanceRepository;
    private final ResortMaintanceRepository resortMaintanceRepository;
    private final CoOwnerMaintenanceRepository coOwnerMaintenanceRepository;


    @Override
    @Transactional
    public EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException, IOException, WriterException, MessagingException {

        if (bookingRequest.getCheckInDate().compareTo(bookingRequest.getCheckOutDate()) >= 0)
            throw new EntityNotFoundException("Check in date must be before check out date");
        var booki = availableTimeRepository.findByIdAndDeletedFalse(bookingRequest.getAvailableTimeId()).orElseThrow(() -> new DataIntegrityViolationException("Not found!."));
        var co = coOwnerRepository.findByIdAndIsDeletedIsFalse(booki.getCoOwnerId()).orElseThrow();
        checkValidBooking(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
        if (Objects.equals(co.getUserId(), bookingRequest.getUserId()))
            throw new EntityNotFoundException("You can't book your own apartment");
        UserProfileResponse user = userService.getUserById(bookingRequest.getUserId());
        List<Booking> checkBookingOverlap;
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        var notificationRequestForOwner = new NotificationRequest();
        var notificationRequestForUserBooking = new NotificationRequest();
        Booking booking = new Booking();
        String qrCode;
        boolean checkMaintenance;
        long days;
        LocalDate localDateCheckin = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckInDate()));
        LocalDate localDateCheckout = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckOutDate()));
        AvailableTime availableTime;
        RLock fairLock = RedissonLockUtils.getFairLock("booking-" + bookingRequest.getAvailableTimeId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {

                availableTime = availableTimeRepository.findAvailableTimeByIdAndStartTimeAndEndTime(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()).orElseThrow(() -> new EntityNotFoundException("This availableTime not available in this time"));
//                 TODO: check booking of this apartment
                checkBookingOverlap = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDate(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
//                checkBooking = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDateAndAvailableId(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getAvailableTimeId());

                if (!checkBookingOverlap.isEmpty())
                    throw new EntityNotFoundException("This apartment is not available in this time");


                // TODO: create booking

                days = ChronoUnit.DAYS.between(localDateCheckin, localDateCheckout);

                //thêm đường dẫn vào trước uuidString
                qrCode = fileService.createQRCode("https://holiday-swap.vercel.app/informationBooking/" + uuidString);

                booking.setUuid(uuidString);
                booking.setQrcode(qrCode);
                booking.setCheckInDate(bookingRequest.getCheckInDate());
                booking.setCheckOutDate(bookingRequest.getCheckOutDate());
                booking.setUserBookingId(bookingRequest.getUserId());
                booking.setOwnerId(availableTime.getCoOwner().getUserId());
                booking.setAvailableTimeId(bookingRequest.getAvailableTimeId());
                booking.setAvailableTime(availableTime);
                booking.setTotalDays(days);
                booking.setTotalMember(bookingRequest.getNumberOfGuest());
                booking.setStatusCheckReturn(false);
                booking.setPrice(days * availableTime.getPricePerNight());
                booking.setCommission(booking.getPrice() * 10 / 100);
                booking.setDateBooking(Helper.getCurrentDate());
                booking.setActualPrice(booking.getPrice() - booking.getCommission());
                booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
                booking.setTransferStatus(EnumBookingStatus.TransferStatus.WAITING);
                booking.setTypeOfBooking(EnumBookingStatus.TypeOfBooking.RENT);
                bookingRepository.save(booking);
                userOfBookingService.saveUserOfBooking(booking.getId(), bookingRequest.getUserOfBookingRequests());


                //TODO trừ point trong ví

                transferPointService.payBooking(booking);
                //create notification for user booking
                 notificationRequestForUserBooking.setSubject("Booking Success");
                notificationRequestForUserBooking.setContent("Booking Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate());
                notificationRequestForUserBooking.setToUserId(bookingRequest.getUserId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
                //create notification for owner
                notificationRequestForOwner.setSubject("Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " has been booked");
                notificationRequestForOwner.setContent("Booking Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate());
                notificationRequestForOwner.setToUserId(booking.getAvailableTime().getCoOwner().getUserId());
                pushNotificationService.createNotification(notificationRequestForOwner);
                emailService.sendConfirmBookedHtml(booking, user.getEmail());
                return EnumBookingStatus.BookingStatus.SUCCESS;
            } finally {
                fairLock.unlock();
            }
        }
        return EnumBookingStatus.BookingStatus.FAILED;
    }

    private void isInMaintain(LocalDate start, LocalDate end, CoOwner coOwner) {
        //parse time
        LocalDateTime startD = start.atStartOfDay();
        LocalDateTime endD = end.atStartOfDay();
        var resMaintain = coOwner.getProperty().getResort().getResortMaintainces().stream().filter(e -> e.getType() == ResortStatus.MAINTENANCE);
        var propMaintain = coOwner.getProperty().getPropertyMaintenance().stream().filter(e -> e.getType() == PropertyStatus.MAINTENANCE);
        var apartmentMaintain = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentId(coOwner.getPropertyId(), coOwner.getRoomId()).stream().filter(e -> e.getType() == CoOwnerMaintenanceStatus.MAINTENANCE);
        resMaintain.forEach(e -> {
            if ((startD.isAfter(e.getStartDate()) || startD.isEqual(e.getStartDate())) && (startD.isBefore(e.getEndDate()) || startD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
            if ((endD.isAfter(e.getStartDate()) || endD.isEqual(e.getStartDate())) && (endD.isBefore(e.getEndDate()) || endD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Resort in maintain this time. Try to another time!.");
            }
        });
        propMaintain.forEach(e -> {
            if ((startD.isAfter(e.getStartDate()) || startD.isEqual(e.getStartDate())) && (startD.isBefore(e.getEndDate()) || startD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Property in maintain this time. Try to another time!.");
            }
            if ((endD.isAfter(e.getStartDate()) || endD.isEqual(e.getStartDate())) && (endD.isBefore(e.getEndDate()) || endD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Property in maintain this time. Try to another time!.");
            }
        });
        apartmentMaintain.forEach(e -> {
            if ((startD.isAfter(e.getStartDate()) || startD.isEqual(e.getStartDate())) && (startD.isBefore(e.getEndDate()) || startD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Apartment in maintain this time. Try to another time!.");
            }
            if ((endD.isAfter(e.getStartDate()) || endD.isEqual(e.getStartDate())) && (endD.isBefore(e.getEndDate()) || endD.isEqual(e.getEndDate()))) {
                throw new DataIntegrityViolationException("Apartment in maintain this time. Try to another time!.");
            }
        });
    }

    private void checkIsInDeactivate(LocalDate start, LocalDate end, CoOwner coOwner) {
        LocalDateTime startD = start.atStartOfDay();
        LocalDateTime endD = end.atStartOfDay();
        var resDeactivate = coOwner.getProperty().getResort().getResortMaintainces().stream().filter(e -> e.getType() == ResortStatus.DEACTIVATE).toList();
        var propDeactivate = coOwner.getProperty().getPropertyMaintenance().stream().filter(e -> e.getType() == PropertyStatus.DEACTIVATE).toList();
        var apartmentMaintain = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentId(coOwner.getPropertyId(), coOwner.getRoomId()).stream().filter(e -> e.getType() == CoOwnerMaintenanceStatus.DEACTIVATE).toList();
        if (!resDeactivate.isEmpty()) {
            if ((resDeactivate.get(0).getStartDate().isBefore(startD) || resDeactivate.get(0).getStartDate().isEqual(startD)) || (resDeactivate.get(0).getStartDate().isBefore(endD) || resDeactivate.get(0).getStartDate().isEqual(endD))) {
                throw new DataIntegrityViolationException("Resort is deactivate this time. Try to another time!.");
            }
        }
        if (!propDeactivate.isEmpty()) {
            if ((propDeactivate.get(0).getStartDate().isBefore(startD) || propDeactivate.get(0).getStartDate().isEqual(startD)) || (propDeactivate.get(0).getStartDate().isBefore(endD) || propDeactivate.get(0).getStartDate().isEqual(endD))) {
                throw new DataIntegrityViolationException("Property is deactivate this time. Try to another time!.");
            }
        }
        if (!apartmentMaintain.isEmpty()) {
            if ((apartmentMaintain.get(0).getStartDate().isBefore(startD) || apartmentMaintain.get(0).getStartDate().isEqual(startD)) || (apartmentMaintain.get(0).getStartDate().isBefore(endD) || apartmentMaintain.get(0).getStartDate().isEqual(endD))) {
                throw new DataIntegrityViolationException("Apartment is deactivate this time. Try to another time!.");
            }
        }
    }

    @Override
    public List<HistoryBookingResponse> historyBookingUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;

        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        List<Booking> userBooking = bookingRepository.findAllByUserId(user.getUserId());
        if (userBooking.size() > 0) {
            for (Booking booking : userBooking) {
                historyBookingResponses.add(new HistoryBookingResponse(booking.getId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getAvailableTime().getCoOwner().getProperty().getPropertyName(), booking.getAvailableTime().getCoOwner().getRoomId(), booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName(), booking.getStatus().name(), booking.getPrice(), booking.getAvailableTime().getCoOwner().getProperty().getPropertyImages().get(0).getLink(), booking.getDateBooking(), booking.getAvailableTimeId(), false, booking.getUser().getUsername(), booking.getUserOwner().getUsername())

                );
            }
        }
        return historyBookingResponses;
    }

    @Override
    public HistoryBookingDetailResponse historyBookingDetail(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(bookingId);
        var historyBookingDetailResponse = new HistoryBookingDetailResponse();

        historyBookingDetailResponse.setResortName(booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getAvailableTime().getCoOwner().getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getUserOfBookings().size());
        historyBookingDetailResponse.setOwnerEmail(booking.getAvailableTime().getCoOwner().getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getAvailableTime().getCoOwner().getProperty().getPropertyName());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);
        historyBookingDetailResponse.setAvailableTimeId(booking.getAvailableTimeId());
        historyBookingDetailResponse.setQrcode(booking.getQrcode());
        historyBookingDetailResponse.setPropertyImage(booking.getAvailableTime().getCoOwner().getProperty().getPropertyImages().get(0).getLink());
        historyBookingDetailResponse.setCreatedDate(booking.getDateBooking());

        return historyBookingDetailResponse;
    }

    @Override
    public List<HistoryBookingResponse> historyBookingOwnerLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;

        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        var bookingList = bookingRepository.findAllByOwnerLogin(user.getUserId());
        if (bookingList.size() > 0) {
            for (Booking booking : bookingList) {
                historyBookingResponses.add(new HistoryBookingResponse(booking.getId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getAvailableTime().getCoOwner().getProperty().getPropertyName(), booking.getAvailableTime().getCoOwner().getRoomId(), booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName(), booking.getStatus().name(), booking.getActualPrice(), booking.getAvailableTime().getCoOwner().getProperty().getPropertyImages().get(0).getLink(), booking.getDateBooking(), booking.getAvailableTimeId(), booking.getStatusCheckReturn(), booking.getUser().getUsername(), booking.getUserOwner().getUsername()));
            }

        }
        return historyBookingResponses;
    }

    @Override
    public HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(bookingId);
        var historyBookingDetailResponse = new HistoryDetailBookingOwnerResponse();

        historyBookingDetailResponse.setResortName(booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getAvailableTime().getCoOwner().getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getUserOfBookings().size());
        historyBookingDetailResponse.setMemberBookingEmail(booking.getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getAvailableTime().getCoOwner().getProperty().getPropertyName());
        historyBookingDetailResponse.setCommission(booking.getCommission() + "%");
        historyBookingDetailResponse.setTotal(booking.getActualPrice());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);
        historyBookingDetailResponse.setAvailableTimeId(booking.getAvailableTimeId());
        historyBookingDetailResponse.setPropertyImage(booking.getAvailableTime().getCoOwner().getProperty().getPropertyImages().get(0).getLink());
        historyBookingDetailResponse.setCreatedDate(booking.getDateBooking());
        historyBookingDetailResponse.setCanCancel(booking.getStatusCheckReturn());
        historyBookingDetailResponse.setUserNameBooking(booking.getUser().getUsername());
        historyBookingDetailResponse.setUserNameOwner(booking.getUserOwner().getUsername());

        return historyBookingDetailResponse;
    }

    @Override
    public List<TimeHasBooked> getTimeHasBooked(Long timeFameId, int year) {

        var listTimeHasBooked = bookingRepository.getTimeHasBooked(timeFameId, year);

        return listTimeHasBooked;
    }

    @Override
    public List<TimeHasBooked> getTimeHasBookedByCoOwnerId(Long coOwnerId) {
        var listTimeHasBooked = bookingRepository.getTimeHasBookedByCoOwnerId(coOwnerId);

        return listTimeHasBooked;
    }

    @Override
    public void deactiveResortNotifyBookingUser(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus, List<String> listImage) throws IOException, MessagingException {
        ZonedDateTime hcmZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        List<Booking> bookingList = new ArrayList<>();
        //get list booking of resort and date booking is after current date

        if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name())) {
            bookingList = bookingRepository.getListBookingByResortIdAndDate(resortId, startDate, startDate);
            bookingList.addAll(bookingRepository.getListBookingHasCheckinAfterDeactiveDate(resortId, startDate));
        } else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name())) {
            bookingList = bookingRepository.getListBookingByResortIdAndDate(resortId, startDate, endDate);
        }


        if (bookingList.size() > 0) {
            bookingList.forEach(booking -> {
                //create notification for user booking
                var checkIssueBooking = issueBookingRepository.findByBookingId(booking.getId());
                if (checkIssueBooking == null) {
                    issueBookingService.createIssueBooking(booking.getId(), "Booking Id: " + booking.getId() + " has been issue because resort is " + resortStatus.name());
                    booking.setStatusCheckReturn(true);
                }
                var notificationRequestForUserBooking = new NotificationRequest();
                if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is deactive date: " + startDate);
                else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is maintenance date: " + startDate + " to " + endDate);
                notificationRequestForUserBooking.setSubject("Resort of your booking is ");
                notificationRequestForUserBooking.setContent("Booking Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate() + " can be cancel,contact owner for more details");
                notificationRequestForUserBooking.setToUserId(booking.getUserBookingId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);

                bookingRepository.save(booking);
            });
        }
        // get list cowner of resort
        List<CoOwner> coOwnerList = coOwnerRepository.getListCownerByResortId(resortId);
        if (coOwnerList.size() > 0) {
            coOwnerList.forEach(coOwner -> {
                //create notification for user booking
                var notificationRequestForUserBooking = new NotificationRequest();
                if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your ownership is deactive date: " + startDate);
                else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your ownership is maintenance date: " + startDate + " to " + endDate);
                notificationRequestForUserBooking.setContent("Booking Apartment " + coOwner.getRoomId() + " of resort " + coOwner.getProperty().getResort().getResortName());
                notificationRequestForUserBooking.setToUserId(coOwner.getUserId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
            });
        }
    }

    @Override
    public void deactivePropertyNotifyBookingUser(Long propertyId, LocalDateTime startDate, LocalDateTime endDate, PropertyStatus resortStatus, List<String> listImage) throws IOException, MessagingException {
//        String currentDate = Helper.getCurrentDateWithoutTime();

        List<Booking> bookingList = new ArrayList<>();
        if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name())) {
            bookingList = bookingRepository.getListBookingByPropertyIdAndDate(propertyId, startDate, endDate);
            bookingList.addAll(bookingRepository.getListBookingPropertyHasCheckinAfterDeactiveDate(propertyId, startDate));
        } else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name())) {
            bookingList = bookingRepository.getListBookingByPropertyIdAndDate(propertyId, startDate, endDate);
        }
        //get list booking of resort and date booking is after current date
//        List<Booking> bookingList =
        if (bookingList.size() > 0) {
            bookingList.forEach(booking -> {
                //create notification for user booking
                var checkIssueBooking = issueBookingRepository.findByBookingId(booking.getId());
                if (checkIssueBooking == null) {
                    issueBookingService.createIssueBooking(booking.getId(), "Booking Id: " + booking.getId() + " has been issue because property is " + resortStatus.name());
                    booking.setStatusCheckReturn(true);
                }


                var notificationRequestForUserBooking = new NotificationRequest();
                if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is deactive date: " + startDate);
                else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is maintenance date: " + startDate + " to " + endDate);
                notificationRequestForUserBooking.setContent("Booking Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of property " + booking.getAvailableTime().getCoOwner().getProperty().getPropertyName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate() + " can be cancel, contact owner for more details");
                notificationRequestForUserBooking.setToUserId(booking.getUserBookingId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
                bookingRepository.save(booking);
            });
            //     get list cowner of resort
            List<CoOwner> coOwnerList = coOwnerRepository.getListCoOwnerByPropertyId(propertyId);
            if (coOwnerList.size() > 0) {
                coOwnerList.forEach(coOwner -> {
                    //create notification for user booking
                    var notificationRequestForUserBooking = new NotificationRequest();
                    if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                        notificationRequestForUserBooking.setSubject("Resort of your ownership is deactive date: " + startDate);
                    else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                        notificationRequestForUserBooking.setSubject("Resort of your ownership is maintenance date: " + startDate + " to " + endDate);
                    notificationRequestForUserBooking.setContent("Booking Apartment " + coOwner.getRoomId() + " of resort " + coOwner.getProperty().getResort().getResortName() + " can't post or book anymore");
                    notificationRequestForUserBooking.setToUserId(coOwner.getUserId());
                    pushNotificationService.createNotification(notificationRequestForUserBooking);
                });
            }
        }
    }

    @Override
    public void deactiveApartmentNotifyBookingUser(Long property, String apartmentId, LocalDateTime startDate, LocalDateTime endDate, CoOwnerMaintenanceStatus resortStatus, List<String> listImage) throws IOException, MessagingException {
        List<Booking> bookingList = new ArrayList<>();
        if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name())) {
            bookingList = bookingRepository.getListBookingByPropertyIdAndApartmentIdAndDate(property, apartmentId, startDate, endDate);
            bookingList.addAll(bookingRepository.getListBookingApartmentHasCheckinAfterDeactiveDate(property, apartmentId, startDate));
        } else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name())) {
            bookingList = bookingRepository.getListBookingByPropertyIdAndApartmentIdAndDate(property, apartmentId, startDate, endDate);
        }
        //get list booking of resort and date booking is after current date
//        List<Booking> bookingList =
        if (bookingList.size() > 0) {
            bookingList.forEach(booking -> {
                //create notification for user booking
                var checkIssueBooking = issueBookingRepository.findByBookingId(booking.getId());
                if (checkIssueBooking == null) {
                    issueBookingService.createIssueBooking(booking.getId(), "Booking Id: " + booking.getId() + " has been issue because apartment is " + resortStatus.name());
                    booking.setStatusCheckReturn(true);
                }

                var notificationRequestForUserBooking = new NotificationRequest();
                if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is deactive date: " + startDate);
                else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                    notificationRequestForUserBooking.setSubject("Resort of your booking is maintenance date: " + startDate + " to " + endDate);
                notificationRequestForUserBooking.setContent("Booking Apartment " + booking.getAvailableTime().getCoOwner().getRoomId() + " of property " + booking.getAvailableTime().getCoOwner().getProperty().getPropertyName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate() + " can be cancel, contact owner for more details");
                notificationRequestForUserBooking.setToUserId(booking.getUserBookingId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
                bookingRepository.save(booking);
            });
            //     get list cowner of resort
            List<CoOwner> coOwnerList = coOwnerRepository.getListCoOwnerByPropertyIdAndApartmentId(property, apartmentId);
            if (coOwnerList.size() > 0) {
                coOwnerList.forEach(coOwner -> {
                    //create notification for user booking
                    var notificationRequestForUserBooking = new NotificationRequest();
                    if (resortStatus.name().equals(ResortStatus.DEACTIVATE.name()))
                        notificationRequestForUserBooking.setSubject("Apartment of your ownership is deactive date: " + startDate);
                    else if (resortStatus.name().equals(ResortStatus.MAINTENANCE.name()))
                        notificationRequestForUserBooking.setSubject("Apartment of your ownership is maintenance date: " + startDate + " to " + endDate);
                    notificationRequestForUserBooking.setContent("Booking Apartment " + coOwner.getRoomId() + " of resort " + coOwner.getProperty().getResort().getResortName() + " can't post or book anymore");
                    notificationRequestForUserBooking.setToUserId(coOwner.getUserId());
                    pushNotificationService.createNotification(notificationRequestForUserBooking);
                });
            }
        }
        //get list booking of resort and date booking is after current date
    }

    @Override
    public String returnPointBooking(Long bookingId) throws InterruptedException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

//        long threeDaysAgoMillis = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000);
//        Date threeDaysAgo = new Date(threeDaysAgoMillis);
//        if (booking.getCheckOutDate().before(threeDaysAgo)) {
//            booking.setStatusCheckReturn(false);
//            bookingRepository.save(booking);
//            throw new EntityNotFoundException("Can not return point because check out date is before 3 days ago");
//        }
        if (booking.getStatusCheckReturn()) {
            booking.setStatus(EnumBookingStatus.BookingStatus.CANCELLED);
            booking.setStatusCheckReturn(false);
            transferPointService.returnPoint(booking.getOwnerId(), booking.getUserBookingId(), booking.getActualPrice(), booking.getCommission());
            bookingRepository.save(booking);
            return "Refund point success";
        }
        return "can not refund this booking";
    }

    @Override
    @Transactional
    public void refundPointBookingToOwner(LocalDate endDate) {
        List<Booking> bookingList = bookingRepository.getListBookingByDateAndStatusAndTransferStatus(endDate, 5, 0);
        if (bookingList.size() > 0) {
            bookingList.forEach(booking -> {
                try {
                    if (issueBookingRepository.findById(booking.getId()).isEmpty() || issueBookingRepository.findById(booking.getId()).get().getStatus().equals(EnumBookingStatus.IssueBookingStatus.RESOLVE))
                        transferPointService.refundPointBookingToOwner(booking.getId());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }

    @Override
    public HistoryBookingDetailResponse historyBookingByUUID(String uuid) {
        var booking = bookingRepository.findByUuid(uuid);
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(booking.getId());
        var historyBookingDetailResponse = new HistoryBookingDetailResponse();

        historyBookingDetailResponse.setResortName(booking.getAvailableTime().getCoOwner().getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getAvailableTime().getCoOwner().getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getUserOfBookings().size());
        historyBookingDetailResponse.setOwnerEmail(booking.getAvailableTime().getCoOwner().getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getAvailableTime().getCoOwner().getProperty().getPropertyName());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);
        historyBookingDetailResponse.setAvailableTimeId(booking.getAvailableTimeId());
        historyBookingDetailResponse.setQrcode(booking.getQrcode());
        historyBookingDetailResponse.setPropertyImage(booking.getAvailableTime().getCoOwner().getProperty().getPropertyImages().get(0).getLink());
        historyBookingDetailResponse.setCreatedDate(booking.getDateBooking());

        return historyBookingDetailResponse;
    }

//    @Override
//    @Transactional
//    public ExchangeResponse createExchange(Exchange exchange) throws InterruptedException, IOException, WriterException {
//        if(exchange.getCheckInDateOfUser1().after(exchange.getCheckOutDateOfUser1()) || exchange.getCheckInDateOfUser2().after(exchange.getCheckOutDateOfUser2()))
//            throw new RuntimeException("Check in date must be before check out date");
//        UserProfileResponse user1 = userService.getUserById(exchange.getUserIdOfUser1());
//        UserProfileResponse user2 = userService.getUserById(exchange.getUserIdOfUser2());
//        List<Booking> checkBookingOverlap1, checkBookingOverlap2;
//        UUID uuid = UUID.randomUUID();
//        String uuidString1 = uuid.toString();
//        String uuidString2 = uuid.toString();
//        var notificationRequestForOwner = new NotificationRequest();
//        var notificationRequestForUserBooking = new NotificationRequest();
//        LocalDate localDateCheckin1, localDateCheckout1, localDateCheckin2, localDateCheckout2;
////        LocalDate localDateCheckout;
//        Booking booking1 = new Booking();
//        Booking booking2 = new Booking();
//        String qrCode1, qrCode2;
//        AvailableTime availableTime1, availableTime2;
//        long days1, days2;
//        RLock fairLock1 = RedissonLockUtils.getFairLock("booking-" + exchange.getAvailableTimeIdOfUser1());
//        boolean tryLock = fairLock1.tryLock(10, 10, TimeUnit.SECONDS);
//        RLock fairLock2 = RedissonLockUtils.getFairLock("booking-" + exchange.getAvailableTimeIdOfUser2());
//        boolean tryLock2 = fairLock2.tryLock(10, 10, TimeUnit.SECONDS);
//        if (tryLock && tryLock2) {
//            try {
//                availableTime1 = availableTimeRepository.findAvailableTimeByIdAndStartTimeAndEndTime(exchange.getAvailableTimeIdOfUser1(), exchange.getCheckInDateOfUser2(), exchange.getCheckOutDateOfUser2()).orElseThrow(() -> new EntityNotFoundException("Apartment of "+ user1.getUsername() +"not available in this time"));
//                availableTime2 = availableTimeRepository.findAvailableTimeByIdAndStartTimeAndEndTime(exchange.getAvailableTimeIdOfUser2(), exchange.getCheckInDateOfUser1(), exchange.getCheckOutDateOfUser1()).orElseThrow(() -> new EntityNotFoundException("Apartment of "+ user2.getUsername() +"not available in this time"));
////                 TODO: check booking of this apartment
//                checkBookingOverlap1 = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDate(exchange.getAvailableTimeIdOfUser1(), exchange.getCheckInDateOfUser2(), exchange.getCheckInDateOfUser2());
//                checkBookingOverlap2 = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDate(exchange.getAvailableTimeIdOfUser2(), exchange.getCheckInDateOfUser1(), exchange.getCheckInDateOfUser1());
//
//                //                checkBooking = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDateAndAvailableId(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getAvailableTimeId());
//
//                if (!checkBookingOverlap1.isEmpty() && !checkBookingOverlap2.isEmpty())
//                    throw new EntityNotFoundException("This apartment is not available in this time");
//
//
//                // TODO: create booking
//                localDateCheckin1 = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(exchange.getCheckInDateOfUser1()));
//                localDateCheckout1 = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(exchange.getCheckOutDateOfUser1()));
//                localDateCheckin2 = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(exchange.getCheckInDateOfUser2()));
//                localDateCheckout2 = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(exchange.getCheckOutDateOfUser2()));
//
//                days1 = ChronoUnit.DAYS.between(localDateCheckin1, localDateCheckout1);
//                days2 = ChronoUnit.DAYS.between(localDateCheckin2, localDateCheckout2);
//
//                //thêm đường dẫn vào trước uuidString
//                qrCode1 = fileService.createQRCode("https://holiday-swap.vercel.app/informationBooking/" + uuidString1);
//                qrCode2 = fileService.createQRCode("https://holiday-swap.vercel.app/informationBooking/" + uuidString2);
//
//                booking1.setUuid(uuidString1);
//                booking1.setQrcode(qrCode1);
//                booking1.setCheckInDate(exchange.getCheckInDateOfUser1());
//                booking1.setCheckOutDate(exchange.getCheckOutDateOfUser1());
//                booking1.setUserBookingId(exchange.getUserIdOfUser1());
//                booking1.setOwnerId(exchange.getUserIdOfUser2());
//                booking1.setAvailableTimeId(exchange.getAvailableTimeIdOfUser2());
////                booking1.setAvailableTime(availableTime);
//                booking1.setTotalDays(days1);
//                booking1.setTotalMember(exchange.getTotalMemberOfUser1());
//                booking1.setStatusCheckReturn(false);
//                booking1.setPrice(exchange.getPriceOfUser1());
//                booking1.setCommission(0D);
//                booking1.setDateBooking(Helper.getCurrentDate());
//                booking1.setActualPrice(exchange.getPriceOfUser1());
//                booking1.setStatus(EnumBookingStatus.BookingStatus.WAITING_EXCHANGE);
//                booking1.setTransferStatus(EnumBookingStatus.TransferStatus.WAITING);
//                booking1.setTypeOfBooking(EnumBookingStatus.TypeOfBooking.EXCHANGE);
//                booking1 = bookingRepository.save(booking1);
//
//                booking2.setUuid(uuidString2);
//                booking2.setQrcode(qrCode2);
//                booking2.setCheckInDate(exchange.getCheckInDateOfUser2());
//                booking2.setCheckOutDate(exchange.getCheckOutDateOfUser2());
//                booking2.setUserBookingId(exchange.getUserIdOfUser2());
//                booking2.setOwnerId(exchange.getUserIdOfUser1());
//                booking2.setAvailableTimeId(exchange.getAvailableTimeIdOfUser1());
////                booking2.setAvailableTime(availableTime);
//                booking2.setTotalDays(days2);
//                booking2.setTotalMember(exchange.getTotalMemberOfUser2());
//                booking2.setStatusCheckReturn(false);
//                booking2.setPrice(exchange.getPriceOfUser2());
//                booking2.setCommission(0D);
//                booking2.setDateBooking(Helper.getCurrentDate());
//                booking2.setActualPrice(exchange.getPriceOfUser2());
//                booking2.setStatus(EnumBookingStatus.BookingStatus.WAITING_EXCHANGE);
//                booking2.setTransferStatus(EnumBookingStatus.TransferStatus.WAITING);
//                booking2.setTypeOfBooking(EnumBookingStatus.TypeOfBooking.EXCHANGE);
//                booking2 = bookingRepository.save(booking2);
//
//
//                //TODO trừ point trong ví
//
////                transferPointService.payBooking(booking);
//                //create notification for user booking
//                notificationRequestForUserBooking.setSubject("Complete phase 1");
//                notificationRequestForUserBooking.setContent("Booking Apartment " + booking1.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking1.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking1.getCheckInDate() + " to " + booking1.getCheckOutDate());
//                notificationRequestForUserBooking.setToUserId(exchange.getUserIdOfUser1());
//                pushNotificationService.createNotification(notificationRequestForUserBooking);
//                //create notification for owner
//                notificationRequestForOwner.setSubject("Complete phase 1");
//                notificationRequestForUserBooking.setContent("Booking Apartment " + booking2.getAvailableTime().getCoOwner().getRoomId() + " of resort " + booking2.getAvailableTime().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking2.getCheckInDate() + " to " + booking2.getCheckOutDate());
//                notificationRequestForOwner.setToUserId(exchange.getUserIdOfUser2());
//                pushNotificationService.createNotification(notificationRequestForOwner);
//                emailService.sendConfirmBookedHtml(booking1, user1.getEmail());
//                emailService.sendConfirmBookedHtml(booking2, user2.getEmail());
//                ExchangeResponse exchangeResponse = new ExchangeResponse();
//                exchangeResponse.setBookingIdOfUser1(booking1.getId());
//                exchangeResponse.setBookingIdOfUser2(booking2.getId());
//                return exchangeResponse;
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            } finally {
//                fairLock1.unlock();
//                fairLock2.unlock();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void updateExchange(Exchange exchange,EnumBookingStatus.BookingStatus bookingStatus) {
//        Booking booking1 = bookingRepository.findById(exchange.getBookingIdOfUser1()).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
//        Booking booking2 = bookingRepository.findById(exchange.getBookingIdOfUser2()).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
//
//        booking1.setStatus(bookingStatus);
//        booking2.setStatus(bookingStatus);
//        bookingRepository.save(booking1);
//        bookingRepository.save(booking2);
//
//    }

    @Override
    public List<BookingCoOwnerResponse> historyBookingByCoOwnerId(Long coOwnerId) {
        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        var bookingList = bookingRepository.findAllByUserIdAndCoOwnerId(coOwnerId);
        return bookingList.stream().map(bookingMapper::toDtoResponse).collect(Collectors.toList());
    }

    @Override
    public Long createBookingExchange(BookingRequest bookingRequest) throws InterruptedException, IOException, WriterException, MessagingException {
        if (bookingRequest.getCheckInDate().compareTo(bookingRequest.getCheckOutDate()) >= 0)
            throw new EntityNotFoundException("Check in date must be before check out date");
        checkValidBooking(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
        var booki = availableTimeRepository.findByIdAndDeletedFalse(bookingRequest.getAvailableTimeId());
        if (booki.isPresent()) {
            if (booki.get().getCoOwner().getUserId() == bookingRequest.getUserId())
                throw new EntityNotFoundException("You can't book your own apartment");
        }
        List<Booking> checkBookingOverlap;
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        LocalDate localDateCheckin;
        LocalDate localDateCheckout;
        Booking booking = new Booking();
        String qrCode;
        long days;
        AvailableTime availableTime;
        RLock fairLock = RedissonLockUtils.getFairLock("booking-" + bookingRequest.getAvailableTimeId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                availableTime = availableTimeRepository.findAvailableTimeByIdAndStartTimeAndEndTime(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()).orElseThrow(() -> new EntityNotFoundException("This availableTime not available in this time"));

//                 TODO: check booking of this apartment
                checkBookingOverlap = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDate(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

                if (!checkBookingOverlap.isEmpty())
                    throw new EntityNotFoundException("This apartment is not available in this time");


                // TODO: create booking
                localDateCheckin = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckInDate()));
                localDateCheckout = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckOutDate()));
                days = ChronoUnit.DAYS.between(localDateCheckin, localDateCheckout);

                //thêm đường dẫn vào trước uuidString
                qrCode = fileService.createQRCode("https://holiday-swap.vercel.app/informationBooking/" + uuidString);

                booking.setUuid(uuidString);
                booking.setQrcode(qrCode);
                booking.setCheckInDate(bookingRequest.getCheckInDate());
                booking.setCheckOutDate(bookingRequest.getCheckOutDate());
                booking.setUserBookingId(bookingRequest.getUserId());
                booking.setOwnerId(availableTime.getCoOwner().getUserId());
                booking.setAvailableTimeId(bookingRequest.getAvailableTimeId());
                booking.setAvailableTime(availableTime);
                booking.setTotalDays(days);
                booking.setTotalMember(bookingRequest.getNumberOfGuest());
                booking.setStatusCheckReturn(false);
                booking.setPrice(days * availableTime.getPricePerNight());
                booking.setCommission(booking.getPrice() * 5 / 100);
                booking.setDateBooking(Helper.getCurrentDate());
                booking.setActualPrice(booking.getPrice() - booking.getCommission());
                booking.setStatus(EnumBookingStatus.BookingStatus.WAITING_EXCHANGE);
                booking.setTransferStatus(EnumBookingStatus.TransferStatus.WAITING);
                booking.setTypeOfBooking(EnumBookingStatus.TypeOfBooking.RENT);
                bookingRepository.save(booking);
                if (bookingRequest.getUserOfBookingRequests() != null) {
                    userOfBookingService.saveUserOfBooking(booking.getId(), bookingRequest.getUserOfBookingRequests());
                }
            } finally {
                fairLock.unlock();
            }
        }
        return booking.getId();
    }

    @Override
    @Transactional
    public void payBookingExchange(Long bookingId) throws InterruptedException, IOException, WriterException, MessagingException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        transferPointService.payBooking(booking);
        booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
        booking.setStatusCheckReturn(true);
        bookingRepository.save(booking);
    }

    @Override
    public void cancelBookingExchange(Long bookingId) throws InterruptedException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        if (booking.getStatus().equals(EnumBookingStatus.BookingStatus.SUCCESS)) {
            bookingRepository.save(booking);
            returnPointBooking(bookingId);
        } else booking.setStatus(EnumBookingStatus.BookingStatus.CANCELLED);

        bookingRepository.save(booking);

    }

    @Override
    public void checkValidBooking(Long availableTimeId, Date checkInDate, Date checkOutDate) {

        var availableTime = availableTimeRepository.findByIdAndDeletedFalse(availableTimeId).orElseThrow(() -> new EntityNotFoundException("Available time not found"));
        LocalDateTime checkinDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime checkoutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var co = coOwnerRepository.findByIdAndDeleted(availableTime.getCoOwnerId()).get();

        propertyMaintenanceRepository.findByPropertyIdAndStartDateAndEndDateAndType(
                co.getPropertyId(),
                        checkinDate, checkoutDate, PropertyStatus.MAINTENANCE.name())
                .ifPresent(x -> {throw new RuntimeException("This apartment is maintenance at date: " + x.getStartDate() + " to " + x.getEndDate());});
        propertyMaintenanceRepository.findByPropertyIdAndStartDateAndEndDateAndType(
                        co.getPropertyId(),
                        checkinDate, checkinDate, PropertyStatus.MAINTENANCE.name())
                .ifPresent(x -> {throw new RuntimeException("This apartment is maintenance at date: " + x.getStartDate() + " to " + x.getEndDate());});


        resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(
                        co.getProperty().getResortId(), checkinDate, checkinDate, ResortStatus.MAINTENANCE.name())
                .ifPresent(x -> {throw new RuntimeException("This apartment is maintenance at date: " + x.getStartDate() + " to " + x.getEndDate());});
        resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(
                        co.getProperty().getResortId(), checkinDate, checkoutDate, ResortStatus.MAINTENANCE.name())
                .ifPresent(x -> {throw new RuntimeException("This apartment is maintenance at date: " + x.getStartDate() + " to " + x.getEndDate());});

        var checkCoOwner = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentIdAndStartDateAndEndDateAndType(
                co.getPropertyId(), checkinDate,
                checkoutDate , co.getRoomId(),
                CoOwnerMaintenanceStatus.MAINTENANCE.name());
        if(checkCoOwner.size() >0) throw new RuntimeException("This apartment is maintenance at date : " + checkCoOwner.get(0).getStartDate() + " to "+ checkCoOwner.get(0).getEndDate());
        checkCoOwner = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentIdAndStartDateAndEndDateAndType(
                co.getPropertyId(), checkinDate,
                checkinDate , co.getRoomId(),
                CoOwnerMaintenanceStatus.MAINTENANCE.name());
        if(checkCoOwner.size() >0) throw new RuntimeException("This apartment is maintenance at date : " + checkCoOwner.get(0).getStartDate() + " to "+ checkCoOwner.get(0).getEndDate());

       var checkValidDeactiveProperty = propertyMaintenanceRepository.findPropertyMaintenanceByStartDateAndEndDateAndPropertyIdAndType(
                 checkinDate, checkoutDate,co.getPropertyId(), PropertyStatus.DEACTIVATE.name());
       if(checkValidDeactiveProperty != null) throw new RuntimeException("This apartment is deactive at date : " + checkValidDeactiveProperty.getStartDate() );

       var checkValidDeactiveResort = resortMaintanceRepository.findResortMaintanceByStartDateAndEndDateAndResortIdAndType(
               checkinDate, checkoutDate,co.getProperty().getResortId(), ResortStatus.DEACTIVATE.name());
       if(checkValidDeactiveResort != null) throw new RuntimeException("This apartment is deactive at date : " + checkValidDeactiveResort.getStartDate() );

         var checkValidDeactiveApartment = coOwnerMaintenanceRepository.findCoOwnerMaintenanceByStartDateAndEndDateAndPropertyIdAndApartmentIdAndType(
                checkinDate, checkoutDate,co.getPropertyId(), co.getRoomId(), CoOwnerMaintenanceStatus.DEACTIVATE.name());
        if(checkValidDeactiveApartment != null) throw new RuntimeException("This apartment is deactive at date : " + checkValidDeactiveApartment.getStartDate() );

    }


}

