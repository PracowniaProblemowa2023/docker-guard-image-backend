package pl.dockerguardimage.core.functionality.packagethreat.osv.model;

import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OsvApiMapperService {

    public static String getSeverityFromVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        if (vulnerability.affected().get(0).ecosystemSpecific() != null &&
                vulnerability.affected().get(0).ecosystemSpecific().severity() != null) {
            return vulnerability.affected().get(0).ecosystemSpecific().severity();
        }
        if (vulnerability.databaseSpecific() != null) {
            return vulnerability.databaseSpecific().severity();
        }
        return "";
    }

    public static String getAliasesFromVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        return vulnerability.aliases() != null
                ? String.join(",", vulnerability.aliases())
                : "";
    }

    public static String getPayloadName(SyftPayload syftPayload) {
        String syftName = syftPayload.getName();
        if (syftName.contains("spring-boot")) {
            syftName = "org.springframework.boot:" + syftName;
        } else if (syftName.contains("spring")) {
            syftName = "org.springframework:" + syftName;
        } else if (syftName.contains("log4j")) {
            syftName = "org.apache.logging.log4j:" + syftName;
        }
        return syftName;
    }

    public static String getPayloadType(SyftPayload syftPayload) {
        String syftType = syftPayload.getType();
        return switch (syftType) {

            case "java-archive" -> "Maven";
            case "deb" -> "Debian";
            case "python" -> "PyPI";
            case "rpm" -> "Linux";
            default -> syftType;
        };
    }

    public static ZonedDateTime parseToZonedDateTime(String dateTimeString) {
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, localDateTimeFormatter);
            return localDateTime.atZone(ZoneId.systemDefault());
        } catch (DateTimeParseException e) {
            try {
                return ZonedDateTime.parse(dateTimeString, zonedDateTimeFormatter);
            } catch (DateTimeParseException ex) {
                throw new DateTimeParseException("Date cannot be parsed: " + dateTimeString, dateTimeString, 0);
            }
        }
    }

    public static String getCveAlias(OsvApiResponse.OsvApiVulnerability vulnerability) {
        return vulnerability.
                aliases()
                .stream()
                .filter(x -> x.contains("CVE"))
                .findAny()
                .orElse("");
    }

    public static OsvApiRequest mapPayloadToApiRequest(SyftPayload payload) {
        return OsvApiRequest
                .builder()
                .osvPackage(new OsvApiRequest.OsvPackage(getPayloadName(payload), getPayloadType(payload)))
                .version(payload.getVersion())
                .build();
    }

}
