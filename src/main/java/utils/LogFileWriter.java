package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import constants.GameConstants;

/**
 * Responsible for writing log entries to a log file.
 */
public class LogFileWriter implements Observer {

    /**
     * Constructs a LogFileWriter and attaches it to the given Observable.
     *
     * @param p_observable The Observable to attach to.
     */
	public LogFileWriter(Observable p_observable) {
		p_observable.attach(this);
	}

	/**
     * Updates the LogFileWriter with new log entries from the Observable.
     *
     * @param p_observable The Observable object providing updates.
     */
	@Override
	public void update(Observable p_observable) {
		writeLogfile((LogEntryBuffer) p_observable);
	}

	/**
     * Writes log entries to the log file.
     *
     * @param p_logEntryBuffer The LogEntryBuffer containing log entries to write.
     */
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