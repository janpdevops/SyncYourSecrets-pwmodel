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
import static org.junit.Assert.assertTrue;

import java.util.Iterator;


import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.model.pw.PWTable;
import de.petranek.syncyoursecrets.model.pw.PWTableEntry;
import de.petranek.syncyoursecrets.util.StringUtil;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWTableInListTest embeds a PWTable in a PWList and performs a
 * roundtrip test.
 * 
 * @author Jan Petranek
 */
public class PWTableInListTest {

	/** The Constant SECOND_CONTENT is a property for the second table. */
	private static final String SECOND_CONTENT = "Content of second entry";

	/** The Constant SECOND_NAME is a property for the second table.. */
	private static final String SECOND_NAME = "Second Name";

	/** The Constant FIRST_CONTENT is a property for the first table. */
	private static final String FIRST_CONTENT = "Content of first entry";

	/** The Constant FIRST_NAME is a property for the first table. */
	private static final String FIRST_NAME = "First Name";

	/** The Constant DELETED_CONTENT is a property for the first table. */
	private static final String DELETED_CONTENT = "Deleted content";

	/** The Constant DELETED_NAME is a property for the first table. */
	private static final String DELETED_NAME = "Will be deleted";

	/** The Constant DELETED_ID. */
	private static final long DELETED_ID = 4711L;

	/** The Constant TABLE_NAME. */
	private static final String TABLE_NAME = "TableName";

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

	/**
	 * Create an XML document containing a PWList with two PWTables.
	 * 
	 * @return the document
	 * 
	 * @throws Exception the exception
	 */
	public Document testToXml() throws Exception {
		Document doc = XmlSerializeTool.createDocument();

		PWList list = new PWList();

		PWTable table = new PWTable(null);
		table.setName(TABLE_NAME);
		PWTableEntry first = new PWTableEntry(null);
		first.setName(FIRST_NAME);
		first.setContent(FIRST_CONTENT);
		table.add(first);

		PWTableEntry second = new PWTableEntry(null);
		second.setName(SECOND_NAME);
		second.setContent(SECOND_CONTENT);
		table.add(second);

		PWTableEntry deleted = new PWTableEntry(null);
		deleted.setName(DELETED_NAME);
		deleted.setContent(DELETED_CONTENT);

		// set the ID of the deleted Entry, so we can identify it later.
		deleted.setId(DELETED_ID);
		table.add(deleted);

		table.remove(deleted);

		list.add(table);

		Element node = list.toXml(doc);

		doc.appendChild(node);
		return doc;

	}

	/**
	 * Read the XML back in and check the contents.
	 * 
	 * @param doc the doc
	 * 
	 * @throws Exception the exception
	 */
	public void fillFromXml(Document doc) throws Exception {

		Element root = doc.getDocumentElement();

		PWList list = new PWList(root, null);
		PWTable table = (PWTable) list.iterator().next();

		assertEquals("Name on Table checked", TABLE_NAME, table.getName());

		Iterator<MappingElement> it = table.getElements().values().iterator();

		int foundCount = 0;
		while (it.hasNext()) {
			PWTableEntry entry = (PWTableEntry) it.next();

			if (FIRST_NAME.equals(entry.getName())) {
				assertEquals("Checking first element", FIRST_CONTENT, entry
						.getContent());
				foundCount++;
			}
			if (SECOND_NAME.equals(entry.getName())) {
				assertEquals("Checking second element", SECOND_CONTENT, entry
						.getContent());
				foundCount++;
			}
			// the deleted element must be identified by its id, as it
			// has no name.
			if (entry.getId() == DELETED_ID) {
				assertTrue("Table entry should be deleted", entry.isDeleted());
				assertTrue("Name of deleted element should be empty",
						StringUtil.isEmpty(entry.getName()));
				assertEquals("Content from deleted object", null, entry
						.getContent());
				foundCount++;
			}
		}
		assertEquals("Expected 3 child elements", 3, foundCount);
	}
}
