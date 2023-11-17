package pl.dockerguardimage.core.osv;

import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.osv.dto.mapper.OsvApiMapperService;
import pl.dockerguardimage.core.osv.dto.request.OsvApiRequest;
import pl.dockerguardimage.core.osv.dto.request.SystemRequestService;
import pl.dockerguardimage.core.osv.dto.response.OsvApiResponse;
import pl.dockerguardimage.core.osv.dto.response.SystemResponseService;

import java.io.IOException;

@Service
public class OsvApiService {

    OsvApiClient osvApiClient;

    public OsvApiService(OsvApiClient osvApiClient) {
        this.osvApiClient = osvApiClient;
    }

    public SystemResponseService findVulnerabilitiesFromOsvApi(SystemRequestService systemRequest) throws IOException, InterruptedException {
        OsvApiRequest osvApiRequest = OsvApiMapperService.mapSystemToOsvApiRequest(systemRequest);
        OsvApiResponse osvApiResponse = osvApiClient.getOsvVulnerabilityResponse(osvApiRequest);
        return OsvApiMapperService.mapOsvApiToSystemResponse(osvApiResponse, systemRequest);
    }
}
