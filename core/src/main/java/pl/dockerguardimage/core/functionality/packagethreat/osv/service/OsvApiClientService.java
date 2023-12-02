package pl.dockerguardimage.core.functionality.packagethreat.osv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiRequest;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OsvApiClientService {

    ObjectMapper mapper = new ObjectMapper();

    public OsvApiResponse getOsvVulnerabilityResponse(OsvApiRequest osvApiRequest) throws IOException, InterruptedException {

        String body = mapper.writeValueAsString(osvApiRequest);

        String OSV_URL = "https://api.osv.dev/v1/query";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OSV_URL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), OsvApiResponse.class);
    }
}
