// --== CS400 File Header Information ==--
// Name: Minghao Zhou
// Email: mzhou222@wisc.edu
// Team: JB
// Role: Testing Engineer
// TA: Harper
// Lecturer: Gary Dahl
// Notes to Grader: N/A
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TestSuite for testing methods and main argument functionalities of WeatherAppS
 *
 * @author Liangqi Cai, Minghao Zhou
 */
public class TestSuite {

  /**
   * Testing Contains method of CityNameList
   * 
   */
  @Test
 public void testCityNameListContains() {
    CityNameList test = new CityNameList();
    try {
      test.load(); // load the CityNameList
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // non-existing city
    if (test.contains("THISISNOTACITY")) {
      fail("contains() method fails: non-existing city is detected");
    }
    // existing city
    if (!test.contains("Mwanga") || !test.contains("Jeddah")) {
      fail("contains() method fails: existing cites are not detected");
    }
  }

  /**
   * Testing Search method of CityNameList. Search method should return the matched city names with
   * with corresponding country according to the input city name and country
   * 
   */
  @Test
  void testCityNameListSearch() throws FileNotFoundException {
    CityNameList test = new CityNameList();
    try {
      test.load(); // load the CityNameList
    } catch (IOException e) {
      e.printStackTrace();
    }
    String a = "";

    Iterator<String> testCity = test.search("Wuh", "[cn]"); // search city name containing "Wuh" with
                                                          // country CN
    while (testCity.hasNext()) {
          a += testCity.next();
      }
    if (a.equals("Wuhu  ----  CNWuhe Chengguanzhen  ----  CNWuhan  ----  CNWuhai  ----  CN") ){
        fail("fails to search cities");
    }
  }


  /**
   * Testing getDetailInfo method of WeatherTree.
   */
  @Test
  void testWeatherTreeGetDetailInfo() {
    // makes up a city named "ABC" with weather informations
    WeatherTree testTree = new WeatherTree();
    try {
      String a;
      try {
        a = testTree.getDetailInfo("London");
        // Data should be printed out with corresponding information types and correct numbers
        if (!a.contains("City: London") || !a.contains("All Weather Information")
            || !a.contains("Weather description") || !a.contains("Location longitude")
            || !a.contains("Location latitude") || !a.contains("Description")
            || !a.contains("Temperature") || !a.contains("Feels like temperature")
                || !a.contains("Minimum temperature") || !a.contains("Maximum temperature")
                || !a.contains("Pressure") || !a.contains("Humidity")
                || !a.contains("Wind Speed") || !a.contains("Visibility")) {
          fail("fails to get the detail information of the created city [London]");
        }
      } catch (IOException e) {
        e.printStackTrace();
        fail();
      }
      } catch (Exception e) {
      }
  }

  /**
   * Testing getImportantInfo method of WeatherTree.
   */
  @Test
  void testWeatherTreeGetImportantInfo() {
    // makes up a city named "CBA" with weather informations
    WeatherTree testTree = new WeatherTree();
    try {
      String a;
      try {
        a = testTree.getImportantInfo("Shanghai");
        // Only important data should be printed out with corresponding information types and correct
        // numbers
        if (!a.contains("City: Shanghai") || !a.contains("Important Weather Information")
            || !a.contains("Weather description") || !a.contains("Description")
            || !a.contains("Temperature") || a.contains("Minimum temperature")
            || a.contains("Maximum temperature")) {
          fail("fails to get the important information of the created city [London]");
        }
      } catch (IOException e) {
        e.printStackTrace();
        fail();
      }
      } catch (Exception e) {
      }
  }


  /**
   * Testing main argument with correct formated user input. When the user enters correct formated
   * input, WeatherApp should recognize user's goal and set corresponding command boolean to true.
   */
  @Test
  public void mainTestCommand() throws Exception {
    String[] programArgs = new String[] {"--help", "-l", "-v", "--detail", "--clean-cache"};
    ArgumentParser test = new ArgumentParser(programArgs);
    test.parseArgument();
    if (!test.isArgCleanCache() || !test.isArgHelp() || !test.isArgDetail() || !test.isArgList()
        || !test.isArgVersion()) {
      fail("fails to read user's command");
    }
  }

  /**
   * Testing main argument with incorrect formated user input. When the user enters incorrect
   * formated input, IllegalArgumentException should be thrown.
   */
  @Test
  public void mainTestWrongCommand() throws Exception {
    String[] programArgs = new String[] {"-BlahBlahBlah"};
    ArgumentParser test = new ArgumentParser(programArgs);
    Assertions.assertThrows(IllegalArgumentException.class, () -> test.parseArgument());
  }
}
