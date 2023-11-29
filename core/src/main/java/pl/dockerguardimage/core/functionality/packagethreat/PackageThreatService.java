package pl.dockerguardimage.core.functionality.packagethreat;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.io.IOException;

public interface PackageThreatService {

     void executeImageScanInProgressJob();

     void createAllByImageScanOsv(ImageScan imageScan) throws IOException, InterruptedException;

     void createAllByImageScanCve(ImageScan imageScan) throws IOException, InterruptedException;
}
