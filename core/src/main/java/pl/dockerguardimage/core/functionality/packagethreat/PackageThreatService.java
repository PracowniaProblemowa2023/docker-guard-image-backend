package pl.dockerguardimage.core.functionality.packagethreat;

import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiBatchResponse;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.io.IOException;
import java.util.Set;

public interface PackageThreatService {

     void executeImageScanInProgressJob();

     Set<OsvApiBatchResponse> createAllByImageScanOsv(ImageScan imageScan) throws IOException, InterruptedException;

     void createAllByImageScanCve(ImageScan imageScan, Set<OsvApiBatchResponse> osvResponses) throws IOException, InterruptedException;
}
