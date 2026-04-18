package com.api.tests.datadriven;

import com.api.constant.Roles;
import com.api.request.model.*;
import com.api.response.model.CreateJobResponse;
import com.api.services.JobService;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.UtilDao;
import com.db.model.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.testng.Assert.assertEquals;

public class CreateJobAPIPojoResponseTest {

    @Test(description = "Verify if create job API is able to create with fake data generator, " +
            "asserting between Response data and database",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "CreateJobAPIFakerDataProvider")
    public void createJobAPIPojoResponse(CreateJobPayload createJobPayload) {
        JobService jobService = new JobService();
        SoftAssert softAssert = new SoftAssert();
        CreateJobResponse createJobResponse =
                jobService.createJob(Roles.FD, createJobPayload)
                        .then()
                        .spec(getResponseSpec_OK())
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                        .body("message", equalTo("Job created successfully. "))
                        .body("data.mst_service_location_id", equalTo(1))
                        .body("data.job_number", startsWith("JOB_"))
                        .extract().as(CreateJobResponse.class);

        int customerId = createJobResponse.data().tr_customer_id();
        Customer expectedCustomer = createJobPayload.customer();
        CustomerDBModel actualCustomer = CustomerDao.getCustomerInfo(customerId);
        assertEquals(expectedCustomer.first_name(), actualCustomer.first_name(), "Verifying first_name is correct");
        assertEquals(expectedCustomer.last_name(), actualCustomer.last_name(), "Verifying last_name is correct");
        assertEquals(expectedCustomer.mobile_number(), actualCustomer.mobile_number(), "Verifying mobile_number is correct");
        assertEquals(expectedCustomer.mobile_number_alt(), actualCustomer.mobile_number_alt(), "Verifying mobile_number_alt is correct");
        assertEquals(expectedCustomer.email_id(), actualCustomer.email_id(), "Verifying email_id is correct");
        assertEquals(expectedCustomer.email_id_alt(), actualCustomer.email_id_alt(), "Verifying email_id_alt is correct");

        CustomerProduct expectedCustomerProduct = createJobPayload.customer_product();
        CustomerProductDBModel actualCustomerProduct = CustomerProductDao.getCustomerProductInfo(customerId);
        assertEquals(expectedCustomerProduct.dop(), actualCustomerProduct.dop(), "Verifying dop is correct");
        assertEquals(expectedCustomerProduct.serial_number(), actualCustomerProduct.serial_number(), "Verifying serial_number is correct");
        assertEquals(expectedCustomerProduct.imei1(), actualCustomerProduct.imei1(), "Verifying imei1 is correct");
        assertEquals(expectedCustomerProduct.imei2(), actualCustomerProduct.imei2(), "Verifying imei2 is correct");
        assertEquals(expectedCustomerProduct.popurl(), actualCustomerProduct.popurl(), "Verifying popurl is correct");
        assertEquals(expectedCustomerProduct.product_id(), actualCustomerProduct.product_id(), "Verifying product_id is correct");
        assertEquals(expectedCustomerProduct.mst_model_id(), actualCustomerProduct.mst_model_id(), "Verifying mst_model_id is correct");

        CustomerAddress expectedCustomerAddress = createJobPayload.customer_address();
        CustomerAddressDBModel actualCustomerAddress = CustomerAddressDao.getCustomerAddressInfo(actualCustomer.tr_customer_address_id());
        assertEquals(expectedCustomerAddress.flat_number(), actualCustomerAddress.flat_number(), "Verifying flat_number is correct");
        assertEquals(expectedCustomerAddress.apartment_name(), actualCustomerAddress.apartment_name(), "Verifying apartment_name is correct");
        assertEquals(expectedCustomerAddress.street_name(), actualCustomerAddress.street_name(), "Verifying street_name is correct");
        assertEquals(expectedCustomerAddress.landmark(), actualCustomerAddress.landmark(), "Verifying landmark is correct");
        assertEquals(expectedCustomerAddress.area(), actualCustomerAddress.area(), "Verifying area is correct");
        assertEquals(expectedCustomerAddress.pincode(), actualCustomerAddress.pincode(), "Verifying pincode is correct");
        assertEquals(expectedCustomerAddress.country(), actualCustomerAddress.country(), "Verifying country is correct");
        assertEquals(expectedCustomerAddress.state(), actualCustomerAddress.state(), "Verifying state is correct");

        JobHeadDBModel actualJobHead = UtilDao.getJobHeadInfo(customerId);
        assertEquals(createJobPayload.mst_service_location_id(), actualJobHead.mst_service_location_id(), "Verifying mst_service_location_id is correct");
        assertEquals(createJobPayload.mst_platform_id(), actualJobHead.mst_platform_id(), "Verifying mst_platform_id is correct");
        assertEquals(createJobPayload.mst_warrenty_status_id(), actualJobHead.mst_warrenty_status_id(), "Verifying mst_warrenty_status_id is correct");
        assertEquals(createJobPayload.mst_oem_id(), actualJobHead.mst_oem_id(), "Verifying mst_oem_id is correct");

        List<Problems> expectedJobProblem = createJobPayload.problems();
        List<JobProblemDBModel> actualJobProblem = UtilDao.getJobProblemInfo(actualJobHead.id());
        for (int i =  0; i < expectedJobProblem.size(); i++) {
            softAssert.assertEquals(expectedJobProblem.get(i).id(), actualJobProblem.get(i).mst_problem_id(), "Verifying problem id is correct");
            softAssert.assertEquals(expectedJobProblem.get(i).remark(), actualJobProblem.get(i).remark(), "Verifying problem remark is correct");
        }
    }
}