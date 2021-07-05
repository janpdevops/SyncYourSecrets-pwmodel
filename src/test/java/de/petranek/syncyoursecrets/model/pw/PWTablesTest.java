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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sourceforge.syncyoursecrets.model.testmodel.TestTableEntry;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.model.pw.PWTable;
import de.petranek.syncyoursecrets.model.pw.PWTableEntry;
import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.XmlSerializeTool;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWTablesTest makes a round trip test: First, two tables PWTables
 * are populated and added to the PWList. One of these tables is deleted.
 *
 * Later, we check the expected values against what we received from the XML.
 *
 * @author Jan Petranek
 */
public class PWTablesTest {

	/** The expected data of the first table. */
	private Map<Long, TestTableEntry> firstTable = new HashMap<Long, TestTableEntry>();

	/** The expected data of a deleted table. */
	private Map<Long, TestTableEntry> deletedTable = new HashMap<Long, TestTableEntry>();

	/** The Constant FIRST_TABLE_NAME. */
	private static final String FIRST_TABLE_NAME = "First table";

	/** The Constant DELETED_TABLE_NAME. */
	private static final String DELETED_TABLE_NAME = "Deleted table";

	/** The test tables. */
	private Map<Long, Map<Long, TestTableEntry>> testTables = new HashMap<Long, Map<Long, TestTableEntry>>();

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

		/*
		 * Create the first table and populate it with entries:
		 */
		PWTable table = new PWTable(null);
		table.setName(FIRST_TABLE_NAME);

		addTableEntry(table, "123", "Hello", false, firstTable);
		addTableEntry(table, "Please somebody kill me", "delete me", true,
				firstTable);
		addTableEntry(table, "#3poüa43", "sjipoöejü43ojhipoasut04a43\43[]äöü",
				false, firstTable);

		testTables.put(table.getId(), firstTable);
		list.add(table);

		/*
		 * Create the another table and populate it and delete it:
		 */
		PWTable pwTableDeleted = new PWTable(null);
		pwTableDeleted.setName(DELETED_TABLE_NAME);

		addTableEntry(pwTableDeleted, "5555", "Hurtz!", false, deletedTable);
		addTableEntry(pwTableDeleted, "Oiedipos", "delete me daddy", true,
				deletedTable);
		addTableEntry(pwTableDeleted, "#&&&",
				"sjipoöejü43ojhipoasut04a43\43[]äöü", false, deletedTable);

		testTables.put(pwTableDeleted.getId(), deletedTable);
		list.add(pwTableDeleted);

		list.remove(pwTableDeleted);

		Element node = list.toXml(doc);

		doc.appendChild(node);
		return doc;

	}

	/**
	 * Adds the table entry.
	 *
	 * @param pwTable the pw table
	 * @param name the name
	 * @param content the content
	 * @param delete the delete
	 * @param testModellTable the test modell table
	 *
	 * @throws SysInvalidArgumentException the sys invalid argument exception
	 * @throws ElementDeletedException the element deleted exception
	 */
	private void addTableEntry(PWTable pwTable, String name, String content,
			boolean delete, Map<Long, TestTableEntry> testModellTable)
			throws SysInvalidArgumentException, ElementDeletedException {
		TestTableEntry testEntry = new TestTableEntry(name, content);
		PWTableEntry tableEntry = createPwTableEntry(testEntry);

		testModellTable.put(testEntry.getId(), testEntry);

		pwTable.add(tableEntry);
		if (delete) {
			pwTable.remove(tableEntry);
			testEntry.setDeleted(true);
			testEntry.setContent("");
			testEntry.setName(null);
		}
	}

	/**
	 * Creates the pw table entry.
	 *
	 * @param testEntry the test entry
	 *
	 * @return the PW table entry
	 *
	 * @throws SysInvalidArgumentException the sys invalid argument exception
	 * @throws ElementDeletedException the element deleted exception
	 */
	private PWTableEntry createPwTableEntry(TestTableEntry testEntry)
			throws SysInvalidArgumentException, ElementDeletedException {
		PWTableEntry tableEntry = new PWTableEntry(null);
		tableEntry.setName(testEntry.getName());
		testEntry.setId(tableEntry.getId());
		tableEntry.setContent(testEntry.getContent());

		return tableEntry;
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
		assertEquals("Name on list checked", "pwlist", list.getElementName());

		// loop over all child tables
		Iterator<MappingElement> it = list.getElements().values().iterator();

		int foundCount = 0;
		while (it.hasNext()) {
			MappingElement me = it.next();
			if (me instanceof PWTable) {
				PWTable table = (PWTable) me;
				Map<Long, TestTableEntry> testTable = testTables.get(table
						.getId());

				if (table.isDeleted()) {
					checkPwTableDeleted(table);

					foundCount++;
				} else {
					assertEquals("Name on Table checked", FIRST_TABLE_NAME,
							table.getName());
					checkPwTable(table, testTable);
					foundCount++;
				}
			}

		}
		assertEquals("Tables found", 2, foundCount);

	}

	/**
	 * Check pw table deleted.
	 *
	 * @param table the table
	 */
	private void checkPwTableDeleted(PWTable table) {
		assertEquals("Deleted table has no children", 0, table.getElements()
				.values().size());
		assertEquals("Name should be deleted", null, table.getName());

	}

	/**
	 * Check the contents of a PW table.
	 *
	 * @param table the table
	 * @param testTable the test table
	 *
	 * @throws Exception the exception
	 */
	private void checkPwTable(PWTable table, Map<Long, TestTableEntry> testTable)
			throws Exception {
		assertEquals("Number of children should match", testTable.size(), table
				.getElements().values().size());
		Iterator<MappingElement> it = table.getElements().values().iterator();

		while (it.hasNext()) {
			PWTableEntry current = (PWTableEntry) it.next();

			Long id = current.getId();

			TestTableEntry testEntry = testTable.get(id);
			assertTrue("testEntry should not be null", testEntry != null);
			assertEquals("Checking names", testEntry.getName(), current
					.getName());

			assertEquals("Checking deleted", testEntry.isDeleted(), current
					.isDeleted());

			if (!testEntry.isDeleted()) {
				// check content only, if not deleted yet.
				assertEquals("Checking content", testEntry.getContent(),
						current.getContent());
			}

		}
	}
}
