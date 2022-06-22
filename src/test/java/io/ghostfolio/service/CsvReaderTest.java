package io.ghostfolio.service;

import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class CsvReaderTest {

    @Inject
    private CsvReader csvReader;

    @Test
    public void test() throws IOException {
        List<String[]> strings = csvReader.processSheet(new CompletedFileUpload() {
            @Override
            public Optional<MediaType> getContentType() {
                return Optional.empty();
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getFilename() {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public long getDefinedSize() {
                return 0;
            }

            @Override
            public boolean isComplete() {
                return false;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(getClass().getClassLoader().getResource("test.csv").getFile());
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public ByteBuffer getByteBuffer() throws IOException {
                return null;
            }
        });

        assertThat(strings).hasSize(5);
        assertThat(strings.get(0)[0]).isEqualTo("Deposit");
        assertThat(strings.get(1)[0]).isEqualTo("Market buy");
        assertThat(strings.get(2)[0]).isEqualTo("Dividend (Return of capital)");
        assertThat(strings.get(3)[0]).isEqualTo("Dividend (Ordinary)");
        assertThat(strings.get(4)[0]).isEqualTo("Market sell");
    }

}
