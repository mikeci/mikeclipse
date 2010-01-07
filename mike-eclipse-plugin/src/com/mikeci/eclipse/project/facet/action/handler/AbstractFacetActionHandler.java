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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.mikeci.eclipse.project.model.WstProject;
import com.mikeci.eclipse.project.template.engine.TemplateEngine;
import com.mikeci.eclipse.project.template.model.FileResource;
import com.mikeci.eclipse.project.template.model.FileResourceContainer;
import com.mikeci.eclipse.project.template.model.TemplateFileResource;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;
import com.mikeci.eclipse.project.util.EclipseService;

/**
 * Base class for all FacetActionHandlers
 * 
 * @author Adam Leggett
 * 
 */
public abstract class AbstractFacetActionHandler implements FacetActionHandler {

	protected final EclipseService eclipseService;
	protected final WstProjectTemplateDescriptor wstTemplateProjectDescriptor;
	protected final WstProject wstProject;
	protected final TemplateEngine templateEngine;

	protected static final String PLUGIN_RESOURCES = "resources/templates";

	public AbstractFacetActionHandler(EclipseService eclipseService,
			WstProject wstProject,
			WstProjectTemplateDescriptor projectDescriptor,
			TemplateEngine templateEngine) {
		this.eclipseService = eclipseService;
		this.wstTemplateProjectDescriptor = projectDescriptor;
		this.wstProject = wstProject;
		this.templateEngine = templateEngine;
	}

	/**
	 * Abstract method for all action handlers to implement
	 */
	public abstract void execute() throws CoreException;
	
	/**
	 * Implementations should override this method to return an identifier
	 * indicating the location of the templates that reside under resources/templates
	 * inside this bundle.
	 * @return String
	 */
	public abstract String getTemplateProjectType();

	/**
	 * Copy all binary files into the generated project.
	 * @throws CoreException
	 */
	protected void copyBinaries() throws CoreException {
		copyLibFolderBinaries();
		copyWebContentFolderBinaries();
		copyWebInfLibFolderBinaries();
	}

	/**
	 * Copy all 'lib' folder binary files into the generated project.
	 * @throws CoreException
	 */
	protected void copyLibFolderBinaries() throws CoreException {
		copyBinaries(wstTemplateProjectDescriptor.getLibDir(), wstProject
				.getLibDir(), "lib", true);
	}
	
	/**
	 * Copy all 'WEB-INF/lib' folder binary files into the generated project.
	 * @throws CoreException
	 */
	protected void copyWebInfLibFolderBinaries() throws CoreException {
		copyBinaries(wstTemplateProjectDescriptor.getWebInfLibDir(), wstProject
				.getWebInfLibDir(), "WebContent/WEB-INF/lib", false);
	}

	/**
	 * Copy all content root folder binary files into the generated project.
	 * @throws CoreException
	 */
	protected void copyWebContentFolderBinaries() throws CoreException {
		copyBinaries(wstTemplateProjectDescriptor.getWebContentDir(),
				wstProject.getWebContentDir(), "WebContent", false);
	}
	
	/**
	 * Performs the actual copying.
	 * @param con
	 * @param target
	 * @param dirName
	 * @param appendToClasspath
	 * @throws CoreException
	 */
	@SuppressWarnings("unchecked")
	protected void copyBinaries(FileResourceContainer con, IPath target,
			String dirName, boolean appendToClasspath) throws CoreException {
		String pluginDir = PLUGIN_RESOURCES + "/" + getTemplateProjectType()
				+ "/" + dirName;
		List<FileResource> l = con.getFileResources();
		if (l != null) {
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				FileResource f = (FileResource) iterator.next();
				if (!(f instanceof TemplateFileResource)) {
					IWorkspace workspace = eclipseService.getWorkspace();
					IFolder folder = workspace.getRoot().getFolder(target);
					eclipseService.copyFromPlugin(new Path(pluginDir + "/"
							+ f.getName()), folder.getFile(f.getName()));
					if (appendToClasspath) {
						eclipseService.addNonExportedLibEntryToClasspath(target
								.append(f.getName()));
					}
				}
			}
		}
	}

}
