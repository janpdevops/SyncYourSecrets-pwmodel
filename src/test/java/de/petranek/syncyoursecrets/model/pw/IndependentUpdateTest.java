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
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;



import org.junit.Before;
import org.junit.Test;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;

import de.petranek.syncyoursecrets.xmlmapping.MappingElement;
import de.petranek.syncyoursecrets.model.pw.FileLocationHelperModel;

// TODO: Auto-generated Javadoc
/**
 * The Class IndependentUpdateTest.
 *
 * @author janp
 */
public class IndependentUpdateTest {

	/** The Constant ADDED_ENTRY_ID. */
	private static final long ADDED_ENTRY_ID = 7999099149007219712L;

	/** The Constant FILLED_ENTRY_ID. */
	private static final long FILLED_ENTRY_ID = 8584938351695561728L;

	/** The Constant FILL_NAME_FILE. */
	private static final String FILL_NAME_FILE = FileLocationHelperModel
			.getFileName("independent_update/filled_name2.xml");

	/** The Constant ADD_OTHER_ENTRY_FILE. */
	private static final String ADD_OTHER_ENTRY_FILE = FileLocationHelperModel
			.getFileName("independent_update/addedEntry2.xml");

	/** The properties for our entry. The PWEntry will be created with these values and later we check the values. */
	private Map<String, String> filledEntryProperties = new HashMap<String, String>();

	/** The properties for our entry. The PWEntry will be created with these values and later we check the values. */
	private Map<String, String> addedEntryProperties = new HashMap<String, String>();

	/**
	 * Upsets the test properties.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		filledEntryProperties.put(PWTestHelper.NAME, "filled Name");
		filledEntryProperties.put(PWTestHelper.PASSWORD, "");
		filledEntryProperties.put(PWTestHelper.USER, "");
		filledEntryProperties.put(PWTestHelper.URL, "");
		filledEntryProperties.put(PWTestHelper.REMARK, "Empty name");
		filledEntryProperties.put(PWTestHelper.EMAIL, "");

		addedEntryProperties.put(PWTestHelper.NAME, "New Entry");
		addedEntryProperties.put(PWTestHelper.PASSWORD, "");
		addedEntryProperties.put(PWTestHelper.USER, "");
		addedEntryProperties.put(PWTestHelper.URL, "");
		addedEntryProperties.put(PWTestHelper.REMARK,
				"Added this entry, other one still without a name");
		addedEntryProperties.put(PWTestHelper.EMAIL, "");

	}

	/**
	 * Merges two files and checks the result. The input files are both in version 1.
	 *
	 * The first file contains a deleted entry. The other file contains the same entry,
	 * but the entry has been modified later. So, when we merge the two files, we
	 * expect the second change to overwrite the deletion, that is, the entry "resurrects".
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMergeV1() throws Exception {

		PWList fillNameList = PWTestHelper.loadPwList(FILL_NAME_FILE);
		assertEquals("Comparing Version on deleted", 2, fillNameList
				.getVersion());

		PWList addEntryList = PWTestHelper.loadPwList(ADD_OTHER_ENTRY_FILE);
		assertEquals("Comparing Version on deleted", 2, addEntryList
				.getVersion());

		mergeAndCheck(fillNameList, addEntryList);
	}

	/**
	 * Merge and check.
	 *
	 * @param fillNameList the fill name list
	 * @param addEntryList the add entry list
	 *
	 * @throws SysInvalidArgumentException the sys invalid argument exception
	 * @throws ElementDeletedException the element deleted exception
	 */
	private void mergeAndCheck(PWList fillNameList, PWList addEntryList)
			throws SysInvalidArgumentException, ElementDeletedException {

		ZonedDateTime updatedTimestamp = addEntryList.getLastModified();

		PWList mergedList = (PWList) fillNameList.merge(addEntryList);

		assertEquals("Expecting newest timestamp", updatedTimestamp, mergedList
				.getLastModified());

		// get first entry from mergedList

		PWEntry pwEntry = (PWEntry) mergedList.getElements().values()
				.iterator().next();

		assertEquals("Last Action should be modified",
				MappingElement.ACTIONS.UPDATE, pwEntry.getLastAction());

		PWEntry filledEntry = (PWEntry) mergedList.getElements().get(
				FILLED_ENTRY_ID);
		assertNotNull("Filled Entry expected", filledEntry);

		PWTestHelper.checkEntry(filledEntryProperties, filledEntry);

		PWEntry addedEntry = (PWEntry) mergedList.getElements().get(
				ADDED_ENTRY_ID);
		assertNotNull("Added Entry expected", addedEntry);

		PWTestHelper.checkEntry(addedEntryProperties, addedEntry);

	}

}
