package Practice;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UniqueCsvValues {

  public static String[] getUniqueValues(String filePath) {
    Set<String> uniqueValues = new LinkedHashSet<>();

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String[] cells = scanner.nextLine().split(";"); // Разделение по запятым
        for (String cell : cells) {
          if (!cell.trim().isEmpty()) {
            uniqueValues.add(cell.trim());
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new String[0];
    }

    return uniqueValues.toArray(new String[0]);
  }

  public static void main(String[] args) {
    String[] uniqueValues = getUniqueValues("D:\\Work\\LearnQA_JavaAPI\\src\\test\\java\\Homework\\PopPass.csv");
    System.out.println(Arrays.toString(uniqueValues));
  }
}