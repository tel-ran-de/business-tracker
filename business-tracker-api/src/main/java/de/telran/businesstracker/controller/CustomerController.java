package de.telran.businesstracker.controller;

import de.telran.businesstracker.dto.CustomerDataDto;
import de.telran.businesstracker.dto.CustomerDataIdDto;
import de.telran.businesstracker.service.CustomerDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerDataService customerDataService;

    public CustomerController(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }

    @PostMapping("")
    public CustomerDataIdDto create(@RequestBody CustomerDataDto customerDataDto) {
        String id = customerDataService.add(
                customerDataDto.email,
                customerDataDto.lastName,
                customerDataDto.linkedInUrl,
                customerDataDto.phoneNumber,
                customerDataDto.firstName,

                customerDataDto.startupPos,
                customerDataDto.interest,
                customerDataDto.businessGoals,
                customerDataDto.engLvl,

                customerDataDto.videoUrl1,
                customerDataDto.videoUrl2);
        CustomerDataIdDto idDto = new CustomerDataIdDto();
        idDto.formId = id;
        return idDto;
    }

    @PutMapping("")
    public void update(@RequestBody CustomerDataDto customerDataDto) {
        customerDataService.update(
                customerDataDto.formId,

                customerDataDto.email,
                customerDataDto.lastName,
                customerDataDto.linkedInUrl,
                customerDataDto.phoneNumber,
                customerDataDto.firstName,

                customerDataDto.startupPos,
                customerDataDto.interest,
                customerDataDto.businessGoals,
                customerDataDto.engLvl,

                customerDataDto.videoUrl1,
                customerDataDto.videoUrl2);
    }
}
