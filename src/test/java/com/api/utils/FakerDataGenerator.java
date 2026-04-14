package com.api.utils;

import com.api.constant.*;
import com.api.request.model.*;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FakerDataGenerator {
    private FakerDataGenerator() {}
    private static final String COUNTRY = "USA";
    private static final Faker faker = new Faker();
    private static final int validProblemIds[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 19, 20, 22, 24, 26, 27, 28, 29};



    public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
        List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

        for (int i = 0; i < count; i++) {
            CreateJobPayload createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getId(), Platform.FRONT_DESK.getId(),
                    WarrantyStatus.IN_WARRANTY.getId(), OEM.GOOGLE.getId(),
                    generateFakeCustomerData(), generateFakeCustomerAddress(),
                    generateFakeCustomerProduct(), generateFakeProblems());
            payloadList.add(createJobPayload);
        }
        return payloadList.iterator();
    }

    private static Customer generateFakeCustomerData() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String mobileNumber = faker.numerify("09########");
        String alternateMobileNumber = faker.numerify("09########");
        String emailAddress = faker.internet().emailAddress();
        String altEmailAddress = faker.internet().emailAddress();

        return new Customer(firstName, lastName, mobileNumber, alternateMobileNumber, emailAddress, altEmailAddress);
    }

    private static CustomerAddress generateFakeCustomerAddress() {
        String flatNumber = faker.numerify("###");
        String apartmentNumber = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landMark = faker.address().streetName();
        String area = faker.address().streetName();
        String pinCode = faker.numerify("#####");
        String state = faker.address().state();

        return new CustomerAddress(flatNumber, apartmentNumber, streetName, landMark, area, pinCode, COUNTRY, state);
    }

    private static CustomerProduct generateFakeCustomerProduct() {
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popurl = faker.internet().url();

        return new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
                popurl, Product.NEXUS_2.getId(), Model.NEXUS_2_BLUE.getId());
    }

    private static ArrayList<Problems> generateFakeProblems() {
        int problemId = validProblemIds[new Random().nextInt(validProblemIds.length)];
        String remark = faker.lorem().sentence();
        Problems problems = new Problems(problemId, remark);

        return new ArrayList<>(List.of(problems));
    }
}
