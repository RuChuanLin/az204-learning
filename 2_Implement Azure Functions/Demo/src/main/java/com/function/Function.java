package com.function;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import java.util.Optional;

public class Function {

    @FunctionName("HttpBlobFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            /* ▼ NEW: Blob output binding ▼ */
            @BlobOutput(
                    name        = "blob",
                    dataType    = "string",              // or "string"
                    path        = "messages/{rand-guid}.txt",
                    connection  = "AzureWebJobsStorage"
            )
            OutputBinding<String> blobOut,
            final ExecutionContext context) {

        String name = request.getQueryParameters().get("name");
        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass ?name=")
                    .build();
        }

        /* Write to the blob */
        blobOut.setValue("Hello " + name + " from Blob output!");

        return request.createResponseBuilder(HttpStatus.OK)
                .body("Wrote greeting for " + name + " to Blob")
                .build();
    }

}