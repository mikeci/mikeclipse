/*
 * Copyright (C) 2009 The Ultimate People Company Ltd ("UPCO").
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mikeci.eclipse.project.facet.action.handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jst.j2ee.project.facet.J2EEFacetInstallDelegate;

/**
 * Adapter class to allow FacetActionHandler impl's to avoid extending J2EEFacetInstallDelegate.
 * @author Adam Leggett
 *
 */
public class J2EEFacetInstallDelegateAdapter extends J2EEFacetInstallDelegate {
	
	/**
	 * Delegate method that adapts J2EEFacetInstallDelegate.addToClasspath
	 * @param jproj
	 * @param entry
	 * @throws CoreException
	 */
	public static void addToClasspath(IJavaProject jproj, IClasspathEntry entry)
			throws CoreException {
		J2EEFacetInstallDelegate.addToClasspath(jproj, entry);
	}

}
