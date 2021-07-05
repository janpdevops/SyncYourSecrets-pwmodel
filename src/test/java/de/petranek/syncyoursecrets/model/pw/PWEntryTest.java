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

import java.util.HashMap;
import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;

// TODO: Auto-generated Javadoc
/**
 * The Class PWEntryTest performs a roundtrip test for a PWEntry. First, the
 * entry is created from a prpperty map and serialized into an XML Document.
 * Afterwards, it is parsed in from the XML Document and its properties are
 * verified.
 *
 * @author Jan Petranek
 */
public class PWEntryTest {

	/** The properties for our entry. The PWEntry will be created with these values and later we check the values. */
	private Map<String, String> testEntryProperties = new HashMap<String, String>();

	/**
	 * Sets the test properties up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		testEntryProperties.put(PWTestHelper.NAME, "sourceforge account");
		testEntryProperties.put(PWTestHelper.PASSWORD, "Top secret");
		testEntryProperties.put(PWTestHelper.USER, "myaccountname");
		testEntryProperties.put(PWTestHelper.URL, "https://sourceforge.net/");
		testEntryProperties.put(PWTestHelper.REMARK,
				"Of course, this is not my real account password");
		testEntryProperties.put(PWTestHelper.EMAIL,
				"jpetranek@users.sourceforge.net");
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Read the PWEntry in from xml and check its content against our property
	 * map.
	 *
	 * @param doc the document containing our PWEntry
	 *
	 * @throws Exception the exception
	 */
	public void fillFromXml(Document doc) throws Exception {

		Element root = doc.getDocumentElement();

		PWEntry pwEntry = new PWEntry(root, null);
		PWTestHelper.checkEntry(testEntryProperties, pwEntry);

	}

	/**
	 * Create an XML document containing a single PWEntry corresponding to our
	 * properties.
	 *
	 * @return the document
	 *
	 * @throws Exception the exception
	 */
	public Document testToXml() throws Exception {
		Document doc = XmlSerializeTool.createDocument();

		PWEntry pwEntry = PWTestHelper.createEntry(testEntryProperties);

		Element node = pwEntry.toXml(doc);

		doc.appendChild(node);
		return doc;

	}

	/**
	 * Makes a round trip test.
	 *
	 * @throws Exception when the test fails.
	 */
	@Test
	public void testRoundTrip() throws Exception {
		Document doc = testToXml();
		fillFromXml(doc);
	}
}
