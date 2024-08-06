package fa.training.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtil {
	public static List<String> readFile(String readPath) {
		try {
			return Files.readAllLines(Paths.get(readPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeFile(String writePath, String content) {
		try {
			Files.write(Paths.get(writePath), content.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("KHONG THE GHI FILE");
			e.printStackTrace();
		}
	}
}
