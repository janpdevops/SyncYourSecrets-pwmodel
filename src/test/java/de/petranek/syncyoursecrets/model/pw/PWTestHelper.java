/**
 * SyncYourSecrets-pwmodel ties the generic MappingElements from the
 * xmlbase to a concrete structure, suitable for password entries.
 *
 *
 *    Copyright 2008 Jan Petranek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.petranek.syncyoursecrets.model.pw;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.Map;



import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.util.DateTimeUtil;
import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.SysParseException;
import de.petranek.syncyoursecrets.util.SysXmlBaseException;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWTestHelper contains common helper methods for testing the
 * PWModel. Instead of creating PWEntry objects and filling it with properties,
 * only to check these properties later on, you can use maps of properties.
 * 
 * PWTestHelper lets you create a corresponding PWEntry with these entries and
 * compares the properties of a PWEntry with your map. This saves us the effort
 * of re-typing certain properties.
 * 
 * @author Jan Petranek
 */
public final class PWTestHelper {

	/**
	 * Utility class, shall not be instantiated.
	 */
	private PWTestHelper() {
		super();
	}

	/** The Constant URL. */
	public static final String URL = "url";

	/** The Constant USER. */
	public static final String USER = "user";

	/** The Constant PASSWORD. */
	public static final String PASSWORD = "password";

	/** The Constant NAME. */
	public static final String NAME = "name";

	/** The Constant REMARK. */
	public static final String REMARK = "remark";

	/** The Constant EMAIL. */
	public static final String EMAIL = "email";

	/**
	 * Creates an PWEntry from the given map of properties. The map should
	 * contain entries for all constants in this class.
	 * 
	 * @param properties the properties
	 * 
	 * @return the pW entry
	 * 
	 * @throws Exception the exception when the implementation of this method has gone totally foul.
	 */
	public static PWEntry createEntry(Map<String, String> properties)
			throws Exception {
		PWEntry pwEntry = new PWEntry(null);
		pwEntry.setName(properties.get(NAME));
		pwEntry.setPassword(properties.get(PASSWORD));
		pwEntry.setRemark(properties.get(REMARK));
		pwEntry.setUrl(properties.get(URL));
		pwEntry.setUser(properties.get(USER));
		pwEntry.setEmail(properties.get(EMAIL));
		return pwEntry;
	}

	/**
	 * Checks if the properties of the PWEntry are equal to the properties in
	 * the corresponding map.
	 * 
	 * @param properties the properties
	 * @param pwEntry the pw entry
	 * 
	 * @throws ElementDeletedException , when the element has been deleted instead.
	 */
	public static void checkEntry(Map<String, String> properties,
			PWEntry pwEntry) throws ElementDeletedException {
		assertEquals("Checking  " + NAME + " on pwEntry " + pwEntry, properties
				.get(NAME), pwEntry.getName());
		assertEquals("Checking  " + PASSWORD + " on pwEntry " + pwEntry,
				properties.get(PASSWORD), pwEntry.getPassword());
		assertEquals("Checking  " + REMARK + " on pwEntry " + pwEntry,
				properties.get(REMARK), pwEntry.getRemark());
		assertEquals("Checking  " + URL + " on pwEntry " + pwEntry, properties
				.get(URL), pwEntry.getUrl());
		assertEquals("Checking  " + USER + " on pwEntry " + pwEntry, properties
				.get(USER), pwEntry.getUser());
		assertEquals("Checking  " + EMAIL + " on pwEntry " + pwEntry,
				properties.get(EMAIL), pwEntry.getEmail());
	}

	/**
	 * Check if an entry has been deleted.
	 * 
	 * @param pwEntry the pw entry
	 */
	public static void checkDeleted(PWEntry pwEntry) {
		assertEquals("Last Action must be delete",
				MappingElement.ACTIONS.DELETE, pwEntry.getLastAction());
	}

	/**
	 * Gets the old time stamp.
	 * 
	 * @return the Old time Stamp
	 * 
	 * @throws SysParseException the sys parse exception
	 */
	public static ZonedDateTime getOldTimeStamp() throws SysParseException {
		return DateTimeUtil.parseDateTime("2008-09-21T15:51:30.346+02:00");
	}

	/**
	 * Gets the updated time stamp.
	 * 
	 * @return a time stamp after old, but before newest
	 * 
	 * @throws SysParseException the sys parse exception
	 */
	public static ZonedDateTime getUpdatedTimeStamp() throws SysParseException {
		return DateTimeUtil.parseDateTime("2008-09-21T16:51:30.346+02:00");
	}

	/**
	 * Gets the newest time stamp.
	 * 
	 * @return a time stamp after old and updated.
	 * 
	 * @throws SysParseException the sys parse exception
	 */
	public static ZonedDateTime getNewestTimeStamp() throws SysParseException {
		return DateTimeUtil.parseDateTime("2008-09-22T00:00:00.000+02:00");

	}

	/**
	 * Load pw list.
	 * 
	 * @param fileName the file name
	 * 
	 * @return the PW list
	 * 
	 * @throws SysXmlBaseException the sys xml base exception
	 * @throws SysParseException the sys parse exception
	 * @throws SysInvalidArgumentException the sys invalid argument exception
	 */
	public static PWList loadPwList(String fileName)
			throws SysXmlBaseException, SysParseException,
			SysInvalidArgumentException {
		File oldFile = new File(fileName);
		Document doc = XmlSerializeTool.readFile(oldFile);

		Element root = doc.getDocumentElement();
		PWList list = new PWList(root, null);
		assertEquals("Name on list checked", "pwlist", list.getElementName());
		return list;
	}

}
