package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.Base;
import pages.HomePage;

public class TestClass extends DriverSetup, ExtentReportClass{

	HomePage hp;

	@BeforeTest
	public void start() {

		setupDriver();
		hp = new HomePage(driver);
		hp.openPage();
	}

	@AfterTest
	public void end() {

		quitDriver();
	}

	@Test
	public void test00_OpenPage() {

		setupDriver();
		hp = new HomePage(driver);
		hp.openPage();
		quitDriver();
	}

	@Test
	public void test01_EntriesPerPage() {

		hp.changeEntriesNumber("25");
		int size01 = hp.getNamesFromPage().size();
		Assert.assertTrue(size01 == 25);
		System.out.println("*table gives correct number of entries*");
	}

	@Test
	public void test02_AllNamesSize() {

		int sizeActual = hp.getAllNamesFromTable().size();
		int sizeExpected = hp.getNumberofEntriesinTable();
		Assert.assertTrue(sizeActual == sizeExpected);
		System.out.println("*number of entries is correct*");
	}

	@Test
	public void test03_TableIsSorted() {

		List<String> names = hp.getAllNamesFromTable();
		List<String> sortedNames = Base.sortList(names);
		Assert.assertEquals(names, sortedNames);
		System.out.println("*table is sorted as intended*");
	}

	@Test
	public void test04_dataPosition() {

		Assert.assertEquals("$725,000", hp.matchData("Paul Byrd", "salary"));
		Assert.assertEquals("64", hp.matchData("Olivia Liang", "age"));
		Assert.assertEquals("London", hp.matchData("Lael Greer", "office"));

		System.out.println("*Table is sorted as intended*");
	}

	@Test
	public <T> void test05_searchFalse() {

		List<T> results = hp.searchFunctionError("zzz");
		Assert.assertTrue(results.get(0).equals("No matching records found"));
		Assert.assertTrue(results.get(1).equals("0 elements founded"));
		Assert.assertTrue((Boolean) results.get(2) == true);
		Assert.assertTrue((Integer) results.get(3) == 1);
	}

	@Test
	public <T> void test06_search() {

		List<T> results = hp.searchFunction("London");
		Assert.assertTrue((Long) results.get(0) == 1);
		Assert.assertTrue((Boolean) results.get(1) == true);
	}

}
