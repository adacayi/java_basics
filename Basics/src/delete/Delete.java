package delete;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.*;
import delete2.*;

@SuppressWarnings("unused")
public class Delete {
	public static void main(String[] args) {
		LocalTime time = LocalTime.now();
		System.out.println("Time is " + time);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				BufferedOutputStream bufferedWriter = new BufferedOutputStream(out);
				ObjectOutputStream writer = new ObjectOutputStream(bufferedWriter)) {
			writer.writeObject(time);
			writer.flush();
			try (ByteArrayInputStream in =new ByteArrayInputStream(out.toByteArray());
					BufferedInputStream bufferedReader = new BufferedInputStream(in);
					ObjectInputStream reader = new ObjectInputStream(bufferedReader)) {
				LocalTime newTime = (LocalTime) reader.readObject();
				System.out.println("Clone is " + newTime);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}