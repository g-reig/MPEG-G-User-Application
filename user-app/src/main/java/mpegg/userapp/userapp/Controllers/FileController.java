package mpegg.userapp.userapp.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @PostMapping("/api/create")
    public String uploadFiles(@RequestParam("dg_md") MultipartFile dg_md, @RequestParam("dt_md") MultipartFile[] dt_md) {
        return "ok";
    }
}
