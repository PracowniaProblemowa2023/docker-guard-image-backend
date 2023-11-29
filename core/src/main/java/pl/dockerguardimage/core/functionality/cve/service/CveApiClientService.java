package pl.dockerguardimage.core.functionality.cve.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.cve.model.CveApiRequest;
import pl.dockerguardimage.core.functionality.cve.model.CveApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CveApiClientService {

    private static final String CVE_URL = "https://cve.circl.lu/api/cve/";

    ObjectMapper mapper = new ObjectMapper();

    public CveApiResponse getOsvVulnerabilityResponse(CveApiRequest cveApiRequest) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CVE_URL + cveApiRequest.cve()))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), CveApiResponse.class);
    }
}
