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

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWTableEntry;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;

// TODO: Auto-generated Javadoc
/**
 * The Class PWTableEntryTest performs a round trip test for the PWTableEntry.
 * 
 * @author Jan Petranek
 */
public class PWTableEntryTest {

	/** The Constant TEST_CONTENT. */
	private static final String TEST_CONTENT = "Test Content";

	/** The Constant TEST_NAME. */
	private static final String TEST_NAME = "Entry Name";

	/**
	 * Create an XML document containing a single PWTableEntry.
	 * 
	 * @return the document
	 * 
	 * @throws Exception the exception
	 */
	public Document testToXml() throws Exception {
		Document doc = XmlSerializeTool.createDocument();

		PWTableEntry tEntry = new PWTableEntry(null);
		tEntry.setName(TEST_NAME);

		tEntry.setContent(TEST_CONTENT);

		Element node = tEntry.toXml(doc);

		doc.appendChild(node);
		return doc;

	}

	/**
	 * Read the PWTableEntry in from xml and check its content.
	 * 
	 * @param doc the document containing our PWTableEntry
	 * 
	 * @throws Exception the exception
	 */
	public void fillFromXml(Document doc) throws Exception {

		Element root = doc.getDocumentElement();

		PWTableEntry tEntry = new PWTableEntry(root, null);

		// check name
		assertEquals("Checking name", TEST_NAME, tEntry.getName());
		// check content
		assertEquals("Checking content", TEST_CONTENT, tEntry.getContent());

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
