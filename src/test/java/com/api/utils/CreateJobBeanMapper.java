package com.api.utils;

import com.api.request.model.*;
import com.dataproviders.api.bean.CreateJobBean;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class CreateJobBeanMapper {
    private CreateJobBeanMapper() {
    }

    @Step("Converting CreateJobBean to CreateJobPayload")
    public static CreateJobPayload mapper(CreateJobBean bean) {
        log.info("Converting Create Job Bean {} to Create Job Payload...", bean);
        int mstServiceLocationId = Integer.parseInt(bean.getMst_service_location_id());
        int mstPlatformId = Integer.parseInt(bean.getMst_platform_id());
        int oemId = Integer.parseInt(bean.getMst_oem_id());
        int mstWarrentyStatusId = Integer.parseInt(bean.getMst_warrenty_status_id());

        Customer customer = new Customer(bean.getCustomer__first_name(),
                bean.getCustomer__last_name(),
                bean.getCustomer__mobile_number(),
                bean.getCustomer__mobile_number_alt(),
                bean.getCustomer__email_id(),
                bean.getCustomer__email_id_alt());

        CustomerAddress customerAddress = new CustomerAddress(bean.getCustomer_address__flat_number(),
                bean.getCustomer_address__apartment_name(),
                bean.getCustomer_address__street_name(),
                bean.getCustomer_address__landmark(),
                bean.getCustomer_address__area(),
                bean.getCustomer_address__pincode(),
                bean.getCustomer_address__country(),
                bean.getCustomer_address__state());

        CustomerProduct customerProduct = new CustomerProduct(bean.getCustomer_product__dop(),
                bean.getCustomer_product__serial_number(),
                bean.getCustomer_product__imei1(),
                bean.getCustomer_product__imei2(),
                bean.getCustomer_product__popurl(),
                Integer.parseInt(bean.getCustomer_product__product_id()),
                Integer.parseInt(bean.getCustomer_product__mst_model_id()));

        int problemlId = Integer.parseInt(bean.getProblems_id());
        List<Problems> problemsList = List.of(new Problems(problemlId, bean.getProblems_remark()));

        CreateJobPayload createJobPayload = new CreateJobPayload(mstServiceLocationId, mstPlatformId, oemId,
                mstWarrentyStatusId,
                customer,
                customerAddress,
                customerProduct,
                problemsList);
        log.info("Converting the bean {} to Create Job Payload", createJobPayload);

        return createJobPayload;
    }
}
