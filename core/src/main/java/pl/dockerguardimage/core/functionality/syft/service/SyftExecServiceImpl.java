package pl.dockerguardimage.core.functionality.syft.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.common.properties.SyftProperties;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Slf4j
@Service
@AllArgsConstructor
class SyftExecServiceImpl implements SyftExecService {

    private final SyftProperties syftProperties;
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS");

    @Override
    public void execute(String imageName, ImageScan imageScan) {
        var command = buildCommand(imageName, imageScan.getId());
        int exitCode;
        try {
            var process = Runtime.getRuntime().exec(command);
            saveErrorIfExist(process.getErrorStream(), imageScan);
            exitCode = process.waitFor();
        } catch (InterruptedException | IOException e) {
            setError(imageScan, "Cannot process command: " + command + "\n" + e.getMessage());
            return;
        }

        if (exitCode == 0) {
            log.debug("Command for imagescan: {} run successfully (command: {})", imageScan.getId(), command);
            return;
        }

        log.debug("Process executed with errors for imagescan: {} (command: {})", imageScan.getId(), command);
    }

    private void saveErrorIfExist(InputStream inputStream, ImageScan imageScan) {
        new Thread(() -> {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                synchronized (this) {
                    var errorMsg = format.format(new Date()) + " ERROR: " + scanner.nextLine();
                    setError(imageScan, errorMsg);
                }
            }
            scanner.close();
        }).start();
    }

    private static void setError(ImageScan imageScan, String errorMsg) {
        imageScan.setErrorMsg(errorMsg);
        imageScan.setResult(Result.ERROR);
    }

    private String buildCommand(String imageName, Long imageScanId) {
        var path = String.format(syftProperties.getDirectory(), imageScanId);
        return String.format(syftProperties.getCommand(), imageName, path);
    }

}
