package de.telran.businesstracker.service;

import de.telran.businesstracker.data.CustomerData;
import de.telran.businesstracker.repositories.CustomerDataRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerDataService {
    private final String CUSTOMER_ALREADY_EXIST = "Customer is already exist";

    private final CustomerDataRepository customerDataRepository;

    public CustomerDataService(CustomerDataRepository customerDataRepository) {
        this.customerDataRepository = customerDataRepository;
    }

    public String add(String email, String lastName, String linkedInUrl,
                      String phoneNumber, String firstName,
                      List<String> startupPos, List<String> interest, List<String> businessGoals,
                      int engLvl, String videoUrl1, String videoUrl2) {
        String formId = UUID.randomUUID().toString();
        customerDataRepository.save(new CustomerData(
                formId, email, lastName, linkedInUrl,
                phoneNumber, firstName, startupPos,
                interest, businessGoals, engLvl,
                videoUrl1, videoUrl2
        ));
        return formId;
    }

    public void update(String formId, String email, String lastName, String linkedInUrl,
                       String phoneNumber, String firstName,
                       List<String> startupPos, List<String> interest, List<String> businessGoals,
                       int engLvl, String videoUrl1, String videoUrl2) {

        CustomerData dateToUpdate = customerDataRepository.findById(formId)
                .orElseThrow(() -> new EntityExistsException(CUSTOMER_ALREADY_EXIST));

        dateToUpdate.setEmail(email);
        dateToUpdate.setLastName(lastName);
        dateToUpdate.setLinkedInUrl(linkedInUrl);
        dateToUpdate.setPhoneNumber(phoneNumber);
        dateToUpdate.setFirstName(firstName);

        dateToUpdate.setBusinessGoals(businessGoals);
        dateToUpdate.setInterest(interest);
        dateToUpdate.setStartupPos(startupPos);
        dateToUpdate.setEngLvl(engLvl);

        dateToUpdate.setVideoUrl1(videoUrl1);
        dateToUpdate.setVideoUrl2(videoUrl2);

        customerDataRepository.save(dateToUpdate);
    }
}
