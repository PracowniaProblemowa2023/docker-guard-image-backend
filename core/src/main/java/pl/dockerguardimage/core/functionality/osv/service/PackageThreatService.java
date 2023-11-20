package pl.dockerguardimage.core.functionality.osv.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.io.IOException;

public interface PackageThreatService {

     void createAllByImageScan(ImageScan imageScan) throws IOException, InterruptedException;
}
