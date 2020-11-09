package test;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      
   File mainFolder = new File(System.getProperty("user.dir"));
      File files[] = mainFolder.listFiles();
      // If any of these files end in '_output.txt', delete it.
      for (int i = 0; i < files.length; i++) {
            File pes = files[i];
            if (pes.getName().contains("_output.txt")) {
               files[i].delete();
            }
      }
      Result result = JUnitCore.runClasses(TestSuite.class);

      System.out.println(System.lineSeparator());

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      if (result.wasSuccessful())  {
         System.out.println("All tests passed.");
      }
   }
}