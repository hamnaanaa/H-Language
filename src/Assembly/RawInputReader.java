package Assembly;

import Assembly.AssemblyExceptions.FunctionalExceptions.WrongFilePathException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// TODO : check the magic number before reading the file for correctness
/**
 * Class that represents the first step of the Assembly process:
 * -> read all lines from the given file
 */
public class RawInputReader {
    /**
     * ArrayList of Strings containing all lines read from the file with the given path
     */
    private ArrayList<String> allLines;

    /**
     * Get-method for all lines of RawInputReader
     * @return all lines that the reader could read as ArrayList
     */
    ArrayList<String> getAllLines() {
        return allLines;
    }

    /**
     * Constructor for RawInputReader, that takes file path as input and reads every line of this file
     * @param filePath path of the file to read
     * @throws IOException file doesn't exist or unable to read lines
     */
    RawInputReader(String filePath) throws IOException {
        if (filePath == null)
            throw new WrongFilePathException("\nNon-valid path for a file");

        this.allLines = new ArrayList<>();
        readAllLines(filePath);
    }

    /**
     * Method to read all lines from the file with given file Path using BufferedReader line-by-line
     * @param filePath path of the file to read the lines from
     * @throws IOException if file not found
     */
    private void readAllLines(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null) {
            this.allLines.add(line);
            line = reader.readLine();
        }

        reader.close();
    }
}
