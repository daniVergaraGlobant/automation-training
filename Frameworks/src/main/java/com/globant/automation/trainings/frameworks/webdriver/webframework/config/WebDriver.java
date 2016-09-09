package com.globant.automation.trainings.frameworks.webdriver.webframework.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.globant.automation.trainings.frameworks.webdriver.webframework.config.interfaces.IWebDriverConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Juan Krzemien
 */
@JsonSerialize
class WebDriver implements IWebDriverConfig {

    private static final int DEFAULT_TIMEOUT = 30;
    private static final int DEFAULT_POOLING_INTERVAL = 500;

    @JsonProperty
    private int explicitTimeOut = DEFAULT_TIMEOUT;

    @JsonProperty
    private int implicitTimeOut = 0;

    @JsonProperty
    private int pageLoadTimeout = DEFAULT_TIMEOUT;

    @JsonProperty
    private int scriptTimeout = DEFAULT_TIMEOUT;

    @JsonProperty
    private int pollingEveryMs = DEFAULT_POOLING_INTERVAL;

    @JsonProperty
    private URL remoteURL = new URL("http://localhost:4444/wd/hub");

    @JsonProperty
    private boolean useSeleniumGrid = false;

    @JsonProperty
    private boolean useListener = true;

    WebDriver() throws MalformedURLException {
    }

    @Override
    public int getExplicitTimeOut() {
        return explicitTimeOut;
    }

    @Override
    public int getImplicitTimeOut() {
        return implicitTimeOut;
    }

    @Override
    public long getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    @Override
    public long getScriptTimeout() {
        return scriptTimeout;
    }

    @Override
    public int getPollingEveryMs() {
        return pollingEveryMs;
    }

    @Override
    public URL getRemoteURL() {
        return remoteURL;
    }

    @Override
    public boolean isUseSeleniumGrid() {
        return useSeleniumGrid;
    }

    @Override
    public boolean isUseListener() {
        return useListener;
    }
}