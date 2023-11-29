package pl.dockerguardimage.core.functionality.cve.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CveApiResponse(
        @JsonProperty("Modified") String modified,
        @JsonProperty("Published") String published,
        @JsonProperty("access") Access access,
        @JsonProperty("assigner") String assigner,
        @JsonProperty("capec") List<Capec> capec,
        @JsonProperty("cvss") double cvss,
        @JsonProperty("cvss-time") String cvssTime,
        @JsonProperty("cvss-vector") String cvssVector,
        @JsonProperty("cwe") String cwe,
        @JsonProperty("id") String id,
        @JsonProperty("impact") Impact impact,
        @JsonProperty("last-modified") String lastModified,
        @JsonProperty("msbulletin") List<MsBulletin> msbulletin,
        @JsonProperty("oval") List<Oval> oval,
        @JsonProperty("references") List<String> references,
        @JsonProperty("refmap") RefMap refmap,
        @JsonProperty("saint") List<Saint> saint,
        @JsonProperty("summary") String summary,
        @JsonProperty("vulnerable_configuration") List<VulnerableConfiguration> vulnerableConfiguration,
        @JsonProperty("vulnerable_configuration_cpe_2_2") List<Object> vulnerableConfigurationCpe22,
        @JsonProperty("vulnerable_product") List<String> vulnerableProduct
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Access(
            @JsonProperty("authentication") String authentication,
            @JsonProperty("complexity") String complexity,
            @JsonProperty("vector") String vector
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Capec(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("prerequisites") String prerequisites,
            @JsonProperty("related_weakness") List<String> relatedWeakness,
            @JsonProperty("solutions") String solutions,
            @JsonProperty("summary") String summary
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Impact(
            @JsonProperty("availability") String availability,
            @JsonProperty("confidentiality") String confidentiality,
            @JsonProperty("integrity") String integrity
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MsBulletin(
            @JsonProperty("bulletin_id") String bulletinId,
            @JsonProperty("bulletin_url") String bulletinUrl,
            @JsonProperty("date") String date,
            @JsonProperty("impact") String impact,
            @JsonProperty("knowledgebase_id") String knowledgebaseId,
            @JsonProperty("knowledgebase_url") String knowledgebaseUrl,
            @JsonProperty("severity") String severity,
            @JsonProperty("title") String title
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Oval(
            @JsonProperty("accepted") String accepted,
            @JsonProperty("class") String clazz,
            @JsonProperty("contributors") List<Contributor> contributors,
            @JsonProperty("description") String description,
            // ... other fields ...
            @JsonProperty("family") String family,
            @JsonProperty("id") String id,
            @JsonProperty("status") String status,
            @JsonProperty("submitted") String submitted,
            @JsonProperty("title") String title,
            @JsonProperty("version") String version
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Contributor(
                @JsonProperty("name") String name,
                @JsonProperty("organization") String organization
        ) {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RefMap(
            @JsonProperty("bid") List<String> bid,
            @JsonProperty("cert") List<String> cert
            // ... other fields ...
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Saint(
            @JsonProperty("bid") String bid,
            @JsonProperty("description") String description,
            @JsonProperty("id") String id,
            @JsonProperty("osvdb") String osvdb,
            @JsonProperty("title") String title,
            @JsonProperty("type") String type
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VulnerableConfiguration(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title
    ) {
    }
}
