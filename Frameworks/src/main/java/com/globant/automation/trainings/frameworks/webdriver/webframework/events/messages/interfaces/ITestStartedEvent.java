package com.globant.automation.trainings.frameworks.webdriver.webframework.events.messages.interfaces;

import com.globant.automation.trainings.frameworks.webdriver.webframework.webdriver.Browser;

/**
 * @author Juan Krzemien
 */
public interface ITestStartedEvent extends IEvent {
    Browser getBrowser();
}
