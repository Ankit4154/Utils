import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyTask {

	public static void main(String args[]) throws IOException {

		// Getting list of Files from current directory
		File[] files = new File("<Enter your file system path to DailyTask folder>").listFiles();
		// Storing initial file for comparison
		File lastModifiedFile = files[0];
		// Looping through all lastmodified timestamps to find out the latest
		for (File file : files) {
			if (file.lastModified() > lastModifiedFile.lastModified()) {
				lastModifiedFile = file;
			}
		}
		// Printing lastModified file name to console
		System.out.println(lastModifiedFile.getName());
		// Empty ArrayList of strings for storing all lines from the file
		List<String> newLines = new ArrayList<>();
		// Date creation via format to store in file first line
		String currentDateTime = new SimpleDateFormat("dd-MMMM-yyyy hh.mm aa")
				.format(new Date(System.currentTimeMillis()));
		// Generating date for file name
		String currentDateForFileName = currentDateTime.substring(0, currentDateTime.lastIndexOf("-") + 5).toString()
				.replaceAll("-", "");
		// Printing file name date on console
		System.out.println(currentDateForFileName);
		// Copying last modified file as dated file name generated above
		// Replacing/Overwriting any existing file with same name.
		File finalFile = Files.copy(Paths.get(lastModifiedFile.getAbsolutePath()),
				Paths.get(lastModifiedFile.getParent() + File.separator + currentDateForFileName + ".txt"),
				StandardCopyOption.REPLACE_EXISTING).toFile();
		// Using finalFile object for reading the contents in UTF-8 charset mode
		List<String> lines = Files.readAllLines(Paths.get(finalFile.getAbsolutePath()).toAbsolutePath(),
				StandardCharsets.UTF_8);
		// Setting the first line in the file with current date and time
		lines.set(0, currentDateTime);
		// Iterating over all lines and storing in newLine list of Strings
		for (String line : lines) {
			newLines.add(line);
		}
		// Writing the newlines back to the file having modified first line
		Files.write(Paths.get(finalFile.getAbsolutePath()).toAbsolutePath(), newLines, StandardCharsets.UTF_8);

	}
}