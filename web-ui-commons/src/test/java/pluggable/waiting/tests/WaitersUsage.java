package pluggable.waiting.tests;

import com.globant.automation.trainings.webdriver.waiting.ComplexWaiter;
import com.globant.automation.trainings.webdriver.waiting.SimpleWaiter;
import com.globant.automation.trainings.webdriver.waiting.conditions.Conditions;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pluggable.waiting.tests.base.HideNonRelatedStuff;

import java.util.List;

import static com.globant.automation.trainings.webdriver.waiting.conditions.Conditions.Browser.ContainsInUrl;
import static com.globant.automation.trainings.webdriver.waiting.conditions.Conditions.Element.Visibility.Visible;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Juan Krzemien
 */

public class WaitersUsage extends HideNonRelatedStuff {

    private final SimpleWaiter.Waiter<WebElement> ELEMENT_WAITER = new SimpleWaiter.Waiter<>();
    private final SimpleWaiter.Waiter<List<WebElement>> ELEMENTS_WAITER = new SimpleWaiter.Waiter<>();
    private final SimpleWaiter.Waiter<WebDriver> DRIVER_WAITER = new SimpleWaiter.Waiter<>();

    @Test
    public void simpleWaiterUsage() {
        ELEMENT_WAITER.withTimeOut(5).on(anElement).until(Visible);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. So element is visible.");
    }

    @Test
    public void simpleWaiterUsageOnManyElements() {
        ELEMENTS_WAITER.withTimeOut(5).on(manyElements).until(Conditions.Elements.Visibility.Visible);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. So element is visible.");
    }

    @Test(expected = TimeoutException.class)
    public void simpleWaiterTimesOut() {
        ELEMENT_WAITER.withTimeOut(1).on(anElement).until(Visible);
    }

    @Test
    public void simpleWaiterWithoutFailing() {
        ELEMENT_WAITER.withTimeOut(1).withoutFailing().on(anElement).until(Visible);
    }

    @Test
    public void complexWaiterUsage1() {
        new ComplexWaiter<>(anElement).is(Visible);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. So element is visible.");
    }

    @Test
    public void complexWaiterUsage2() {
        new ComplexWaiter<>(anElement).and(anotherElement).are(Visible);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. So element is visible.");
    }

    @Test
    public void complexWaiterUsageOnManyElements() {
        new ComplexWaiter<>(manyElements).are(Conditions.Elements.Visibility.Visible);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. So element is visible.");
    }

    @Test(expected = TimeoutException.class)
    public void complexWaiterTimesOut() {
        new ComplexWaiter<>(anElement).withTimeOut(1).is(Visible);
    }

    @Test
    public void simpleWaiterUsageTriFunctionCondition() {
        DRIVER_WAITER.withTimeOut(5).on(aDriver).until(ContainsInUrl, "about:blank");
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. Url matched.");
    }

    @Test
    public void simpleWaiterUsageTriFunctionCondition2() {
        WebElement element = DRIVER_WAITER.withTimeOut(5).on(aDriver).until(Conditions.Locator.Exists, anId);
        assertNotNull(element);
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. Element was found.");
    }

    @Test
    public void complexWaiterUsageTriFunctionCondition() {
        List<WebElement> elements = new ComplexWaiter<>(aDriver).withTimeOut(5).until(Conditions.Locator.Exists, anId);
        assertNotNull(elements);
        assertEquals("There should be one element in here!", 1, elements.size());
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown. Element was found.");
    }

    @Test(expected = TimeoutException.class)
    public void complexWaiterUsageTriFunctionConditionTimesOut() {
        List<WebElement> elements = new ComplexWaiter<>(aDriver).withTimeOut(1).until(Conditions.Locator.Exists, anXPath);
    }

    @Test
    public void complexWaiterWithoutFailing() {
        List<WebElement> elements = new ComplexWaiter<>(aDriver).withTimeOut(1).withoutFailing().until(Conditions.Locator.Exists, anXPath);
        assertNotNull(elements);
        assertEquals("There should be one element in here!", 0, elements.size());
        out.println("Done waiting! Since you can see this, a TimeoutException was not thrown due to withoutFailing().");
    }
}