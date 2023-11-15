package pl.dockerguardimage.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DemoController {

    @GetMapping("/demo/admin")
    public String dupa() {
        return "admin";
    }

}
