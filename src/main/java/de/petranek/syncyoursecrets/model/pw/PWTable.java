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

import java.util.TreeMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.SysParseException;
import de.petranek.syncyoursecrets.xmlmapping.ListElement;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

/**
 * The PWTable models a Table of PWTableEntries.
 * 
 * @author Jan Petranek
 */
public class PWTable extends ListElement {

	/** The Constant logger. */
	static final Logger logger = LogManager.getLogger(PWTable.class);

	/** The Constant ELEMENT_NAME. */
	public static final String ELEMENT_NAME = "pwtable";

	/**
	 * Instantiates a new PWTable from an XML node.
	 * 
	 * @param node
	 *            the node
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the argument was invalid
	 * @throws SysParseException
	 *             when the XML cannot be transformed into a PWTable
	 */
	public PWTable(Element node, MappingElement parent)
			throws SysParseException, SysInvalidArgumentException {
		super(node, parent);

	}

	/**
	 * Instantiates a new PWTable with the given name.
	 * 
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the argument was invalid
	 */
	public PWTable(MappingElement parent) throws SysInvalidArgumentException {
		super(ELEMENT_NAME, parent);

	}

	/**
	 * Load a child element from XML.
	 * 
	 * First, we identify the node by its name. Then, we can attempt to parse
	 * the node as a PWTableEntry.
	 * 
	 * @param name
	 *            the name of the XML-node
	 * @param node
	 *            the XML-node to be parsed
	 * 
	 * @return a mapping element (non-Javadoc)
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the argument was invalid
	 * @throws SysParseException
	 *             when the XMl could not be loaded
	 * 
	 * @see de.petranek.syncyoursecrets.xmlmapping.ListElement#loadElement(java
	 *      .lang.String, org.w3c.dom.Element)
	 */
	@Override
	protected MappingElement loadElement(String name, Element node)
			throws SysParseException, SysInvalidArgumentException {
		if (PWTableEntry.ELEMENT_NAME.equals(name)) {
			PWTableEntry tableEntry = new PWTableEntry(node, this);
			if (logger.isTraceEnabled()) {
				logger.trace("Found child element "
						+ MappingElement.log(tableEntry));
			}
			return tableEntry;
		}

		logger.warn("Unknown child element found, skipping " + name);
		return null;
	}

	/**
	 * Gets the elements.
	 * 
	 * NOTE: This method is currently only called in test methods; it should not
	 * be used in production code, as this circumvents the encapsulation (esp.
	 * deleted entries can be seen).
	 * 
	 * @return the elements
	 * 
	 * @see de.petranek.syncyoursecrets.xmlmapping.ListElement#getElements()
	 */
	@Override
	protected TreeMap<Long, MappingElement> getElements() {
		return super.getElements();
	}
}
