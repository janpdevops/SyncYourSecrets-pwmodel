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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.SysParseException;
import de.petranek.syncyoursecrets.util.SysRuntimeException;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.Entry;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;
import de.petranek.syncyoursecrets.xmlmapping.StringElement;

/**
 * The Class PWTableEntry is an entry in a PWTable. It models a simple key-value
 * pair.
 * 
 * @author Jan Petranek
 */
public class PWTableEntry extends Entry {

	/** The Constant logger. */
	static final Logger logger = LogManager.getLogger(PWTableEntry.class);

	/** The name of this xml element. */
	public static final String ELEMENT_NAME = "pwtableentry";

	/** The Constant for the property CONTENT. */
	public static final String CONTENT = "content";

	/**
	 * Constructs a new PWTableEntry with the name as attribute. Note, that this
	 * is not the element name (as opposed to StringElement(String) !
	 * 
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the argument was invalid
	 */
	public PWTableEntry(MappingElement parent)
			throws SysInvalidArgumentException {
		super(ELEMENT_NAME, parent);
	}

	/**
	 * The Constructor.
	 * 
	 * @param node
	 *            the node
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the argument was invalid
	 * @throws SysParseException
	 *             when the XML could not be transformed into a PWTableEntry
	 */
	public PWTableEntry(Element node, MappingElement parent)
			throws SysParseException, SysInvalidArgumentException {
		super(node, parent);
		if (this.isDeleted()) {
			return;
		}
		if (LEGACY_VERSION_1 == this.getVersion()) {
			StringElement tempEntry = new StringElement(node, null);
			try {
				this.setName(tempEntry.getLegacyName(), true);
				this.getNameProperty().setCreated(this.getCreated());
				this.getNameProperty().setLastAction(this.getLastAction());
				this.getNameProperty().setLastModified(this.getLastModified());

				this.setContent(tempEntry.getContent(), true);
			} catch (ElementDeletedException ex) {
				String msg = "Cannot set legacy name";
				logger.error(msg, ex);
				throw new SysRuntimeException(msg, ex);
			}

		}

	}

	/**
	 * Initializes the children / properties of the PWTableEntry.
	 * 
	 * @throws SysInvalidArgumentException
	 *             *
	 * @see de.petranek.syncyoursecrets.xmlmapping.Entry#initChildren()
	 */
	@Override
	protected void initChildren() {
		logger.trace("entering initChildren");
		try {

			super.initChildren();

			addStringProperty(CONTENT);
		} catch (SysInvalidArgumentException e) {
			String msg = "Failed to initialize children, this is a bug";
			logger.fatal(msg, e);
			throw new SysRuntimeException(msg, e);
		}
		logger.trace("exiting initChildren");

	}

	/**
	 * Gets the content.
	 * 
	 * @return the content.
	 */
	public String getContent() {

		try {
			return this.getStringChildByName(CONTENT).getContent();
		} catch (ElementDeletedException ex) {
			logger.error("Getting content from deleted PWTable Entry", ex);
			return null;
		}

	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new coontent
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setContent(String content) throws ElementDeletedException {
		setContent(content, false);
	}

	/**
	 * Sets the content.
	 * 
	 * @param skipUpdate
	 *            suppress update events, if true.
	 * @param content
	 *            the new content
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	private void setContent(String content, boolean skipUpdate)
			throws ElementDeletedException {
		this.getStringChildByName(CONTENT).setContent(content, skipUpdate);
	}

}
