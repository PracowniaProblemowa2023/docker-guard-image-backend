package pl.dockerguardimage.core.functionality.syft.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.common.properties.SyftProperties;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

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
    public void execute(ImageScan imageScan) {
        var command = buildCommand(imageScan.getImageName(), imageScan.getId());
        int exitCode;
        try {
            var process = Runtime.getRuntime().exec(command);
            saveErrorIfExist(process.getErrorStream(), imageScan);
            exitCode = process.waitFor();
        } catch (InterruptedException | IOException e) {
            var errorMsg = "Cannot process command: " + command + "\n" + e.getMessage();
            imageScan.setErrorMsg(errorMsg);
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
                    imageScan.setErrorMsg(errorMsg);
                }
            }
            scanner.close();
        }).start();
    }

    private String buildCommand(String imageName, Long imageScanId) {
        var path = String.format(syftProperties.getDirectory(), imageScanId);
        return String.format(syftProperties.getCommand(), imageName, path);
    }

}
