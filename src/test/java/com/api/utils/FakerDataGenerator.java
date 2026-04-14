package com.api.utils;

import com.api.constant.*;
import com.api.request.model.*;
import net.datafaker.Faker;

import java.util.*;

public class FakerDataGenerator {
    private FakerDataGenerator() {}
    private static final String COUNTRY = "USA";
    private static final Faker FAKER = new Faker(new Locale("en"));
    private static final Random RANDOM = new Random();
    private static final int VALID_PROBLEM_IDS[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 19, 20, 22, 24, 26, 27, 28, 29};



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
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        String mobileNumber = FAKER.numerify("09########");
        String alternateMobileNumber = FAKER.numerify("09########");
        String emailAddress = FAKER.internet().emailAddress();
        String altEmailAddress = FAKER.internet().emailAddress();

        return new Customer(firstName, lastName, mobileNumber, alternateMobileNumber, emailAddress, altEmailAddress);
    }

    private static CustomerAddress generateFakeCustomerAddress() {
        String flatNumber = FAKER.numerify("###");
        String apartmentNumber = FAKER.address().streetName();
        String streetName = FAKER.address().streetName();
        String landMark = FAKER.address().streetName();
        String area = FAKER.address().streetName();
        String pinCode = FAKER.numerify("#####");
        String state = FAKER.address().state();

        return new CustomerAddress(flatNumber, apartmentNumber, streetName, landMark, area, pinCode, COUNTRY, state);
    }

    private static CustomerProduct generateFakeCustomerProduct() {
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = FAKER.numerify("###############");
        String popurl = FAKER.internet().url();

        return new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
                popurl, Product.NEXUS_2.getId(), Model.NEXUS_2_BLUE.getId());
    }

    private static List<Problems> generateFakeProblems() {
        List<Problems> problemsList = new ArrayList<>();
        int maxProblems = RANDOM.nextInt(3) + 1;

        for (int i = 0; i < maxProblems; i++) {
            int problemId = VALID_PROBLEM_IDS[RANDOM.nextInt(VALID_PROBLEM_IDS.length)];
            String remark = FAKER.lorem().sentence();
            Problems problems = new Problems(problemId, remark);
            problemsList.add(problems);
        }
        return problemsList;
    }
}
