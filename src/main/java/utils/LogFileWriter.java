package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import constants.GameConstants;

public class LogFileWriter implements Observer {

	private String fileName = GameConstants.LOG_FILE;

	@Override
	public void update(String p_message) {
		writeLogfile(p_message);
	}

	private void writeLogfile(String p_message) {
		PrintWriter l_printWriter = null;

		try {
			l_printWriter = new PrintWriter(
					new FileOutputStream(GameConstants.SRC_MAIN_RESOURCES + GameConstants.LOG_FILE));
			l_printWriter.println(p_message);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			l_printWriter.close();
		}

	}

}
