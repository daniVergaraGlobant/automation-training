package com.globant.automation.trainings.webframework;

import com.globant.automation.trainings.logging.Logging;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * @author Juan Krzemien
 */
public class InjectPageObjectsParametersRunnerFactory implements ParametersRunnerFactory, Logging {

    public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
        return new RunnerWithParametersInjector(test);
    }

}