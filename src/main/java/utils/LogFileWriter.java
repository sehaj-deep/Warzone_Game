package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import constants.GameConstants;

public class LogFileWriter implements Observer {

	public LogFileWriter(Observable p_observable) {
		p_observable.attach(this);
	}

	@Override
	public void update(Observable p_observable) {
		writeLogfile((LogEntryBuffer) p_observable);
	}

	private void writeLogfile(LogEntryBuffer p_logEntryBuffer) {
		PrintWriter l_printWriter = null;

		try {
			FileOutputStream l_fileOutputStream = new FileOutputStream(
					new File(GameConstants.SRC_MAIN_RESOURCES + GameConstants.LOG_FILE), true);

			l_printWriter = new PrintWriter(l_fileOutputStream);
//			l_printWriter = new PrintWriter(
//					new FileOutputStream(GameConstants.SRC_MAIN_RESOURCES + GameConstants.LOG_FILE), true);

			l_printWriter.println(p_logEntryBuffer.getD_buffer());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			l_printWriter.close();
		}
	}
}