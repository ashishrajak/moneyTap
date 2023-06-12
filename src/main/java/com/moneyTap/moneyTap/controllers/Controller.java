package com.moneyTap.moneyTap.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moneyTap.moneyTap.models.Lead;
import com.moneyTap.moneyTap.models.UserCredentials;
import com.moneyTap.moneyTap.services.ServiceMoneyTap;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
   private ServiceMoneyTap serviceMoneyTap;
    private ObjectMapper objectMapper;
   @Autowired
   public  void setServiceMoneyTap(ServiceMoneyTap serviceMoneyTap){
       this.serviceMoneyTap=serviceMoneyTap;
   }
    @Autowired
    private void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    @PostMapping("/access-token")
    public ObjectNode accessToken(@RequestBody @Valid UserCredentials userCredentials){

        return serviceMoneyTap.tokenAccess(userCredentials);
    }
    @PostMapping("/lead-creation")
    public ObjectNode leadCreation(@RequestBody Lead lead) throws JsonProcessingException {
       return serviceMoneyTap.leadCreation(lead);
    }


    @PostMapping("/user-status")
    public ObjectNode getCustomerDetails(@RequestParam String userID) {
        try {
            ObjectNode response = serviceMoneyTap.getCustomerDetails(userID);
            return response;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            // Return an error response
            ObjectNode errorResponse = objectMapper.createObjectNode();
            errorResponse.put("error", "Failed to retrieve user status");
            return errorResponse;
        }
    }

}
