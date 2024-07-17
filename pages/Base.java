package pages;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

	public WebDriver driver;
	public WebDriverWait wait;

	public Base(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public static List<String> sortList(List<String> data) {

		List<String> sortedData = data.stream().sorted().collect(Collectors.toList());
		return sortedData;
	}

	public static List<Integer> getIndeciesOf(List<String> data, String searchQuery) {

		List<Integer> indeces = new ArrayList<>();
		for (int a = 0; a < data.size(); a++) {
			if (data.get(a).equals(searchQuery)) {
				indeces.add(a);
			}
		}
		return indeces;
	}

	public static String createFolderPath() {

		String finalPath = "";
		String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\Selenium WebTables\\reportsFolder\\reportNo";
		String end = ".html";

		for (int a = 0; a < 200; a++) {
			File file = new File(path + String.valueOf(a) + end);
			if (!file.exists()) {
				finalPath = path + String.valueOf(a) + end;
				break;
			}
		}
		return finalPath;
	}

}
