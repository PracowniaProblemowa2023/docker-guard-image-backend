package pl.dockerguardimage.core.functionality.cve;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CveApiClient {

    private static final String CVE_URL = "https://cve.circl.lu/api/cve/";

    ObjectMapper mapper = new ObjectMapper();

    public CveApiResponse getOsvVulnerabilityResponse(String cve) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CVE_URL + cve))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Files.writeString(
                Path.of("cve_response.json"),
                response.body(),
                StandardOpenOption.CREATE);

        return mapper.readValue(response.body(), CveApiResponse.class);
    }
}
