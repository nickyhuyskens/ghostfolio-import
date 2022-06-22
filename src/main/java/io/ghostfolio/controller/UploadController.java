package io.ghostfolio.controller;

import io.ghostfolio.service.ProcessSheetService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Controller("/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private final ProcessSheetService processSheetService;

    public UploadController(ProcessSheetService processSheetService) {
        this.processSheetService = processSheetService;
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Post
    public HttpResponse upload(CompletedFileUpload file, String token, String platform) throws IOException {
        LOGGER.info("uploaded a file: {}", file.getFilename());
        processSheetService.processSheet(file, token);
        return HttpResponse.accepted();
    }

}
