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

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.SysParseException;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.ListElement;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PWList models a ListElement, whose child elements are a mix of
 * PWEntries and List elements which are identified by the XML-Element named
 * "pwentry" and "pwList". The loadElement method handles this objects logic.
 * 
 * @author Jan Petranek
 */
public class PWList extends ListElement {

	/** The Constant logger. */
	static final Logger logger = LogManager.getLogger(PWList.class);

	/** The Constant ELEMENT_NAME. */
	public static final String ELEMENT_NAME = "pwlist";

	/** The listeners. */
	private Set<ModificationListener> listeners = new HashSet<ModificationListener>();

	/**
	 * The Constructor.
	 * 
	 * @throws SysInvalidArgumentException
	 *             means a bug here.
	 */
	public PWList() throws SysInvalidArgumentException {
		super(ELEMENT_NAME, null);
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
	 *             when the input was invalid
	 * @throws SysParseException
	 *             when the XML could not be transformed into a PWList
	 */
	public PWList(Element node, MappingElement parent)
			throws SysParseException, SysInvalidArgumentException {
		super(node, parent);
	}

	/**
	 * Load a child element from XML.
	 * 
	 * First, we identify the node by its name. Then, we can attempt to parse
	 * the node as a PWEntry.
	 * 
	 * @param name
	 *            the name of the XML-node
	 * @param node
	 *            the XML-node to be parsed
	 * 
	 * @return a mapping element (non-Javadoc)
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the child node could not be initialized
	 * @throws SysParseException
	 *             when the child node's XML was invalid
	 * 
	 * @see de.petranek.syncyoursecrets.xmlmapping.ListElement#loadElement(java
	 *      .lang.String, org.w3c.dom.Element)
	 */
	@Override
	protected MappingElement loadElement(String name, Element node)
			throws SysParseException, SysInvalidArgumentException {

		if (PWEntry.ELEMENT_NAME.equals(name)) {
			PWEntry pwEntry = new PWEntry(node, this);

			if (logger.isTraceEnabled()) {
				logger.trace("PWEntry found" + MappingElement.log(pwEntry));
			}

			return pwEntry;
		} else if (PWTable.ELEMENT_NAME.equals(name)) {
			PWTable pwTable = new PWTable(node, this);

			if (logger.isTraceEnabled()) {
				logger.trace("PWTable found" + MappingElement.log(pwTable));

			}
			return pwTable;
		} else {
			logger.warn("Unknown child element found, skipping " + name);
		}

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

	/**
	 * Adds the modification listener. As the PWList is the root element with
	 * our application, it is sufficient to listen to modifications on the root
	 * element.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addModificationListener(ModificationListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes the modification listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeModificationListener(ModificationListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Updates this element; in addition, notify all modification listeners.
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 * 
	 * @see de.petranek.syncyoursecrets.xmlmapping.MappingElement#modify()
	 */
	@Override
	protected void modify() throws ElementDeletedException {

		super.modify();
		for (ModificationListener listener : listeners) {
			listener.modified(this);
		}
	}

}
