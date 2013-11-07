package group8.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains various utility methods that may be used on lists of
 * objects.
 * 
 * @author Natacha Gabbamonte
 * @author Kim Parisé
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 12/10/2011
 */

public class ListUtilities {

	/**
	 * No instances of this class may be created.
	 */
	private ListUtilities() {
	}

	/**
	 * This method merges two arrays of Comparable objects into one array. In
	 * case of duplicates, the entry from the first array is merged, then both
	 * entries are written in a file (the one that was merged has the "(merged)"
	 * annotation next to it.
	 * 
	 * Precondition: Both arrays are sorted and are not null. The file name is
	 * not null. The file has the correct permissions and the directory exists.
	 * 
	 * Postcondition: The returned array contains all the entries from list1 and
	 * all the entries from list2 that are not considered duplicates. The result
	 * is sorted. A file will be created or entries will be appended depending
	 * if the file name already exists.
	 * 
	 * @param list1
	 *            The first array (which has priority).
	 * @param list2
	 *            The second array.
	 * @param duplicateFileName
	 *            The name of the file for the duplicate entries.
	 * @return the array of merged objects.
	 * @throws IOException
	 *             if the directory of the file does not exist or if the file
	 *             does not have the correct permissions.
	 * @throws IllegalArgumentException
	 *             if any parameter is null.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Comparable[] merge(Comparable[] list1, Comparable[] list2,
			String duplicateFileName) throws IOException {

		// If a parameter is null, it sends an IllegalArgumentException.
		if (list1 == null || list2 == null || duplicateFileName == null)
			throw new IllegalArgumentException(
					"The parameters must not be null.");

		int maxLength = list1.length + list2.length;
		Comparable[] mergedList = new Comparable[maxLength];
		String[] duplicateList = new String[maxLength];
		int indexL1 = 0, indexL2 = 0, indexML = 0, indexDL = 0;

		// As long as no list has reached the end.
		while (indexL1 < list1.length && indexL2 < list2.length) {

			// To know which record is greater
			int compared = list1[indexL1].compareTo(list2[indexL2]);
			if (compared < 0) {
				mergedList[indexML] = list1[indexL1];
				indexL1++;

			} else if (compared > 0) {
				mergedList[indexML] = list2[indexL2];
				indexL2++;

			} else {
				mergedList[indexML] = list1[indexL1];
				duplicateList[indexDL] = list1[indexL1].toString()
						+ " (merged)";
				indexDL++;
				duplicateList[indexDL] = list2[indexL2].toString();
				indexDL++;
				indexL1++;
				indexL2++;
			}
			indexML++;
		}// End while

		// If list1 has reached the end
		if (indexL1 == list1.length) {
			// If list2 hasn't reached the end, we add it to mergedList
			if (indexL2 < list2.length) {
				indexML = addList(list2, mergedList, indexL2, indexML);
			}
		}
		// If list1 hasn't reached the end, list2 has, so we add list1 to
		// mergedList.
		else {
			indexML = addList(list1, mergedList, indexL1, indexML);
		}

		if (indexML < mergedList.length)
			mergedList = Arrays.copyOf(mergedList, indexML);

		if (indexDL < duplicateList.length)
			duplicateList = Arrays.copyOf(duplicateList, indexDL);

		save(duplicateList, duplicateFileName, true);

		return mergedList;
	}

	/**
	 * Saves a string representation of the objects in a list to a sequential
	 * text file.
	 * 
	 * @param list
	 *            a list of objects.
	 * 
	 * @param filename
	 *            the output file specification.
	 * 
	 * @param boolean if true, records will be appended to the end of the file.
	 * 
	 * @throws SecurityException
	 *             if the file doesn’t permit write access.
	 * 
	 * @throws IOException
	 *             if an error occurs while trying to create or open the
	 *             specified file.
	 */

	public static void save(Object[] list, String filename, boolean append)
			throws IOException {
		PrintWriter outputFile = null;
		try {
			FileOutputStream f = new FileOutputStream(filename, append);
			OutputStreamWriter out = new OutputStreamWriter(f,
					System.getProperty("file.encoding"));
			outputFile = new PrintWriter(new BufferedWriter(out));

			for (Object obj : list)
				if (obj != null)
					outputFile.println(obj);
		} catch (FileNotFoundException e) {
			throw new IOException(
					"Error saving list! Unable to access the device "
							+ filename);
		}

		catch (SecurityException se) {
			throw new SecurityException(
					"The file does not have writtable permissions.");
		} finally {
			if (outputFile != null)
				outputFile.close();
		}
	}

	/**
	 * Sort a list of Comparable objects in ascending natural order.
	 * 
	 * Precondition: Assumes that the list is not null, it contains at least one
	 * valid entry, and that any null entries are situated at the end of the
	 * list.
	 * 
	 * @param list
	 *            A list of comparable objects.
	 * @throws ClassCastException
	 *             if the array contains entries that are not mutually
	 *             comparable (e.g. trying to compare Dog objects to Employee
	 *             objects).
	 * @throws IllegalArgumentException
	 *             if the list is null
	 * 
	 * @throws NullPointerException
	 *             if there is a null entry in between valid entries.
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the list does not contain at least one entry.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sort(Comparable[] list) {

		int size;

		if (list == null)
			throw new IllegalArgumentException(
					"The parameter should not be null.");

		size = list.length;
		// If the list does not contain at least one entry, it will throw an
		// ArrayIndexOutOfBoundsException
		if (size > 0)
			while (list[size - 1] == null)
				size--;
		int minIndex;
		Comparable minObject;

		int outerLoopSize = size - 1;

		for (int pass = 0; pass < outerLoopSize; pass++) {
			minIndex = pass;

			for (int index = pass + 1; index < size; index++) {
				// If one of the entries is null, it will throw a
				// NullPointerException
				if (list[index].compareTo(list[minIndex]) < 0)
					minIndex = index;
			}// end of inner loop

			// swap positions
			minObject = list[minIndex];
			list[minIndex] = list[pass];
			list[pass] = minObject;
		}// end of outer loop
	}

	public static void saveListToTextFile(Object[] objects, String filename)
			throws FileNotFoundException, UnsupportedEncodingException {

		saveListToTextFile(objects, filename, false,
				System.getProperty("file.encoding"));

	}

	public static void saveListToTextFile(Object[] objects, String filename,
			boolean append) throws FileNotFoundException,
			UnsupportedEncodingException {

		saveListToTextFile(objects, filename, append,
				System.getProperty("file.encoding"));
	}

	public static void saveListToTextFile(Object[] objects, String filename,
			boolean append, String encoding) throws FileNotFoundException,
			UnsupportedEncodingException {

		PrintWriter outputFile = null;
		try {
			FileOutputStream f = new FileOutputStream(filename, append);
			OutputStreamWriter out = new OutputStreamWriter(f, encoding);
			outputFile = new PrintWriter(new BufferedWriter(out));

			for (Object obj : objects)
				if (obj != null)
					outputFile.println(obj);

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					"Error saving list! Unable to access the device "
							+ filename);

		} finally {
			if (outputFile != null)
				outputFile.close();
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param objects
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static <T extends Object> void saveListToTextFile(List<T> objects,
			String filename) throws FileNotFoundException,
			UnsupportedEncodingException {
		saveListToTextFile(objects, filename, false,
				System.getProperty("file.encoding"));
	}

	/**
	 * 
	 * @param <T>
	 * @param objects
	 * @param filename
	 * @param append
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static <T extends Object> void saveListToTextFile(List<T> objects,
			String filename, boolean append) throws FileNotFoundException,
			UnsupportedEncodingException {
		saveListToTextFile(objects, filename, append,
				System.getProperty("file.encoding"));
	}

	/**
	 * 
	 * @param <T>
	 * @param objects
	 * @param filename
	 * @param append
	 * @param encoding
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static <T extends Object> void saveListToTextFile(List<T> objects,
			String filename, boolean append, String encoding)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter outputFile = null;
		try {
			FileOutputStream f = new FileOutputStream(filename, append);
			OutputStreamWriter out = new OutputStreamWriter(f, encoding);
			outputFile = new PrintWriter(new BufferedWriter(out));

			for (Object obj : objects)
				if (obj != null)
					outputFile.println(obj);

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					"Error saving list! Unable to access the device "
							+ filename);

		} finally {
			if (outputFile != null)
				outputFile.close();
		}
	}

	/*
	 * This method adds a the rest of list1 to the end of list2.
	 * 
	 * @param list1 the array that will be added to the other array.
	 * 
	 * @param list2 the array that will be modified.
	 * 
	 * @param indexL1 the index position of the first list.
	 * 
	 * @param indexL2 the index position of the second list.
	 * 
	 * @return the new index position of the second list.
	 */
	@SuppressWarnings({ "rawtypes" })
	private static int addList(Comparable[] list1, Comparable[] list2,
			int indexL1, int indexL2) {
		while (indexL1 < list1.length) {
			list2[indexL2] = list1[indexL1];
			indexL1++;
			indexL2++;
		}

		return indexL2;
	}

	/**
	 * 
	 * @param <T>
	 * @param list
	 * @param key
	 * @return
	 */

	public static <T extends Comparable<? super T>> int binarySearch(
			List<T> list, T key) {

		int low = 0, high = list.size() - 1;
		int mid, result;

		while (high >= low) {
			mid = (high + low) / 2;
			result = list.get(mid).compareTo(key);
			if (result > 0) {
				high = mid - 1;
			} else if (result == 0) {
				return mid;
			} else
				low = mid + 1;
		}

		return -(low + 1);
	}
}
