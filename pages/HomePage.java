package pages;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage<T> extends Base {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	String url = "https://datatables.net/examples/advanced_init/dt_events.html";
	String namexp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td[1]";
	String nextButtonxp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[3]/div[2]/div/button[9]";
	String agexp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td[4]";
	String mainxp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td";
	String errorxp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td";
	String allElxp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td";
	String officexp = "/html/body/div[2]/div[3]/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td[3]";

	public void openPage() {

		driver.get(url);
	}

	public int getNumberofEntriesinTable() {

		String txt = driver.findElement(By.id("example_info")).getText();
		String txt1 = txt.split("of ")[1];
		String txtFinal = txt1.replace(" entries", "");
		return Integer.valueOf(txtFinal);
	}

	public void changeEntriesNumber(String value) {

		Select select = new Select(driver.findElement(By.xpath("//select[@name='example_length']")));
		select.selectByValue(value);
	}

	public List<String> getNamesFromPage() {

		List<WebElement> elements = driver.findElements(By.xpath(namexp));
		List<String> names = elements.stream().map(s -> s.getText()).collect(Collectors.toList());

		return names;
	}

	public List<String> getAllNamesFromTable() {

		List<String> names = new ArrayList<>();
		List<WebElement> elements = driver.findElements(By.xpath(namexp));
		names = elements.stream().map(s -> s.getText()).collect(Collectors.toList());

		String nextButtonClass = driver.findElement(By.xpath(nextButtonxp)).getAttribute("class");
		while (!nextButtonClass.contains("disable")) {
			driver.findElement(By.xpath(nextButtonxp)).click();
			elements = driver.findElements(By.xpath(namexp));
			names.addAll(elements.stream().map(s -> s.getText()).collect(Collectors.toList()));
			nextButtonClass = driver.findElement(By.xpath(nextButtonxp)).getAttribute("class");
		}
		return names;
	}

	public List<String> getAllDataFromTable(String endxp) {

		String xpath = mainxp + endxp;
		List<String> data = new ArrayList<>();
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		data = elements.stream().map(s -> s.getText()).collect(Collectors.toList());

		String nextButtonClass = driver.findElement(By.xpath(nextButtonxp)).getAttribute("class");
		while (!nextButtonClass.contains("disable")) {
			driver.findElement(By.xpath(nextButtonxp)).click();
			elements = driver.findElements(By.xpath(xpath));
			data.addAll(elements.stream().map(s -> s.getText()).collect(Collectors.toList()));
			nextButtonClass = driver.findElement(By.xpath(nextButtonxp)).getAttribute("class");
		}
		driver.navigate().refresh();
		return data;
	}

	public String matchData(String name, String type) {

		String xpend = "";
		switch (type) {
		case "age": {
			xpend = "[4]";
		}
			break;
		case "office": {
			xpend = "[3]";
		}
			break;
		case "salary": {
			xpend = "[6]";
		}
			break;
		case "date": {
			xpend = "[5]";
		}
			break;
		case "position": {
			xpend = "[2]";
		}
			break;
		case "name": {
			xpend = "[1]";
		}
			break;
		}
		List<String> names = getAllDataFromTable("[1]");
		driver.navigate().refresh();
		List<String> target = getAllDataFromTable(xpend);
		driver.navigate().refresh();
		int index = names.indexOf(name);

		return target.get(index);
	}

	public List<T> searchFunctionError(String txt) {

		List<WebElement> dummyList = new ArrayList<>();
		List<T> result = new ArrayList<>();
		String note = "";
		driver.findElement(By.id("dt-search-0")).sendKeys(txt);
		String error = driver.findElement(By.xpath(errorxp)).getText();
		try {
			dummyList = driver.findElements(By.xpath(allElxp));
		} catch (Exception e) {
		}
		String txt2 = driver.findElement(By.id("example_info")).getText();
		Boolean value = txt2.split("Showing ")[1].startsWith("0");
		if (dummyList.size() == 1) {
			note = "0 elements founded";
		}
		result.add((T) error);
		result.add((T) note);
		result.add((T) value);
		result.add((T) (Integer) dummyList.size());

		return result;
	}

	public List<T> searchFunction(String txt) {

		List<T> results = new ArrayList<>();
		List<String> towns = new ArrayList<>();
		Boolean eql = false;
		driver.findElement(By.id("dt-search-0")).sendKeys(txt);
		List<WebElement> townsEl = driver.findElements(By.xpath(officexp));
		towns = townsEl.stream().map(el -> el.getText()).collect(Collectors.toList());

		Long distinct = towns.stream().distinct().count();
		int size = towns.stream().filter(t -> t.equals(txt)).collect(Collectors.toList()).size();
		if (towns.size() == size) {
			eql = true;
		}
		results.add((T) distinct);
		results.add((T) eql);
		return results;
	}

}
