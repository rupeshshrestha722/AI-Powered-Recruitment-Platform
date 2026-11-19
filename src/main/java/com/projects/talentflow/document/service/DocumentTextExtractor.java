
package com.projects.talentflow.document.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class DocumentTextExtractor {

    private final Tika tika;

    public DocumentTextExtractor() {
        this.tika = new Tika();
    }

    public String extractText(byte[] fileContent) {

        if (fileContent == null || fileContent.length == 0) {
            throw new IllegalArgumentException("Document is empty");
        }

        try (ByteArrayInputStream inputStream =
                     new ByteArrayInputStream(fileContent)) {

            String extractedText = tika.parseToString(inputStream);

            if (extractedText == null || extractedText.isBlank()) {
                throw new IllegalArgumentException(
                        "No readable text found in the document"
                );
            }

            return extractedText.trim();

        } catch (IOException | TikaException exception) {
            throw new IllegalArgumentException(
                    "Unable to extract text from document",
                    exception
            );
        }
    }
}
