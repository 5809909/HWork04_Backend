package com.github.a5809909.hwork04_backend;

import com.example.boss.myapplication.backend.studentApi.StudentApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class ApiBuilder { static StudentApi buildApi() {
    StudentApi.Builder builder = new StudentApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            // options for running against local devappserver
            // - 10.0.2.2 is localhost's IP address in Android emulator
            // - turn off compression when running against local devappserver
            .setRootUrl(Api.USER_URL)
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {

                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    // end options for devappserver

    return builder.build();
}
}
