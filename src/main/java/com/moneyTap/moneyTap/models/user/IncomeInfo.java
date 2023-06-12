package com.moneyTap.moneyTap.models.user;

import lombok.Data;

import java.util.List;

@Data
public class IncomeInfo {
    private int declared;
    private String mode;
    private List<String> bankIfscPrefixes;


}