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

// TODO: Auto-generated Javadoc
/**
 * The Interface ModificationListener is notified from a PWList, when
 * it has been modified. Before, the Listener must of course be registered
 * with the PWList.
 *
 * @author Jan Petranek
 */
public interface ModificationListener {

	/**
	 * Notify this object, when the event source has been modified.
	 *
	 * @param source the source
	 */
	void modified(PWList source);

}
