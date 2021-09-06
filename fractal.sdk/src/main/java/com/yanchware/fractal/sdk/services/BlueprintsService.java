package com.yanchware.fractal.sdk.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;

@Data
@Slf4j
public class BlueprintsService {

    private final HttpClient client;

    public void instantiate() {
    }
}
