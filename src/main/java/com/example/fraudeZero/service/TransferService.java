package com.example.fraudeZero.service;

import com.example.fraudeZero.models.*;
import com.example.fraudeZero.repository.AddressRepository;
import com.example.fraudeZero.repository.BankAccountRepository;
import com.example.fraudeZero.repository.TransferRepository;
import com.example.fraudeZero.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class TransferService {

    private final Adress[] VALID_ADRESSES = {Adress.CASA, Adress.TRABALHO, Adress.FACULDADE};

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender mailSender;

    private static final String BASE_URL = "http://localhost:8080/transfer";
    private static final int VALIDATION_WAIT_MINUTES = 3;

    public List<Transfer> listAllTransfer(){
        return transferRepository.findAll();
    }

    public List listTransferByAccount(String account){

        JSONObject json = new JSONObject(account);
        String pixKey = json.getString("pixKey");

        List<Transfer> transfers = transferRepository.findByOrigimAccount(pixKey);

        if(transfers.isEmpty()){
            throw new RuntimeException("No transfers found");
        }

        return transfers;
    }

    private final Map<UUID, CompletableFuture<Object>> pendingValidations = new ConcurrentHashMap<>();

    public boolean validateBalance(BigDecimal balance, BigDecimal value) {

        if (balance.compareTo(value) < 0) {
            return true;
        }

        return false;
    }

    public boolean validationTimeIntermediary() {

        LocalTime now = LocalTime.now();
        LocalTime intermediary = LocalTime.of(20, 0);

        if (now.isAfter(intermediary)) {
            return true;
        }
        return false;

    }

    public boolean validationTimeLimit() {

        LocalTime now = LocalTime.now();
        LocalTime limit = LocalTime.of(23, 0);
        LocalTime interval = LocalTime.of(8, 00);

        if ((now.isAfter(limit)) || (now.isBefore(interval))) {
            return true;
        }
        return false;
    }


    public boolean validateLocation(Adress adress) {
        if (adress == null) {
            throw new RuntimeException("Location is null");
        }

        for (Adress validAdress : VALID_ADRESSES) {
            if (adress == validAdress) {
                return true;
            }
        }

        return false;
    }

    private void sendValidationEmail(String to, UUID validationToken, String origimAccount, String destinationAccount, BigDecimal value, Adress location, String description) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            String validationLink = BASE_URL + "/validateEmail?token=" + validationToken +
                    "&origimAccount=" + java.net.URLEncoder.encode(origimAccount, "UTF-8") +
                    "&destinationAccount=" + java.net.URLEncoder.encode(destinationAccount, "UTF-8") +
                    "&value=" + value +
                    "&location=" + java.net.URLEncoder.encode(location.toString(), "UTF-8") +
                    "&description=" + java.net.URLEncoder.encode(description, "UTF-8");
            String htmlContent = "<html><body>" +
                    "<h2>Transaction Validation</h2>" +
                    "<p>Please click the button below to validate and complete your transaction within 3 minutes:</p>" +
                    "<a href='" + validationLink + "' style='padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;'>Validate and Complete Transaction</a>" +
                    "</body></html>";
            helper.setTo(to);
            helper.setSubject("Transaction Validation Required");
            helper.setText(htmlContent, true);
            helper.setFrom("no-reply@yourdomain.com");

            mailSender.send(message);
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send validation email: " + e.getMessage());
        }
    }

    @Transactional
    public void validateEmail(String tokenStr, String origimAccount, String destinationAccount, BigDecimal value, Adress location, String description) {
        var bank = bankAccountRepository.findByPixKey(origimAccount)
                .orElseThrow(() -> new RuntimeException("account not found"));

        var user = bank.getUser();
        System.out.println("Validating email with token string: " + tokenStr);
        UUID validationToken = UUID.fromString(tokenStr); // Converte a string da URL para UUID
        user.setValidationToken(validationToken);
        userRepository.save(user);
        CompletableFuture<Object> future = pendingValidations.get(validationToken);
        if (future != null && !future.isDone()) {
            userRepository.findByValidationToken(validationToken)
                    .orElseThrow(() -> new RuntimeException("Invalid validation token"));
            System.out.println("Found user with token: " + user.getEmail() + ", Token in DB: " + user.getValidationToken());

            if (!user.isEmailValidated()) {
                user.setEmailValidated(true);
                user.setValidationToken(null);
                userRepository.save(user);

                BankAccount newAccount = bankAccountRepository.findByPixKey(origimAccount).get();
                BankAccount newDestination = bankAccountRepository.findByPixKey(destinationAccount).get();
                BigDecimal extract = newAccount.getBankBalance().subtract(value);
                newAccount.setBankBalance(extract);
                bankAccountRepository.save(newAccount);
                BigDecimal deposit = newDestination.getBankBalance().add(value);
                newDestination.setBankBalance(deposit);
                bankAccountRepository.save(newDestination);

                Transfer transfer = new Transfer();
                transfer.setOrigimAccount(newAccount.getPixKey());
                transfer.setTargetAccount(newDestination.getPixKey());
                transfer.setValue(value);
                transfer.setDescriptionTransfer(description);
                transfer.setDateTime(LocalDateTime.now());
                transferRepository.save(transfer);

                future.complete(transfer);
                pendingValidations.remove(validationToken);
                System.out.println("Validation completed for token: " + validationToken);
            } else {
                System.out.println("Email already validated for token: " + validationToken);
            }
        } else {
            System.out.println("No pending validation or future already done for token: " + validationToken + ", Pending validations: " + pendingValidations.keySet());
            throw new IllegalStateException("No pending validation or already completed for token: " + validationToken);
        }
    }

    @Transactional
    public Object sendTransfer(String origimAccount, String destinationAccount, BigDecimal value, Adress location, String description) {
        Optional<BankAccount> origin = bankAccountRepository.findByPixKey(origimAccount);

        if (origin.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Optional<BankAccount> destination = bankAccountRepository.findByPixKey(destinationAccount);

        if (destination.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        if (validateBalance(origin.get().getBankBalance(), value)) {
            throw new RuntimeException("insufficient balance");
        } else if (validationTimeIntermediary()) {
            BigDecimal limitValue = BigDecimal.valueOf(800.0);

            if (value.compareTo(limitValue) > 0) {
                throw new RuntimeException("It is not possible to make transfers larger than 800 reais after 20:00");
            }
            User user = origin.get().getUser();
            if (!user.isEmailValidated()) {
                UUID validationToken = UUID.randomUUID(); // Gera UUID diretamente
                System.out.println("Generated validation token: " + validationToken + " for user: " + user.getEmail() + ", Before save: " + user.getValidationToken());
                user.setValidationToken(validationToken);
                User savedUser = userRepository.save(user); // Salva e verifica
                System.out.println("After save - User ID: " + savedUser.getIdUser() + ", Token: " + savedUser.getValidationToken() + ", Email: " + savedUser.getEmail());

                CompletableFuture<Object> future = new CompletableFuture<>();
                pendingValidations.put(validationToken, future);

                sendValidationEmail(user.getEmail(), validationToken, origimAccount, destinationAccount, value, location, description);

                try {
                    return future.get(VALIDATION_WAIT_MINUTES, TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    pendingValidations.remove(validationToken);
                    System.out.println("Validation timed out for token: " + validationToken + ", Error: " + e.getMessage());
                    throw new RuntimeException("Transaction failed: Validation not completed within 3 minutes. Please try again: " + e.getMessage());
                }
            }

            BankAccount newAccount = origin.get();
            BigDecimal extract = newAccount.getBankBalance().subtract(value);
            newAccount.setBankBalance(extract);
            bankAccountRepository.save(newAccount);

            BankAccount newDestination = destination.get();
            BigDecimal deposit = newDestination.getBankBalance().add(value);
            newDestination.setBankBalance(deposit);
            bankAccountRepository.save(newDestination);

            Transfer transfer = new Transfer();
            transfer.setOrigimAccount(newAccount.getPixKey());
            transfer.setTargetAccount(newDestination.getPixKey());
            transfer.setValue(value);
            transfer.setDescriptionTransfer(description);
            transfer.setDateTime(LocalDateTime.now());

            return transferRepository.save(transfer);
        } else if (validationTimeLimit()) {
            throw new RuntimeException("It is not possible to make transfers after 23:00");
        } else if (!validateLocation(location)) {
            throw new RuntimeException("Invalid location for transfer");
        }

        User user = origin.get().getUser();
        if (!user.isEmailValidated()) {
            UUID validationToken = UUID.randomUUID(); // Gera UUID diretamente
            System.out.println("Generated validation token: " + validationToken + " for user: " + user.getEmail() + ", Before save: " + user.getValidationToken());
            user.setValidationToken(validationToken);
            User savedUser = userRepository.save(user); // Salva e verifica
            System.out.println("After save - User ID: " + savedUser.getIdUser() + ", Token: " + savedUser.getValidationToken() + ", Email: " + savedUser.getEmail());

            CompletableFuture<Object> future = new CompletableFuture<>();
            pendingValidations.put(validationToken, future);

            sendValidationEmail(user.getEmail(), validationToken, origimAccount, destinationAccount, value, location, description);

            try {
                return future.get(VALIDATION_WAIT_MINUTES, TimeUnit.MINUTES);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                pendingValidations.remove(validationToken);
                System.out.println("Validation timed out for token: " + validationToken + ", Error: " + e.getMessage());
                throw new RuntimeException("Transaction failed: Validation not completed within 3 minutes. Please try again: " + e.getMessage());
            }
        }

        BankAccount newAccount = origin.get();
        BigDecimal extract = newAccount.getBankBalance().subtract(value);
        newAccount.setBankBalance(extract);
        bankAccountRepository.save(newAccount);

        BankAccount newDestination = destination.get();
        BigDecimal deposit = newDestination.getBankBalance().add(value);
        newDestination.setBankBalance(deposit);
        bankAccountRepository.save(newDestination);

        Transfer transfer = new Transfer();
        transfer.setOrigimAccount(newAccount.getPixKey());
        transfer.setTargetAccount(newDestination.getPixKey());
        transfer.setValue(value);
        transfer.setDescriptionTransfer(description);
        transfer.setDateTime(LocalDateTime.now());

        return transferRepository.save(transfer);
    }
}