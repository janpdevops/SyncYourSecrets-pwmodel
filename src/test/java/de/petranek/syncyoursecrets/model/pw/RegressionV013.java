/**
 * SyncYourSecrets-pwmodel ties the generic MappingElements from the
 * xmlbase to a concrete structure, suitable for password entries.
 *
 *
 *    Copyright 2008-2009 Jan Petranek
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

import java.time.ZonedDateTime;
import java.util.Iterator;



import org.junit.Test;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.model.pw.PWTable;
import de.petranek.syncyoursecrets.model.pw.PWTableEntry;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class RegressionV013 is a regression test for version 0.1.3, it checks if
 * the files still can be understood.
 *
 * Version 0.1.3 still places the name property into an xml attribute, so we
 * must be sure to deal with that.
 *
 * @author Jan Petranek
 */
public class RegressionV013 {

	/** The Constant FILE_CONVERT_V1_TO_V2_XML. */
	private static final String FILE_CONVERT_V1_TO_V2_XML = "test/resources/regression/convert_v1_to_v2.xml";

	/** The Constant FILE_V0123. */
	private static final String FILE_V0123 = "test/resources/regression/regression_v013.xml";

	/**
	 * Test a file that has been generated with version 0.1.3, using the XML version 1.
	 * It has been decrypted, so we can easily test and view it.
	 * The content should still be readable.
	 *
	 * @throws Exception when I messed up.
	 */
	@Test
	public void testRegressionV013() throws Exception {
		PWList pwList = PWTestHelper.loadPwList(FILE_V0123);

		checkPwList(pwList);

		checkPwListChildren(pwList);

	}

	/**
	 * That file from the above test case has been re-imported into version 0.2.0 and re-exported
	 * with the new XML in version 2. The observable information has remained the same.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testConvertVersion1ToVersion2() throws Exception {

		PWList pwList = PWTestHelper.loadPwList(FILE_CONVERT_V1_TO_V2_XML);

		checkPwList(pwList);

		checkPwListChildren(pwList);
	}

	/**
	 * Verify the values from this node:.
	 *
	 * <pwlist created="2009-05-13T22:39:18.985+02:00" id="3583374439376141312"
	 * lastAction="UPDATE" lastModified="2009-05-13T23:41:38.955+02:00">
	 *
	 * @param pwList the pw list
	 */
	private void checkPwList(PWList pwList) {
		assertEquals("Checking pwList for created timestamp",  ZonedDateTime.parse(
				"2009-05-13T22:39:18.985+02:00"), pwList.getCreated());
		assertEquals("Checking pwList for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T23:41:38.955+02:00"), pwList
						.getLastModified());

		assertEquals("Checking pwList for id", 3583374439376141312L, pwList
				.getId());
		assertEquals("Checking pwList for last action",
				MappingElement.ACTIONS.UPDATE, pwList.getLastAction());
		assertEquals("Checking pwList for deletion", false, pwList.isDeleted());

		assertEquals("Checking pwList for name", null, pwList.getName());
	}

	/**
	 * Check the children within the PWList.
	 *
	 * @param pwList the pw list
	 *
	 * @throws Exception the exception
	 */
	private void checkPwListChildren(PWList pwList) throws Exception {
		assertEquals("Expected 4 child elements", 4, pwList.getElements()
				.values().size());
		int foundCount = 0;
		Iterator<MappingElement> it = pwList.getElements().values().iterator();

		while (it.hasNext()) {
			MappingElement me = it.next();
			if (me instanceof PWEntry) {
				PWEntry pwEntry = (PWEntry) me;
				if (3042283362471630848L == pwEntry.getId()) {
					checkDeletedEntry(pwEntry);
					foundCount++;
				}
				if (4463390212897459200L == pwEntry.getId()) {
					checkCompleteEntry(pwEntry);
					foundCount++;
				}

			}

			if (me instanceof PWTable) {
				PWTable pwTable = (PWTable) me;
				if (4095219210519856128L == pwTable.getId()) {
					checkDeletedTable(pwTable);
					foundCount++;
				}
				if (2771602408756686848L == pwTable.getId()) {
					checkPopulatedTable(pwTable);
					foundCount++;
				}

			}

		}
		assertEquals("Identified all entries", 4, foundCount);
	}

	/**
	 * Check the deleted entry:.
	 *
	 * <pwentry created="2009-05-13T22:41:09.077+02:00" id="3042283362471630848"
	 * lastAction="DELETE" lastModified="2009-05-13T22:42:05.640+02:00" />
	 *
	 * @param pwEntry the pwEntry to check
	 */
	private void checkDeletedEntry(PWEntry pwEntry) {
		assertEquals("Checking pwEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:41:09.077+02:00"), pwEntry.getCreated());
		assertEquals("Checking pwEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:42:05.640+02:00"), pwEntry
						.getLastModified());

		assertEquals("Checking pwEntry for id", 3042283362471630848L, pwEntry
				.getId());
		assertEquals("Checking pwEntry for last action",
				MappingElement.ACTIONS.DELETE, pwEntry.getLastAction());
		assertEquals("Checking pwEntry for deletion", true, pwEntry.isDeleted());

		assertEquals("Checking pwEntry for name", null, pwEntry.getName());

	}

	/**
	 * Check complete entry: <pwentry created="2009-05-13T22:39:22.823+02:00"
	 * id="4463390212897459200" lastAction="UPDATE"
	 * lastModified="2009-05-13T22:40:54.364+02:00" name="First Entry"> email =
	 * iam@home.net remark = This entry has been updated after creation.
	 * password = secret user=me url=http://www.home.net
	 *
	 * @param pwEntry the pwEntry to check
	 *
	 * @throws ElementDeletedException when the test fails
	 */
	private void checkCompleteEntry(PWEntry pwEntry)
			throws ElementDeletedException {
		assertEquals("Checking pwEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:39:22.823+02:00"), pwEntry.getCreated());
		assertEquals("Checking pwEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:40:54.364+02:00"), pwEntry
						.getLastModified());

		assertEquals("Checking pwEntry for id", 4463390212897459200L, pwEntry
				.getId());
		assertEquals("Checking pwEntry for last action",
				MappingElement.ACTIONS.UPDATE, pwEntry.getLastAction());
		assertEquals("Checking pwEntry for deletion", false, pwEntry
				.isDeleted());

		assertEquals("Checking pwEntry for name", "First Entry", pwEntry
				.getName());

		assertEquals("Checking pwEntry for email", "iam@home.net", pwEntry
				.getEmail());
		assertEquals("Checking pwEntry for remark",
				"This entry has been updated after creation.", pwEntry
						.getRemark());
		assertEquals("Checking pwEntry for password", "secret", pwEntry
				.getPassword());
		assertEquals("Checking pwEntry for user", "me", pwEntry.getUser());
		assertEquals("Checking pwEntry for url", "http://www.home.net", pwEntry
				.getUrl());

	}

	/**
	 * Check the deleted pw table:.
	 *
	 * <pwtable created="2009-05-13T22:43:32.738+02:00" id="4095219210519856128"
	 * lastAction="DELETE" lastModified="2009-05-13T23:41:38.955+02:00" />
	 *
	 * @param pwTable the table to check
	 */
	private void checkDeletedTable(PWTable pwTable) {
		assertEquals("Checking pwTable for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:43:32.738+02:00"), pwTable.getCreated());
		assertEquals("Checking pwTable for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T23:41:38.955+02:00"), pwTable
						.getLastModified());

		assertEquals("Checking pwTable for id", 4095219210519856128L, pwTable
				.getId());
		assertEquals("Checking pwTable for last action",
				MappingElement.ACTIONS.DELETE, pwTable.getLastAction());
		assertEquals("Checking pwTable for deletion", true, pwTable.isDeleted());

		assertEquals("Checking pwTable for name", null, pwTable.getName());

	}

	/**
	 * Check a table with entries.
	 * <pwtable created="2009-05-13T22:42:08.630+02:00" id="2771602408756686848"
	 * lastAction="UPDATE" lastModified="2009-05-13T22:43:27.667+02:00"
	 * name="A table with a set of values, where 1 is deleted">
	 *
	 * @param pwTable the table to check
	 *
	 * @throws Exception when the test fails
	 */
	private void checkPopulatedTable(PWTable pwTable) throws Exception {
		assertEquals("Checking pwTable for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:42:08.630+02:00"), pwTable.getCreated());
		assertEquals("Checking pwTable for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:43:27.667+02:00"), pwTable
						.getLastModified());

		assertEquals("Checking pwTable for id", 2771602408756686848L, pwTable
				.getId());
		assertEquals("Checking pwTable for last action",
				MappingElement.ACTIONS.UPDATE, pwTable.getLastAction());
		assertEquals("Checking pwTable for deletion", false, pwTable
				.isDeleted());

		assertEquals("Checking pwTable for name",
				"A table with a set of values, where 1 is deleted", pwTable
						.getName());

		checkTableEntry001(pwTable);
		checkTableEntry002(pwTable);
		checkTableEntry003(pwTable);
		checkTableEntry004(pwTable);
		checkTableEntry005(pwTable);
	}

	/**
	 * Check these entries:
	 * <pwtableentry created="2009-05-13T22:43:13.326+02:00" id="1650289356243081216"
	 * lastAction="UPDATE" lastModified="2009-05-13T22:43:26.461+02:00"
	 * name="005">that is the last entry here.</pwtableentry>
	 *
	 * @param table the table to check
	 *
	 * @throws Exception when the test fails
	 */
	private void checkTableEntry005(PWTable table) throws Exception {

		PWTableEntry tableEntry = (PWTableEntry) table.getElements().get(
				1650289356243081216L);

		assertEquals("Checking tableEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:43:13.326+02:00"), tableEntry.getCreated());
		assertEquals("Checking tableEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:43:26.461+02:00"), tableEntry
						.getLastModified());

		assertEquals("Checking tableEntry for id", 1650289356243081216L,
				tableEntry.getId());
		assertEquals("Checking tableEntry for last action",
				MappingElement.ACTIONS.UPDATE, tableEntry.getLastAction());
		assertEquals("Checking tableEntry for deletion", false, tableEntry
				.isDeleted());

		assertEquals("Checking tableEntry for name", "005", tableEntry
				.getName());
		assertEquals("Checking tableEntry for value",
				"that is the last entry here.", tableEntry.getContent());
	}

	/**
	 * Check these entries:.
	 * <pwtableentry created="2009-05-13T22:42:28.623+02:00" id="4946060908920114176"
	 * lastAction="UPDATE" lastModified="2009-05-13T22:42:45.621+02:00"
	 * name="001">Value is present</pwtableentry>
	 *
	 * @param table the table to check
	 *
	 * @throws Exception when the test fails
	 */
	private void checkTableEntry001(PWTable table) throws Exception {

		PWTableEntry tableEntry = (PWTableEntry) table.getElements().get(
				4946060908920114176L);

		assertEquals("Checking tableEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:42:28.623+02:00"), tableEntry.getCreated());
		assertEquals("Checking tableEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:42:45.621+02:00"), tableEntry
						.getLastModified());

		assertEquals("Checking tableEntry for id", 4946060908920114176L,
				tableEntry.getId());
		assertEquals("Checking tableEntry for last action",
				MappingElement.ACTIONS.UPDATE, tableEntry.getLastAction());
		assertEquals("Checking tableEntry for deletion", false, tableEntry
				.isDeleted());

		assertEquals("Checking tableEntry for name", "001", tableEntry
				.getName());
		assertEquals("Checking tableEntry for value", "Value is present",
				tableEntry.getContent());
	}

	/**
	 * Check these entries:.
	 * <pwtableentry created="2009-05-13T22:42:45.717+02:00" id="6931409495167687680"
	 * lastAction="UPDATE" lastModified="2009-05-13T22:42:54.277+02:00"
	 * name="002">Another entry</pwtableentry>
	 *
	 * @param table the table
	 *
	 * @throws Exception when the test fails
	 */
	private void checkTableEntry002(PWTable table) throws Exception {

		PWTableEntry tableEntry = (PWTableEntry) table.getElements().get(
				6931409495167687680L);

		assertEquals("Checking tableEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:42:45.717+02:00"), tableEntry.getCreated());
		assertEquals("Checking tableEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:42:54.277+02:00"), tableEntry
						.getLastModified());

		assertEquals("Checking tableEntry for id", 6931409495167687680L,
				tableEntry.getId());
		assertEquals("Checking tableEntry for last action",
				MappingElement.ACTIONS.UPDATE, tableEntry.getLastAction());
		assertEquals("Checking tableEntry for deletion", false, tableEntry
				.isDeleted());

		assertEquals("Checking tableEntry for name", "002", tableEntry
				.getName());
		assertEquals("Checking tableEntry for value", "Another entry",
				tableEntry.getContent());
	}

	/**
	 * Check these entries:.
	 * <pwtableentry created="2009-05-13T22:42:54.373+02:00" id="3350087237718044672"
	 * lastAction="DELETE" lastModified="2009-05-13T22:43:27.667+02:00" />
	 *
	 * @param table the table
	 *
	 * @throws Exception when the test fails
	 */
	private void checkTableEntry003(PWTable table) throws Exception {

		PWTableEntry tableEntry = (PWTableEntry) table.getElements().get(
				3350087237718044672L);

		assertEquals("Checking tableEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:42:54.373+02:00"), tableEntry.getCreated());
		assertEquals("Checking tableEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:43:27.667+02:00"), tableEntry
						.getLastModified());

		assertEquals("Checking tableEntry for id", 3350087237718044672L,
				tableEntry.getId());
		assertEquals("Checking tableEntry for last action",
				MappingElement.ACTIONS.DELETE, tableEntry.getLastAction());
		assertEquals("Checking tableEntry for deletion", true, tableEntry
				.isDeleted());

		assertEquals("Checking tableEntry for name", null, tableEntry.getName());

	}

	/**
	 * Check these entries:
	 * <pwtableentry created="2009-05-13T22:43:00.925+02:00" id="6487104134135585792"
	 * lastAction="UPDATE" lastModified="2009-05-13T22:43:13.228+02:00"
	 * name="004">the entry 003 was deleted.</pwtableentry>
	 *
	 * @param table the table
	 *
	 * @throws Exception when the test fails
	 */
	private void checkTableEntry004(PWTable table) throws Exception {

		PWTableEntry tableEntry = (PWTableEntry) table.getElements().get(
				6487104134135585792L);

		assertEquals("Checking tableEntry for created timestamp", ZonedDateTime.parse(
				"2009-05-13T22:43:00.925+02:00"), tableEntry.getCreated());
		assertEquals("Checking tableEntry for lastModified timestamp",
				ZonedDateTime.parse("2009-05-13T22:43:13.228+02:00"), tableEntry
						.getLastModified());

		assertEquals("Checking tableEntry for id", 6487104134135585792L,
				tableEntry.getId());
		assertEquals("Checking tableEntry for last action",
				MappingElement.ACTIONS.UPDATE, tableEntry.getLastAction());
		assertEquals("Checking tableEntry for deletion", false, tableEntry
				.isDeleted());

		assertEquals("Checking tableEntry for name", "004", tableEntry
				.getName());
		assertEquals("Checking tableEntry for value",
				"the entry 003 was deleted.", tableEntry.getContent());
	}
}
