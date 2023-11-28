package pl.dockerguardimage.core.functionality.cve;

import com.example.dockerguarddevelopment.osv.OsvApiClient;
import com.example.dockerguarddevelopment.osv.dto.mapper.OsvApiMapper;
import com.example.dockerguarddevelopment.osv.dto.request.OsvApiRequest;
import com.example.dockerguarddevelopment.osv.dto.request.SystemRequest;
import com.example.dockerguarddevelopment.osv.dto.response.OsvApiResponse;
import com.example.dockerguarddevelopment.osv.dto.response.SystemResponse;

import java.io.IOException;
import java.util.List;

public class CveApiService {

    OsvApiClient osvApiClient = new OsvApiClient();
    CveApiClient cveApiClient = new CveApiClient();

    public SystemResponse findVulnerabilitiesFromCveApi(SystemRequest systemRequest) throws IOException, InterruptedException {

        OsvApiRequest osvApiRequest = OsvApiMapper.mapSystemToOsvApiRequest(systemRequest);
        OsvApiResponse osvApiResponse = osvApiClient.getOsvVulnerabilityResponse(osvApiRequest);

        List<String> cveList = CveApiMapper.mapOsvApiResponseToCveList(osvApiResponse);

        List<SystemResponse.SystemVulnerability> systemVulnerabilities = cveList
                .stream()
                .map(this::getCveApiResponse)
                .map(CveApiMapper::mapCveApiResponseToSystemVulnerability)
                .toList();

        return new SystemResponse(
                systemRequest.packageName(),
                systemRequest.version(),
                systemRequest.ecosystem(),
                systemVulnerabilities
        );
    }

    private CveApiResponse getCveApiResponse(String cve) {
        try {
            return cveApiClient.getOsvVulnerabilityResponse(cve);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
