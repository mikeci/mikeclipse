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
package com.mikeci.eclipse.project.facet.action;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import com.mikeci.eclipse.project.facet.DefaultFacetConfig;
import com.mikeci.eclipse.project.facet.FacetPluginConstants;
import com.mikeci.eclipse.project.model.WstProject;
import com.mikeci.eclipse.project.util.EclipseService;

/**
 * Abstract base class for all facet action delegates.
 * @author Adam Leggett
 *
 */
public abstract class AbstractFacetAction implements IDelegate {

	private static final String DEFAULT_JRE_CONTAINER = "org.eclipse.jdt.launching.JRE_CONTAINER";

	/**
	 * {@inheritDoc}
	 */
	public abstract void execute(IProject arg0, IProjectFacetVersion arg1,
			Object arg2, IProgressMonitor arg3) throws CoreException;

	/**
	 * Creates required folders for the template application.
	 * @param eclipseService
	 * @param config
	 * @throws CoreException
	 */
	protected void createRequiredFolders(EclipseService eclipseService,
			WstProject config) throws CoreException {
		// create the eclipse IPath so we can create the folder(s)
		eclipseService.mkdirs(config.getTestDir());
		// Create the custom output location.
		eclipseService.mkdirs(config.getTestOutputDir());
		// Create the custom lib location.
		eclipseService.mkdirs(config.getLibDir());
		// Create the images location.
		eclipseService.mkdirs(config.getWebContentDir().append(
				FacetPluginConstants.DEFAULT_IMAGES_FOLDER));
		// refresh the project
		eclipseService.refreshProject();
		// add the classpath entries
		eclipseService.addSourceEntryToClasspath(config.getTestDir(), config
				.getTestOutputDir());
	}

	/**
	 * Create the simple model that all facet handlers can access.
	 * 
	 * @param project
	 * @param config
	 * @param service
	 * @return a populated WtpProject pojo.
	 */
	protected WstProject createModel(IProject project,
			DefaultFacetConfig config, EclipseService service)
			throws CoreException {
		WstProject wtpProject = new WstProject(project);
		wtpProject.setTestDir(config.getUnitTestFolder());
		wtpProject
				.setTestOutputDir(FacetPluginConstants.DEFAULT_TEST_OUTPUT_FOLDER);
		wtpProject.setSrcDir(service.getSourceKindClasspathEntryPaths().get(0));
		wtpProject.setPackageName(config.getPackageName());
		wtpProject.setLibDir(FacetPluginConstants.DEFAULT_LIB_FOLDER);
		wtpProject.setAntBuild(config.isGenerateAnt());
		return wtpProject;
	}

	/**
	 * sets the default JRE container.
	 * @param service
	 */
	protected void setDefaultJREContainer(EclipseService service)
			throws CoreException {
		IClasspathEntry entry = service
				.getClasspathEntry(DEFAULT_JRE_CONTAINER);
		if (entry == null) {
			service.logInfo("Unable to locate default JRE");
			service.replaceClasspathEntry(DEFAULT_JRE_CONTAINER,
					DEFAULT_JRE_CONTAINER);
		}
	}

}
