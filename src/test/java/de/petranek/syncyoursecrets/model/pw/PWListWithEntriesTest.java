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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;

import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWListWithEntriesTest a PWList containing PWEntries. It creates a
 * full PWList and checks, if the PWList contains the expected properties and
 * child nodes.
 *
 * @author Jan Petranek
 */
public class PWListWithEntriesTest {

	/** The properties for the first entry. */
	private Map<String, String> firstProperties = new HashMap<String, String>();

	/** The properties for an entry with special characters. */
	private Map<String, String> propsSpecialchars = new HashMap<String, String>();

	/** The properties for an entry that is only partly filled, i.e. some properties are left empty. */
	private Map<String, String> propsPartlyFilled = new HashMap<String, String>();

	/** The properties of a long entry. */
	private Map<String, String> propsLong = new HashMap<String, String>();

	/** The properties for a deleted entry. */
	private Map<String, String> propsDeleted = new HashMap<String, String>();

	/** The testfile. */
	private File testfile;

	/** The Constant DELETED_ID. */
	private static final long DELETED_ID = 4711L;

	/**
	 * Sets the properties up.
	 *
	 * @throws java.lang.Exception 	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		testfile = FileLocationHelperModel.getFile("tmp/pwListWithEntries.xml");
		firstProperties.put(PWTestHelper.NAME, "sourceforge account");
		firstProperties.put(PWTestHelper.PASSWORD, "Top secret");
		firstProperties.put(PWTestHelper.USER, "myaccountname");
		firstProperties.put(PWTestHelper.URL, "https://sourceforge.net/");
		firstProperties.put(PWTestHelper.REMARK,
				"Of course, this is not my real account password");
		firstProperties.put(PWTestHelper.EMAIL,
				"jpetranek@users.sourceforge.net");

		propsSpecialchars
				.put(PWTestHelper.NAME,
						"='ddd'&?!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		propsSpecialchars.put(PWTestHelper.PASSWORD, "<!--$?-->");
		propsSpecialchars
				.put(PWTestHelper.USER,
						"!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		propsSpecialchars.put(PWTestHelper.URL, "<pwentry>stuff</pwentry>");
		propsSpecialchars.put(PWTestHelper.REMARK,
				"See if you can get along with special characters");
		propsSpecialchars.put(PWTestHelper.EMAIL, "home.sweet@127.0.0.1");

		propsPartlyFilled.put(PWTestHelper.NAME, "='Some Name");
		propsPartlyFilled.put(PWTestHelper.PASSWORD, "secret");
		propsPartlyFilled.put(PWTestHelper.USER, "");
		propsPartlyFilled.put(PWTestHelper.URL, "");
		propsPartlyFilled.put(PWTestHelper.EMAIL, "");
		propsPartlyFilled.put(PWTestHelper.REMARK, "work with empty entries");

		propsLong.put(PWTestHelper.NAME, "An entry with a long remark");
		propsLong.put(PWTestHelper.PASSWORD, "somepassword");
		propsLong.put(PWTestHelper.USER, "someuser");
		propsLong.put(PWTestHelper.URL,
				"http://www.sourceforge.net/syncyoursecrets");
		propsLong.put(PWTestHelper.EMAIL, "");
		propsLong
				.put(
						PWTestHelper.REMARK,
						"You have a desktop at work, another at home, a laptop at"
								+ " your coach and a netbook in your pocket. You"
								+ "tried to use a password safe application, installed "
								+ "an run it on each machine. While surfing at home, you"
								+ "register your brand new sourceforge account. At work, "
								+ "company policy asks you to change your passwords."
								+ "But how can you synchronize all your modified password"
								+ " data between all your devices? With SyncYourSecrets, of course ;-)"
								+ "SyncYourSecrets stores your passwords in an XML-File and"
								+ "puts a timestamp on your password entries. These "
								+ "timestamps allow SyncYourSecrets to determine, "
								+ "which entry has been updated and to merge it with an older "
								+ "existing entry. The resulting XML is encrypted using"
								+ "symmetric cryptography, based on your password.");

		propsDeleted.put(PWTestHelper.NAME, "='Some Name");
		propsDeleted.put(PWTestHelper.PASSWORD, "secret");
		propsDeleted.put(PWTestHelper.USER, "");
		propsDeleted.put(PWTestHelper.URL, "");
		propsDeleted.put(PWTestHelper.EMAIL, "");
		propsDeleted.put(PWTestHelper.REMARK, "work with empty entries");
	}

	/**
	 * Tear down.
	 *
	 * @throws java.lang.Exception 	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		if (testfile.exists()) {
			testfile.delete();
		}
	}

	/**
	 * Tests a round trip: Create the PWList, fill it and check the contents.
	 *
	 * @throws Exception when the test fails.
	 */
	@Test
	public void testRoundTrip() throws Exception {
		Document docOut = toXml();
		XmlSerializeTool.writeFile(docOut, testfile);

		Document docin = XmlSerializeTool.readFile(testfile);
		fillFromXml(docin);
	}

	/**
	 * Parses a file from version v001, this is a regression test. If this test
	 * fails, we are no longer able to read files from this version!!!
	 *
	 * @throws Exception when the test fails.
	 */
	@Test
	public void testRegressionV001() throws Exception {
		File v001 = FileLocationHelperModel
				.getFile("regression/pwListWithEntriesV001.xml");
		Document docin = XmlSerializeTool.readFile(v001);
		fillFromXml(docin);
	}

	/**
	 * Tests reading in a file with special characters.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSpecialCharacters() throws Exception {
		File lb = FileLocationHelperModel.getFile("pwListWithLinebreaks.xml");
		Document docin = XmlSerializeTool.readFile(lb);
		fillFromXml(docin);
	}

	/**
	 * Creates a XML document filled with our PWList.
	 *
	 * @return the document
	 *
	 * @throws Exception the exception
	 */
	public Document toXml() throws Exception {
		Document doc = XmlSerializeTool.createDocument();
		PWList list = new PWList();

		PWEntry firstEntry = PWTestHelper.createEntry(firstProperties);
		PWEntry secondEntry = PWTestHelper.createEntry(propsSpecialchars);
		PWEntry thirdEntry = PWTestHelper.createEntry(propsPartlyFilled);
		PWEntry fourthEntry = PWTestHelper.createEntry(propsLong);
		PWEntry deletedEntry = PWTestHelper.createEntry(propsDeleted);

		// set the ID of the deleted Entry, so we can identify it later.
		deletedEntry.setId(DELETED_ID);
		list.add(firstEntry);
		list.add(secondEntry);
		list.add(thirdEntry);
		list.add(fourthEntry);
		list.add(deletedEntry);

		// remove the entry to delete.
		list.remove(deletedEntry);

		Element root = list.toXml(doc);

		doc.appendChild(root);

		return doc;

	}

	/**
	 * Parse the xml and check the properties.
	 *
	 * @param doc the document to parse
	 *
	 * @throws Exception the exception, when the test fails.
	 */
	public void fillFromXml(Document doc) throws Exception {

		Element root = doc.getDocumentElement();
		PWList list = new PWList(root, null);
		assertEquals("Name on list checked", "pwlist", list.getElementName());

		Iterator<MappingElement> it = list.getElements().values().iterator();

		int foundcount = 0; // check all elements are retrieved, no
		// matter in which order.
		while (it.hasNext()) {
			PWEntry current = (PWEntry) it.next();
			if (!current.isDeleted()
					&& current.getName() != null
					&& current.getName().equals(
							firstProperties.get(PWTestHelper.NAME))) {
				PWTestHelper.checkEntry(firstProperties, current);
				foundcount++;
			}
			if (!current.isDeleted()
					&& current.getName() != null
					&& current.getName().equals(
							propsSpecialchars.get(PWTestHelper.NAME))) {
				PWTestHelper.checkEntry(propsSpecialchars, current);
				foundcount++;
			}
			if (!current.isDeleted()
					&& current.getName() != null
					&& current.getName().equals(
							propsPartlyFilled.get(PWTestHelper.NAME))) {
				PWTestHelper.checkEntry(propsPartlyFilled, current);
				foundcount++;
			}

			if (!current.isDeleted()
					&& current.getName() != null
					&& current.getName().equals(
							propsLong.get(PWTestHelper.NAME))) {
				PWTestHelper.checkEntry(propsLong, current);
				foundcount++;
			}
			// the deleted element must be identified by its id, as it
			// has no name.
			if (current.getId() == DELETED_ID) {
				PWTestHelper.checkDeleted(current);
				foundcount++;
			}
		}
		assertEquals("Number of found elements should match", 5, foundcount);
	}

}
