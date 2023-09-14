package com.example.holidayswap.controller.payment;

import com.example.holidayswap.config.BankingConfig;
import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.dto.response.payment.PaymentResDTO;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.service.payment.IMoneyTranferService;
import com.example.holidayswap.service.payment.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private ITransactionService transactionService;
    @Autowired
    private IMoneyTranferService moneyTranferService;
    @GetMapping("/Create_payment")
    public ResponseEntity<?> createPayment(@RequestParam String amount, @RequestParam String orderInfor ) throws UnsupportedEncodingException {

        Long total = Long.parseLong(amount)*100;
        String vnp_TxnRef = BankingConfig.getRandomNumber(8);
        String vnp_TmnCode = BankingConfig.vnp_TmnCode;
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        User user = (User) principal;
        //create history money transfer
        TopUpWalletDTO topUpWalletDTO = new TopUpWalletDTO();
        topUpWalletDTO.setAmount(Integer.parseInt(amount));
        topUpWalletDTO.setOrderInfor(orderInfor);
        topUpWalletDTO.setUserId(user.getUserId().toString());
        topUpWalletDTO.setPaymentDate(vnp_CreateDate);
        MoneyTranfer moneyTranfer = moneyTranferService.CreateMoneyTranferTransaction(topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer.WAITING);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", BankingConfig.vnp_Version);
        vnp_Params.put("vnp_Command", BankingConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", "120000");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/api/v1/payment/payment_infor/" + moneyTranfer.getId());
        vnp_Params.put("vnp_IpAddr", "vnp_IpAddr");
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = BankingConfig.hmacSHA512(BankingConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = BankingConfig.vnp_PayUrl + "?" + queryUrl;
        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setURL(paymentUrl);
        paymentResDTO.setMessage("success");
        paymentResDTO.setStatus("Ok");

        return ResponseEntity.ok(paymentResDTO);
    }

    @GetMapping("/payment_infor/{moneyTransferId}")
    public ResponseEntity<?> transaction(@PathVariable String moneyTransferId ,  @RequestParam(value = "vnp_ResponseCode") String responseCode){

        MoneyTranfer moneyTranfer = moneyTranferService.GetMoneyTranferTransaction(Long.parseLong(moneyTransferId));
        if(moneyTranfer == null) return ResponseEntity.badRequest().body("Transaction not found");
        if(!moneyTranfer.getStatus().name().equals(EnumPaymentStatus.StatusMoneyTranfer.WAITING.name())) return ResponseEntity.badRequest().body("Transaction has been completed");
        TopUpWalletDTO topUpWalletDTO = new TopUpWalletDTO();
        topUpWalletDTO.setAmount((int) moneyTranfer.getAmount());
        topUpWalletDTO.setBankCode(moneyTranfer.getBankCode());
        topUpWalletDTO.setOrderInfor(moneyTranfer.getOrderInfor());
        topUpWalletDTO.setPaymentDate(moneyTranfer.getPaymentDate());
        topUpWalletDTO.setUserId(moneyTranfer.getUser().getUserId().toString());

        var check = false;

        if(responseCode.equals("00")) {
           check = transactionService.TransactionTopUpWallet(topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer.SUCCESS,moneyTranfer.getId());
        }else {
            moneyTranferService.UpdateStatusMoneyTranferTransaction(moneyTranfer.getId(), EnumPaymentStatus.StatusMoneyTranfer.FAILED);
        }
        return check ? ResponseEntity.ok(topUpWalletDTO) : ResponseEntity.badRequest().body("Transfer fail");
    }
}
