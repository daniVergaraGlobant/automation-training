package frameworks.web;

import org.junit.runners.model.FrameworkMethod;

/**
 * @author Juan Krzemien
 */
class WebDriverFrameworkMethod extends FrameworkMethod {

    private final Browser browser;

    /**
     * Returns a new {@code FrameworkMethod} for {@code method}
     *
     * @param method  original framework test method
     * @param browser browser associated with this test method
     */
    WebDriverFrameworkMethod(FrameworkMethod method, Browser browser) {
        super(method.getMethod());
        this.browser = browser;
    }

    Browser getBrowser() {
        return browser;
    }
}