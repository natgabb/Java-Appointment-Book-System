package group8.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class contains various utility methods.
 * 
 * @author Natacha Gabbamonte
 * 
 */
public class Utilities {

	/**
	 * No instances of this class may be created.
	 */
	private Utilities() {
	}

	/**
	 * This method uses serialization to make a deep copy of an object.
	 * 
	 * @param <T>
	 *            the object type which extends Serializable.
	 * @param obj
	 *            the object.
	 * @return a deep copy of the object.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copyOf(T obj) {
		try {
			// Serializing
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteArrayOut);
			objOut.writeObject(obj);
			objOut.close();

			// Deserializing
			ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(
					byteArrayOut.toByteArray());
			ObjectInputStream objIn = new ObjectInputStream(byteArrayIn);
			T deepCopy = (T) objIn.readObject();
			objIn.close();
			return deepCopy;
		} catch (Exception e) {
			// This shouldn't happen since T is Serializable
			return null;
		}
	}

	/**
	 * This method serializes an Object sent in.
	 * 
	 * @param object
	 *            the object to be serialized
	 * @param fileSpecification
	 *            the file that the serialized object will be stored in.
	 * @throws Exception
	 *             If an error occurs while serializing the object.
	 */
	public static void serializeObject(Object object, String fileSpecification)
			throws Exception {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(
					new FileOutputStream(fileSpecification));
			out.writeObject(object);
		} catch (Exception e) {
			throw new Exception("Error serializing object\n" + e.getMessage());
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * This method deserializes an Object sent in.
	 * 
	 * @param fileSpecification
	 *            The serialized file path
	 * @return the deserialized object
	 * @throws Exception
	 *             If an error occurs while deserializing objects
	 */
	public static Object deserializeObject(String fileSpecification)
			throws Exception {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(fileSpecification));

			Object obj = in.readObject();

			return obj;
		} catch (Exception e) {
			throw new Exception("Error deserializing object\n" + e.getMessage());
		} finally {
			if (in != null)
				in.close();
		}
	}

}
