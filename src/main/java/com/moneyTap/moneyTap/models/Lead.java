
package com.moneyTap.moneyTap.models;

import com.moneyTap.moneyTap.models.user.Address;
import com.moneyTap.moneyTap.models.user.IncomeInfo;
import lombok.Data;

@Data
public class Lead {
    private String name;
    private String phone;
    private String emailId;
    private String panNumber;
    private String dateOfBirth;
    private IncomeInfo incomeInfo;
    private String householdIncome;
    private String companyType;
    private String jobType;
    private String gender;
    private String residenceType;
    private int totalWorkExperienceInMonths;
    private int currentWorkExperienceInMonths;
    private int currentCityDurationInMonths;
    private int currentHomeAddressDurationInMonths;
    private String maritalStatus;
    private String officeEmail;
    private String companyName;
    private String employmentType;
    private Address homeAddress;
    private Address officeAddress;


}
