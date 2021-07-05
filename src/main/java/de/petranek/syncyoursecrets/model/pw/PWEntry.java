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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import de.petranek.syncyoursecrets.util.SysInvalidArgumentException;
import de.petranek.syncyoursecrets.util.SysParseException;
import de.petranek.syncyoursecrets.util.SysRuntimeException;
import de.petranek.syncyoursecrets.xmlmapping.ElementDeletedException;
import de.petranek.syncyoursecrets.xmlmapping.Entry;
import de.petranek.syncyoursecrets.xmlmapping.MappingElement;

/**
 * The Class PWEntry models a concrete password entry. It uses simple
 * StringElements for its properties. In the method initChildren, the child
 * nodes are defined as properties.
 * 
 * @author Jan Petranek
 */
public class PWEntry extends Entry {

	/** The Constant logger. */
	static final Logger logger = LogManager.getLogger(PWEntry.class);

	/** The name of this xml-name. */
	public static final String ELEMENT_NAME = "pwentry";

	/** The Constant for the property URL. */
	public static final String URL = "url";

	/** The Constant for the property USER. */
	public static final String USER = "user";

	/** The Constant for the property PASSWORD. */
	public static final String PASSWORD = "password";

	/** The Constant for the property REMARK. */
	public static final String REMARK = "remark";

	/** The Constant for the property EMAIL. */
	public static final String EMAIL = "email";

	/**
	 * Instantiates a new PWEntry with the given name property.
	 * 
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the arguments were invalid
	 */
	public PWEntry(MappingElement parent) throws SysInvalidArgumentException {
		super(ELEMENT_NAME, parent);
	}

	/**
	 * Parses an XML node and instantiates a new PWEntry from it.
	 * 
	 * @param node
	 *            the node
	 * @param parent
	 *            the parent
	 * 
	 * @throws SysInvalidArgumentException
	 *             when the arguments were invalid
	 * @throws SysParseException
	 *             when the XML could not be converted into a PWEntry
	 */
	public PWEntry(Element node, MappingElement parent)
			throws SysParseException, SysInvalidArgumentException {
		super(node, parent);

	}

	/**
	 * Initializes the children / properties of the PWEntry. This is all the
	 * logic that goes into defining the logic, folks!
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

			addStringProperty(URL);
			addStringProperty(USER);
			addStringProperty(PASSWORD);
			addStringProperty(REMARK);
			addStringProperty(EMAIL);
		} catch (SysInvalidArgumentException e) {
			String msg = "Failed to initialize children, this is a bug";
			logger.fatal(msg, e);
			throw new SysRuntimeException(msg, e);
		}
		logger.trace("exiting initChildren");

	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public String getUrl() throws ElementDeletedException {
		return this.getStringChildByName(URL).getContent();
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public String getUser() throws ElementDeletedException {
		return this.getStringChildByName(USER).getContent();
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public String getPassword() throws ElementDeletedException {
		return this.getStringChildByName(PASSWORD).getContent();
	}

	/**
	 * Gets the remark.
	 * 
	 * @return the remark
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public String getRemark() throws ElementDeletedException {
		return this.getStringChildByName(REMARK).getContent();
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public String getEmail() throws ElementDeletedException {
		return this.getStringChildByName(EMAIL).getContent();
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setUrl(String url) throws ElementDeletedException {
		this.getStringChildByName(URL).setContent(url, false);
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setUser(String user) throws ElementDeletedException {
		this.getStringChildByName(USER).setContent(user, false);
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setPassword(String password) throws ElementDeletedException {
		this.getStringChildByName(PASSWORD).setContent(password, false);
	}

	/**
	 * Sets the remark.
	 * 
	 * @param remark
	 *            the new remark
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setRemark(String remark) throws ElementDeletedException {
		this.getStringChildByName(REMARK).setContent(remark, false);
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 * 
	 * @throws ElementDeletedException
	 *             the element deleted exception
	 */
	public void setEmail(String email) throws ElementDeletedException {
		this.getStringChildByName(EMAIL).setContent(email, false);
	}

}
