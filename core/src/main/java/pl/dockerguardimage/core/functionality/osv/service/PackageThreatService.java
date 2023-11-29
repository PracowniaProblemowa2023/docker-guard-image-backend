package pl.dockerguardimage.core.functionality.osv.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.io.IOException;

public interface PackageThreatService {

     void createAllByImageScanOsv(ImageScan imageScan) throws IOException, InterruptedException;

     void createAllByImageScanCve(ImageScan imageScan) throws IOException, InterruptedException;
}
