/**
 * SyncYourSecrets-pwmodel ties the generic MappingElements from the
 * xmlbase to a concrete structure, suitable for password entries.
 *
 *
 *    Copyright 2009 Jan Petranek
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
import java.util.HashMap;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.model.pw.PWList;
import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;

import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadAndPerformTest is an unused test class, it may become a stub
 * for later test methods that load data from the filesystem.
 *
 * @author Jan Petranek
 */
public class ResurrectionTest {

	/** The Constant DELETED_FILE_V1 points to a file, where an entry was deleted. The file was created with XML version 1 */
	private static final String DELETED_FILE_V1 = FileLocationHelperModel
			.getFileName("resurrection/deleted13.xml");

	/** The Constant RESURECTED_FILE_V1 points to a file, where an entry was resurrected, i.e. the file has not yet seen the deletion and the entry was modified. The file was created with XML version 1. */
	private static final String RESURECTED_FILE_V1 = FileLocationHelperModel
			.getFileName("resurrection/resurrect13.xml");

	/** The Constant DELETED_FILE_V2 points to a file, where an entry was deleted. The file was created with XML version 2. */
	private static final String DELETED_FILE_V2 = FileLocationHelperModel
			.getFileName("resurrection/deleted_v2.xml");

	/** The Constant RESURECTED_FILE_V2 points to a file, where an element has been resureccted. The file was created with XML version 1. */
	private static final String RESURECTED_FILE_V2 = FileLocationHelperModel
			.getFileName("resurrection/resurrect_v2.xml");

	/** The properties for our entry. The PWEntry will be created with these values and later we check the values. */
	private Map<String, String> testEntryProperties = new HashMap<String, String>();

	/**
	 * Sets the test properties up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		testEntryProperties.put(PWTestHelper.NAME, "Old Name");
		testEntryProperties.put(PWTestHelper.PASSWORD,
				"UPDATE this and thou shall live");
		testEntryProperties.put(PWTestHelper.USER, "myuser");
		testEntryProperties.put(PWTestHelper.URL, "home");
		testEntryProperties
				.put(
						PWTestHelper.REMARK,
						"Testcase: We will update this file with a newer name and verify the change on the name property.");
		testEntryProperties.put(PWTestHelper.EMAIL, "im@home");

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

		PWList deletedList = PWTestHelper.loadPwList(DELETED_FILE_V1);
		assertEquals("Comparing Version on deleted", 1, deletedList
				.getVersion());

		PWList resurectedList = PWTestHelper.loadPwList(RESURECTED_FILE_V1);
		assertEquals("Comparing Version on deleted", 1, resurectedList
				.getVersion());

		mergeAndCheck(deletedList, resurectedList);
	}

	/**
	 * Merges two files and checks the result. The input files are both in version 2.
	 *
	 * The first file contains a deleted entry. The other file contains the same entry,
	 * but the entry has been modified later. So, when we merge the two files, we
	 * expect the second change to overwrite the deletion, that is, the entry "resurrects".
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMergeV2() throws Exception {

		PWList deletedList = PWTestHelper.loadPwList(DELETED_FILE_V2);
		assertEquals("Comparing Version on deleted", 2, deletedList
				.getVersion());

		PWList resurectedList = PWTestHelper.loadPwList(RESURECTED_FILE_V2);
		assertEquals("Comparing Version on deleted", 2, resurectedList
				.getVersion());

		mergeAndCheck(deletedList, resurectedList);
	}

	/**
	 * Merge and check.
	 *
	 * @param deletedList the deleted list
	 * @param resurectedList the resurected list
	 *
	 * @throws SysInvalidArgumentException the sys invalid argument exception
	 * @throws ElementDeletedException the element deleted exception
	 */
	private void mergeAndCheck(PWList deletedList, PWList resurectedList)
			throws SysInvalidArgumentException, ElementDeletedException {

		ZonedDateTime updatedTimestamp = resurectedList.getLastModified();

		PWList mergedList = (PWList) deletedList.merge(resurectedList);

		assertEquals("Expecting newest timestamp", updatedTimestamp, mergedList
				.getLastModified());

		// get first entry from mergedList

		PWEntry pwEntry = (PWEntry) mergedList.getElements().values()
				.iterator().next();

		assertEquals("Last Action should be modified",
				MappingElement.ACTIONS.UPDATE, pwEntry.getLastAction());
		PWTestHelper.checkEntry(testEntryProperties, pwEntry);
	}

}
