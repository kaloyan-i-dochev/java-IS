package test;

import org.junit.jupiter.api.Test;

import ID3.ID3;
import NBC.NaiveBayes;
import common.CrossValidator;
import common.DataEntry;
import common.DataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CrossValidatorTest {
	private final int voting_name_count = 16;
	private final int cancer_name_count = 9;

	@Test
	public void listFilesInCurrentDirectory() {
		File currentDirectory = new File("./src/test");
		File[] filesList = currentDirectory.listFiles();

		System.out.println("Current Directory: " + currentDirectory.getAbsolutePath());
		for (File file : filesList) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
	}

	@Test
	public void testCrossValidation_voting() throws IOException {
	    // Create a dataset
	    DataSet dataSet = new DataSet(voting_name_count); // voting had 16 attr

	    // Assuming the attribute names are already set in the DataSet
	    List<String> attributeNamesList = dataSet.getAttributeNames();

	    // Load entries from the file
	    try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/house-votes-84.data"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            String label = parts[0];
	            Map<String, String> attributes = new HashMap<>();
	            for (int i = 1; i < parts.length; i++) {
	                attributes.put(attributeNamesList.get(i - 1), parts[i]);
	            }
	            dataSet.addEntry(new DataEntry(label, attributes));
	        }
	    }

	    NaiveBayes nbc = new NaiveBayes(dataSet);
	    // Perform 10-fold cross-validation
	    CrossValidator validator = new CrossValidator(dataSet, 10, nbc::predict);
	    double averageAccuracy = validator.validate();

	    // Assert that the average accuracy is between 0 and 1 (inclusive)
	    System.out.println(averageAccuracy);
	    assertTrue(averageAccuracy >= 0 && averageAccuracy <= 1);
	}
	
	@Test
	public void testCrossValidation_cancer() throws IOException {
	    // Create a dataset
	    DataSet dataSet = new DataSet(cancer_name_count); // cancer had 9 attr

	    // Assuming the attribute names are already set in the DataSet
	    List<String> attributeNamesList = dataSet.getAttributeNames();

	    // Load entries from the file
	    try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/breast-cancer.data"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            String label = parts[0];
	            Map<String, String> attributes = new HashMap<>();
	            for (int i = 1; i < parts.length; i++) {
	                attributes.put(attributeNamesList.get(i - 1), parts[i]);
	            }
	            dataSet.addEntry(new DataEntry(label, attributes));
	        }
	    }

	    NaiveBayes nbc = new NaiveBayes(dataSet);
	    // Perform 10-fold cross-validation
	    CrossValidator validator = new CrossValidator(dataSet, 10, nbc::predict);
	    double averageAccuracy = validator.validate();

	    // Assert that the average accuracy is between 0 and 1 (inclusive)
	    System.out.println(averageAccuracy);
	    assertTrue(averageAccuracy >= 0 && averageAccuracy <= 1);
	}

    @Test
    public void testID3_CrossValidation_voting() throws IOException {
        // Create a dataset
        DataSet dataSet = new DataSet(voting_name_count);

        // Load entries from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/house-votes-84.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String label = parts[0];
                Map<String, String> attributes = new HashMap<>();
                for (int i = 1; i < parts.length; i++) {
                    attributes.put(dataSet.getAttributeNames().get(i - 1), parts[i]);
                }
                dataSet.addEntry(new DataEntry(label, attributes));
            }
        }

        // Initialize the ID3 algorithm
        ID3 id3 = new ID3(dataSet);

        // Train the ID3 algorithm
        id3.train(dataSet);

        // Perform 10-fold cross-validation
        CrossValidator validator = new CrossValidator(dataSet, 10, id3::predict);

        double averageAccuracy = validator.validate();

        // Assert that the average accuracy is between 0 and 1 (inclusive)
        System.out.println(averageAccuracy);
        assertTrue(averageAccuracy >= 0 && averageAccuracy <= 1);
    }

    @Test
    public void testID3_CrossValidation_cancer() throws IOException {
        // Create a dataset
        DataSet dataSet = new DataSet(cancer_name_count);

        // Load entries from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/breast-cancer.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String label = parts[0];
                Map<String, String> attributes = new HashMap<>();
                for (int i = 1; i < parts.length; i++) {
                    attributes.put(dataSet.getAttributeNames().get(i - 1), parts[i]);
                }
                dataSet.addEntry(new DataEntry(label, attributes));
            }
        }

        // Initialize the ID3 algorithm
        ID3 id3 = new ID3(dataSet);

        // Train the ID3 algorithm
        id3.train(dataSet);

        // Perform 10-fold cross-validation
        CrossValidator validator = new CrossValidator(dataSet, 10, id3::predict);

        double averageAccuracy = validator.validate();

        // Assert that the average accuracy is between 0 and 1 (inclusive)
        System.out.println(averageAccuracy);
        assertTrue(averageAccuracy >= 0 && averageAccuracy <= 1);
    }
}
