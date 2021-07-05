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

import java.util.HashMap;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;

import de.petranek.syncyoursecrets.model.pw.PWEntry;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWEntryUpdateTest.
 *
 * @author Jan Petranek
 */
public class PWEntryUpdateTest {

	/** The Constant UPDATED_NAME. */
	private static final String UPDATED_NAME = "Updated name";

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
	 * Tests the update name of the name property.
	 *
	 * @throws Exception the exception when the test fails.
	 */
	@Test
	public void testUpdateName() throws Exception {
		PWEntry pwEntry = PWTestHelper.createEntry(testEntryProperties);

		pwEntry.setCreated(PWTestHelper.getOldTimeStamp());
		pwEntry.setLastModified(PWTestHelper.getOldTimeStamp());

		// update the name property
		pwEntry.setName(UPDATED_NAME);

		testEntryProperties.put(PWTestHelper.NAME, UPDATED_NAME);

		assertEquals("Timestamp should be updated", 1, pwEntry
				.getLastModified().compareTo(PWTestHelper.getOldTimeStamp()));
		assertEquals("Last Action should be modified",
				MappingElement.ACTIONS.UPDATE, pwEntry.getLastAction());
		PWTestHelper.checkEntry(testEntryProperties, pwEntry);

	}

	/**
	 * Tests, if a change/modify event is propagated.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPropagateChange() throws Exception {
		PWEntry pwEntry = PWTestHelper.createEntry(testEntryProperties);

		pwEntry.setCreated(PWTestHelper.getOldTimeStamp());
		pwEntry.setLastModified(PWTestHelper.getOldTimeStamp());

		// update the name property
		String remark = "We update a single property and expect the entire pwEntry to update";
		pwEntry.setRemark(remark);
		testEntryProperties.put(PWTestHelper.REMARK, remark);

		assertEquals("Timestamp should be updated", 1, pwEntry
				.getLastModified().compareTo(PWTestHelper.getOldTimeStamp()));
		assertEquals("Last Action should be modified",
				MappingElement.ACTIONS.UPDATE, pwEntry.getLastAction());
		PWTestHelper.checkEntry(testEntryProperties, pwEntry);

	}
}
