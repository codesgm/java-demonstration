package com.demonstration.demo.resource;

import com.demonstration.demo.dto.account.*;
import com.demonstration.demo.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;


    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        boolean success = accountService.transfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );

        TransferResponse response = new TransferResponse(
                success,
                success ? "Transferência realizada com sucesso" : "Saldo insuficiente",
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );

        return success ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
}
