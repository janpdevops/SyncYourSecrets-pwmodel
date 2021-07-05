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

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWEntryMergeTest tests the merge behaviour of the PWEntry.
 *
 * We create two PWEntries and merge them. To simplify the creation and
 * verification of the entries' properties, we utilize maps: Two for the entries
 * to be created and a third for the expected result of the merge.
 *
 * @author Jan Petranek
 */
public class PWEntryMergeTest {

	/** The properties of the first entry. */
	private Map<String, String> firstProperties = new HashMap<String, String>();

	/** The properties of the first entry. */
	private Map<String, String> secondProperties = new HashMap<String, String>();

	/** The merged props. */
	private Map<String, String> mergedProps = new HashMap<String, String>();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		// properties for the first entry for the merge
		firstProperties.put(PWTestHelper.NAME, "sourceforge account");
		firstProperties.put(PWTestHelper.PASSWORD, "Top secret");
		firstProperties.put(PWTestHelper.USER, "myaccountname");
		firstProperties.put(PWTestHelper.URL, "https://sourceforge.net/");
		firstProperties.put(PWTestHelper.REMARK,
				"Of course, this is not my real account password");
		firstProperties.put(PWTestHelper.EMAIL,
				"jpetranek@users.sourceforge.net");

		// properties for the second entry to merge.
		secondProperties
				.put(PWTestHelper.NAME,
						"='ddd'&?!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		secondProperties.put(PWTestHelper.PASSWORD, "<!--$?-->");
		secondProperties
				.put(PWTestHelper.USER,
						"!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		secondProperties.put(PWTestHelper.URL, "<pwentry>stuff</pwentry>");
		secondProperties.put(PWTestHelper.REMARK,
				"See if you can get along with special characters");
		secondProperties.put(PWTestHelper.EMAIL, "home.sweet@127.0.0.1");

		// expected properties of the merged entry.
		mergedProps
				.put(PWTestHelper.NAME,
						"='ddd'&?!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		mergedProps.put(PWTestHelper.PASSWORD, "Top secret");
		mergedProps
				.put(PWTestHelper.USER,
						"!@\u00a7$%&/{[]}\\\u00c4\u00d6\u00dc\u00df\u00e4\u00f6\u00fc\u00b5 ");
		mergedProps.put(PWTestHelper.URL, "<pwentry>stuff</pwentry>");
		mergedProps.put(PWTestHelper.REMARK,
				"See if you can get along with special characters");
		mergedProps.put(PWTestHelper.EMAIL, "home.sweet@127.0.0.1");

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
	 * Test merge.
	 *
	 * Create two entries from the property maps and check the result of the
	 * merge against a map of expected properties.
	 *
	 * We explicitly set the timestamps for all child elements here. We expect
	 * only the newest properties to show up in the merged PWEntry.
	 *
	 * @throws Exception the exception when the test fails
	 */
	@Test
	public void testMergeSecondWithFirst() throws Exception {
		PWEntry firstEntry = PWTestHelper.createEntry(firstProperties);
		firstEntry.setLastModified(PWTestHelper.getOldTimeStamp());
		// only password is newer
		firstEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastModified(
				PWTestHelper.getNewestTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastAction(
				MappingElement.ACTIONS.UPDATE);
		firstEntry.getStringChildByName(PWTestHelper.USER).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.URL).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.REMARK).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.EMAIL).setLastModified(
				PWTestHelper.getOldTimeStamp());

		// entire entry has been updated:
		PWEntry updatedEntry = PWTestHelper.createEntry(secondProperties);
		updatedEntry.setLastModified(PWTestHelper.getUpdatedTimeStamp());

		updatedEntry.getStringChildByName(PWTestHelper.PASSWORD)
				.setLastModified(PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastAction(
				MappingElement.ACTIONS.UPDATE);
		updatedEntry.getStringChildByName(PWTestHelper.USER).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.URL).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.REMARK).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.EMAIL).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());

		PWEntry mergedEntry = (PWEntry) updatedEntry.merge(firstEntry);

		PWTestHelper.checkEntry(mergedProps, mergedEntry);

	}

	/**
	 * Test merge.
	 *
	 * Create two entries from the property maps and check the result of the
	 * merge against a map of expected properties.
	 *
	 * We explicitly set the timestamps for all child elements here. We expect
	 * only the newest properties to show up in the merged PWEntry.
	 *
	 * @throws Exception the exception when the test fails
	 */
	@Test
	public void testMergeFirstWithSecond() throws Exception {
		PWEntry firstEntry = PWTestHelper.createEntry(firstProperties);
		firstEntry.setLastModified(PWTestHelper.getOldTimeStamp());
		// only password is newer
		firstEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastModified(
				PWTestHelper.getNewestTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastAction(
				MappingElement.ACTIONS.UPDATE);
		firstEntry.getStringChildByName(PWTestHelper.USER).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.URL).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.REMARK).setLastModified(
				PWTestHelper.getOldTimeStamp());
		firstEntry.getStringChildByName(PWTestHelper.EMAIL).setLastModified(
				PWTestHelper.getOldTimeStamp());

		// entire entry has been updated:
		PWEntry updatedEntry = PWTestHelper.createEntry(secondProperties);
		updatedEntry.setLastModified(PWTestHelper.getUpdatedTimeStamp());

		updatedEntry.getStringChildByName(PWTestHelper.PASSWORD)
				.setLastModified(PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.PASSWORD).setLastAction(
				MappingElement.ACTIONS.UPDATE);
		updatedEntry.getStringChildByName(PWTestHelper.USER).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.URL).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.REMARK).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());
		updatedEntry.getStringChildByName(PWTestHelper.EMAIL).setLastModified(
				PWTestHelper.getUpdatedTimeStamp());

		PWEntry mergedEntry = (PWEntry) firstEntry.merge(updatedEntry);

		PWTestHelper.checkEntry(mergedProps, mergedEntry);

	}

}
