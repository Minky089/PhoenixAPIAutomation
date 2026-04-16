package com.api.tests.datadriven;

import com.api.constant.Roles;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.db.model.CustomerAddressDBModel;
import com.db.model.CustomerDBModel;
import com.db.model.CustomerProductDBModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.getRequestSpecWithAuth;
import static com.api.utils.SpecUtil.getResponseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.testng.Assert.assertEquals;

public class CreateJobAPIDataDrivenTest {
    //    @Test(description = "Verify if create job API is able to create Inwarranty job",
    //          groups = {"api", "regression", "datadriven", "CSV"},
    //          dataProviderClass = com.dataproviders.DataProvidersUtils.class,
    //          dataProvider = "CreateJobAPIDataProvider"
    //    )
    //Temporary comment out because the imei in this CSV files need to be updated manually
    public void createJobAPICSVDataDrivenTest(CreateJobPayload createJobPayload) {
        given().spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));
    }

    //    @Test(description = "Verify if create job API is able to create Inwarranty job",
    //             groups = {"api", "regression", "datadriven", "JSON"},
    //             dataProviderClass = com.dataproviders.DataProvidersUtils.class,
    //             dataProvider = "CreateJobAPIJSONDataProvider"
    //       )
    //Temporary comment out because the imei in this JSON files need to be updated manually
    public void createJobAPIJSONDataDrivenTest(CreateJobPayload createJobPayload) {
        given().spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));
    }

    @Test(description = "Verify if create job API is able to create with fake data generator",
            groups = {"api", "regression", "datadriven"},
            dataProviderClass = com.dataproviders.DataProvidersUtils.class,
            dataProvider = "CreateJobAPIFakerDataProvider")
    public void createJobAPITestWithFakeData(CreateJobPayload createJobPayload) {
        int customerId =
        given().spec(getRequestSpecWithAuth(Roles.FD, createJobPayload))
                .when()
                .post("job/create")
                .then()
                .spec(getResponseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/createJobResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"))
                .extract().body().jsonPath().getInt("data.tr_customer_id");

        Customer expectedCustomer = createJobPayload.customer();
        CustomerDBModel actualCustomer = CustomerDao.getCustomerInfo(customerId);
        assertEquals(expectedCustomer.first_name(),  actualCustomer.first_name());
        assertEquals(expectedCustomer.last_name(),  actualCustomer.last_name());
        assertEquals(expectedCustomer.mobile_number(),  actualCustomer.mobile_number());
        assertEquals(expectedCustomer.mobile_number_alt(),  actualCustomer.mobile_number_alt());
        assertEquals(expectedCustomer.email_id(),  actualCustomer.email_id());
        assertEquals(expectedCustomer.email_id_alt(),  actualCustomer.email_id_alt());

        CustomerProduct expectedCustomerProduct = createJobPayload.customer_product();
        CustomerProductDBModel actualCustomerProduct = CustomerProductDao.getCustomerProductInfo(customerId);
        assertEquals(expectedCustomerProduct.dop(),  actualCustomerProduct.dop());
        assertEquals(expectedCustomerProduct.serial_number(),  actualCustomerProduct.serial_number());
        assertEquals(expectedCustomerProduct.imei1(),  actualCustomerProduct.imei1());
        assertEquals(expectedCustomerProduct.imei2(),  actualCustomerProduct.imei2());
        assertEquals(expectedCustomerProduct.popurl(),  actualCustomerProduct.popurl());
        assertEquals(expectedCustomerProduct.product_id(),  actualCustomerProduct.product_id());
        assertEquals(expectedCustomerProduct.mst_model_id(),  actualCustomerProduct.mst_model_id());

        CustomerAddress expectedCustomerAddress = createJobPayload.customer_address();
        CustomerAddressDBModel actualCustomerAddress = CustomerAddressDao.getCustomerAddressInfo(actualCustomer.tr_customer_address_id());
        assertEquals(expectedCustomerAddress.flat_number(),  actualCustomerAddress.flat_number());
        assertEquals(expectedCustomerAddress.apartment_name(),  actualCustomerAddress.apartment_name());
        assertEquals(expectedCustomerAddress.street_name(),  actualCustomerAddress.street_name());
        assertEquals(expectedCustomerAddress.landmark(),  actualCustomerAddress.landmark());
        assertEquals(expectedCustomerAddress.area(),  actualCustomerAddress.area());
        assertEquals(expectedCustomerAddress.pincode(),  actualCustomerAddress.pincode());
        assertEquals(expectedCustomerAddress.country(),  actualCustomerAddress.country());
        assertEquals(expectedCustomerAddress.state(),  actualCustomerAddress.state());
    }
}