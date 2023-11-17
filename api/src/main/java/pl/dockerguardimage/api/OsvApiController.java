package pl.dockerguardimage.api;

import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dockerguardimage.api.dto.mapper.OsvApiMapperController;
import pl.dockerguardimage.api.dto.request.SystemRequest;
import pl.dockerguardimage.api.dto.response.SystemResponse;
import pl.dockerguardimage.core.osv.OsvApiService;
import pl.dockerguardimage.core.osv.dto.request.SystemRequestService;
import pl.dockerguardimage.core.osv.dto.response.SystemResponseService;

import java.io.IOException;
import java.util.Set;

@RestController()
public class OsvApiController {

    OsvApiService osvApiService;

    public OsvApiController(OsvApiService osvApiService) {
        this.osvApiService = osvApiService;
    }

    @PostMapping("/package/vulnerability")
    public ResponseEntity<SystemResponse> findPackageVulnerability(@Valid @RequestBody SystemRequest systemRequest) throws IOException, InterruptedException {

        System.out.println(systemRequest);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SystemRequest>> violations = validator.validate(systemRequest);

        System.out.println(violations);



        SystemRequestService requestService = OsvApiMapperController.mapSystemRequestToServiceRequest(systemRequest);
        SystemResponseService responseService = osvApiService.findVulnerabilitiesFromOsvApi(requestService);
        SystemResponse response= OsvApiMapperController.mapServiceResponseToSystemResponse(responseService);
        return ResponseEntity.ok(response);
    }
}
