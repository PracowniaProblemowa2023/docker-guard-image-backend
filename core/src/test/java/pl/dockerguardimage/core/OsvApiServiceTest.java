package pl.dockerguardimage.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.dockerguardimage.core.osv.OsvApiClient;
import pl.dockerguardimage.core.osv.OsvApiService;
import pl.dockerguardimage.core.osv.dto.request.SystemRequestService;
import pl.dockerguardimage.core.osv.dto.response.SystemResponseService;

import java.io.IOException;
import java.util.stream.Stream;

public class OsvApiServiceTest {

    private static OsvApiService service;

    @BeforeAll
    public static void setUp() {
        OsvApiClient osvApiClient = new OsvApiClient();
        service = new OsvApiService(osvApiClient);
    }

    protected static Stream<Arguments> provideVulnerablePackageVersionEcosystem() {

        return Stream.of(
                Arguments.of("org.apache.activemq:activemq-client", "5.15.9", "Maven"), // CVE-2023-46604
                Arguments.of("org.elasticsearch:elasticsearch", "7.9.3", "Maven"), // CVE-2023-31418, CVE-2023-31417, CVE-2023-31419
                Arguments.of("org.glassfish.main.orb:orb-connector", "5.1.0", "Maven"), // CVE-2023-5763
                Arguments.of("org.opencrx:opencrx-client", "5.2.0", "Maven"), // CVE-2023-46502
                Arguments.of("org.owasp.esapi:esapi", "2.2.3.1", "Maven"), // GHSA-7c2q-5qmr-v76q
                Arguments.of("org.wildfly.core:wildfly-controller", "12.0.3.Final", "Maven"), // CVE-2023-4061
                Arguments.of("com.fasterxml.jackson.core:jackson-databind", "2.9.10.4", "Maven"), // CVE-2020-25649

                Arguments.of("jquery", "3.4.1", "npm"), // CVE-2019-11358
                Arguments.of("angular", "8.0.0", "npm"), // CVE-2020-7676
                Arguments.of("node-fetch", "2.6.0", "npm"), // CVE-2020-15168
                Arguments.of("marked", "0.7.0", "npm"), // CVE-2019-16729
                Arguments.of("electron", "7.1.2", "npm"), // CVE-2020-4076
                Arguments.of("socket.io", "2.3.0", "npm"), // CVE-2020-28481
                Arguments.of("moment", "2.24.0", "npm"), // CVE-2020-26289

                Arguments.of("django", "3.1.7", "PyPI"), // CVE-2021-28658
                Arguments.of("flask", "1.1.2", "PyPI"), // CVE-2020-27218
                Arguments.of("pillow", "8.1.0", "PyPI"), // CVE-2021-25289
                Arguments.of("pyyaml", "5.3.1", "PyPI"), // CVE-2020-14343
                Arguments.of("numpy", "1.19.2", "PyPI"), // CVE-2021-21300
                Arguments.of("requests", "2.25.1", "PyPI"), // CVE-2021-33503
                Arguments.of("scipy", "1.5.4", "PyPI") // CVE-2021-25959
        );
    }

    protected static Stream<Arguments> provideNotVulnerablePackageVersionEcosystem() {
        return Stream.of(
                Arguments.of("org.apache.activemq:activemq-client", "5.18.3", "Maven"),
                Arguments.of("org.glassfish.main.orb:orb-connector", "7.0.10", "Maven"),
                Arguments.of("com.fasterxml.jackson.core:jackson-databind", "2.13.15", "Maven"),
                Arguments.of("com.google.code.gson:gson", "2.0.9", "Maven")
        );
    }

    @ParameterizedTest
    @MethodSource("provideVulnerablePackageVersionEcosystem")
    public void testOsvApiServiceVulnerablePackage(String pack, String version, String eco) throws IOException, InterruptedException {
        SystemRequestService systemRequest = new SystemRequestService(pack, version, eco);
        SystemResponseService systemResponse = service.findVulnerabilitiesFromOsvApi(systemRequest);
        Assertions.assertEquals(pack, systemResponse.packageName());
        Assertions.assertEquals(version, systemResponse.version());
        Assertions.assertEquals(eco, systemResponse.ecosystem());
        Assertions.assertTrue(systemResponse.vulnerabilities().size() > 0);
    }

    @ParameterizedTest
    @MethodSource("provideNotVulnerablePackageVersionEcosystem")
    public void testOsvApiServiceNonVulnerablePackage(String pack, String version, String eco)throws IOException, InterruptedException {
        SystemRequestService systemRequest = new SystemRequestService(pack, version, eco);
        SystemResponseService systemResponse = service.findVulnerabilitiesFromOsvApi(systemRequest);
        Assertions.assertEquals(pack, systemResponse.packageName());
        Assertions.assertEquals(version, systemResponse.version());
        Assertions.assertEquals(eco, systemResponse.ecosystem());
        Assertions.assertEquals(0, systemResponse.vulnerabilities().size());
    }
}
