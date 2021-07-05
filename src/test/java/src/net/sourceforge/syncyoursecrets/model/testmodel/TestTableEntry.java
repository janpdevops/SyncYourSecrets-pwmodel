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
package net.sourceforge.syncyoursecrets.model.testmodel;

// TODO: Auto-generated Javadoc
/**
 * The Class TestTableEntry models the visible data in a PWTableEntry. Within
 * our test cases, we use it to set and compare record sets.
 * 
 * @author Jan Petranek
 */
public class TestTableEntry {

	/** The deleted flag. */
	private boolean deleted;

	/** The name. */
	private String name;

	/** The content. */
	private String content;

	/** The id. */
	private long id;

	/**
	 * Instantiates a new test table entry.
	 */
	public TestTableEntry() {
		super();
	}

	/**
	 * The Constructor.
	 * 
	 * @param key the key
	 * @param value the value
	 */
	public TestTableEntry(String key, String value) {
		super();
		this.name = key;
		this.content = value;
	}

	/**
	 * Checks if the entry is deleted.
	 * 
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Sets the deleted.
	 * 
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the key
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param key the key to set
	 */
	public void setName(String key) {
		this.name = key;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the value
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param value the value to set
	 */
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
