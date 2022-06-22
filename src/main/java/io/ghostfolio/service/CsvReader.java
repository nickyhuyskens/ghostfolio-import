package io.ghostfolio.service;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Named
public class CsvReader {

    public List<String[]> processSheet(CompletedFileUpload csv) throws IOException {
        Scanner scanner = new Scanner(csv.getInputStream());
        scanner.useDelimiter("\n");
        scanner.next();
        return scanner.tokens()
                .map(row -> row.split(","))
                .collect(Collectors.toList());
    }

}
