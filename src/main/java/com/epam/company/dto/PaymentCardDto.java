package com.epam.company.dto;

import com.epam.company.model.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentCardDto {
    private UUID id;
    private String cardNumber;
    private int expMonth;
    private int expYear;
    private String name;
    private String nickName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date added;
    private CardStatus status;
    private Long balance;
    private String currency;
}