package io.ghostfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@Controller("/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private final ProcessSheetService processSheetService;
    private final ObjectMapper objectMapper;

    public UploadController(ProcessSheetService processSheetService, ObjectMapper objectMapper) {
        this.processSheetService = processSheetService;
        this.objectMapper = objectMapper;
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Post
    public HttpResponse upload(CompletedFileUpload file,
                               String token,
                               String platform,
                               Map<String, String> map) throws IOException {
        LOGGER.info("uploaded a file: {}", file.getFilename());
        Map<String, String> accountIdsByCurrency = (Map<String, String>) objectMapper.readValue(map.get("accountIdsByCurrency"), Map.class);
        processSheetService.processSheet(file, platform, token, accountIdsByCurrency);
        return HttpResponse.accepted();
    }

}
