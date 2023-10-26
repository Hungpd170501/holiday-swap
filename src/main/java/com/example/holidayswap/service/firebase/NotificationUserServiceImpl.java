package com.example.holidayswap.service.firebase;

import com.example.holidayswap.domain.dto.response.firebase.NotificationMessage;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.notification.NotificationUser;
import com.example.holidayswap.repository.firebaseNotification.NotificationUserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationUserServiceImpl implements INotificationUserService{
    private final NotificationUserRepository notificationUserRepository;

    private final IFirebaseMessagingService firebaseMessagingService;

    private final INotificationFirebaseService notificationFirebaseService;

    @Override
    public List<NotificationUser> GetAllNotificationByUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        var listNoti = notificationUserRepository.findAllByUserIdOrderByIdDesc(user.getUserId());
        return listNoti;
    }

    @Override
    @Transactional
    public void CreateNotificationByUserId(Long userId,String title,String content, String link) throws FirebaseMessagingException {

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId(userId);
        notificationUser.setContent(content);
        notificationUser.setLink(link);
        notificationUser.setIsRead(false);
        notificationUserRepository.save(notificationUser);
//        List<String> listToken = new ArrayList<>();

        //get Token FCM of userLogin
        var listTokenFCM = notificationFirebaseService.GetAllNotificationFirebaseByUserId(userId);
        for (var tokenFCM : listTokenFCM) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setTitle(title);
            notificationMessage.setBody(content);
            notificationMessage.setImage("123");
            Map<String,String> data = new HashMap<>();
            data.put("link",link);
            notificationMessage.setData(data);
            String token = tokenFCM.getFcmToken();
            String desiredPart = token.substring(1, token.length() - 1);
            notificationMessage.setRecipientToken(desiredPart);
            firebaseMessagingService.sendNotificationByToken(notificationMessage);

//            listToken.add(desiredPart);
        }
//        NotificationMessage notificationMessage = new NotificationMessage();
//        notificationMessage.setTitle(title);
//        notificationMessage.setBody(content);
//        notificationMessage.setRecipientToken(listToken);
//        notificationMessage.setImage("123");
//        Map<String,String> data = new HashMap<>();
//        data.put("link",link);
//        notificationMessage.setData(data);
//        firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

    @Override
    public void IsReadNotificationByUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        var listNoti = notificationUserRepository.findAllByUserIdAndIsRead(user.getUserId(), false);
        for (NotificationUser noti : listNoti) {
            noti.setIsRead(true);
            notificationUserRepository.save(noti);
        }
    }

    @Override
    public int CountNotificationNotReadByUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        var listNoti = notificationUserRepository.findAllByUserIdAndIsRead(user.getUserId(), false);
        return listNoti.size();
    }
}
