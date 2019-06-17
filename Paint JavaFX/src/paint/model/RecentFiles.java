package paint.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruna
 */
public class RecentFiles {

    private final File arquivo = new File("recents.txt");

// Reading from recents.txt
    public List<String> read() throws FileNotFoundException, IOException {
        List<String> recentsList = new ArrayList<>();
        // Initialize reader
        try (BufferedReader bReader = new BufferedReader(new FileReader(arquivo))) {
            String line;
            // reading lines
            do {
                line = bReader.readLine();
                recentsList.add(line);
            } while (line != null);
            // remove last element null
            recentsList.remove(null);
        }

        return recentsList;
    }

    // Writing in recents.txt 
    public void write(String t) throws IOException {
        try (BufferedWriter arqBW = new BufferedWriter(new FileWriter(arquivo, true))) {
            arqBW.write(t);
            arqBW.newLine();
        }
    }
}
