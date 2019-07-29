/**
 * 
 */
package io.crs.mws.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * @author robi
 *
 */
public class CsvUtils {

	/**
	 * jackson-dataformat-csv
	 * 
	 * @param <T>
	 * @param clazz
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
		CsvMapper mapper = new CsvMapper();

		CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true).withColumnSeparator(';');
		ObjectReader reader = mapper.readerFor(clazz).with(schema);
		return reader.<T>readValues(stream).readAll();
	}
}
