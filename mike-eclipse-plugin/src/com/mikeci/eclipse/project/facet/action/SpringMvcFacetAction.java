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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import com.mikeci.eclipse.project.facet.DefaultFacetConfig;
import com.mikeci.eclipse.project.facet.action.handler.CommonTemplateFacetActionHandler;
import com.mikeci.eclipse.project.facet.action.handler.SpringMvcExampleTemplateFacetActionHandler;
import com.mikeci.eclipse.project.model.WstProject;
import com.mikeci.eclipse.project.template.engine.TemplateEngine;
import com.mikeci.eclipse.project.template.engine.VelocityTemplateEngine;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;
import com.mikeci.eclipse.project.template.service.WstProjectTemplateDescriptorService;
import com.mikeci.eclipse.project.util.EclipseService;

/**
 * SpringMvc action delegate.
 * 
 * @author Adam Leggett
 * 
 */
public class SpringMvcFacetAction extends AbstractFacetAction {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IProject project,
			IProjectFacetVersion projectFacetVersion, Object config,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Installing Mike SpringMvc Facet", 5);
		try {
			// create the services
			EclipseService eclipseService = new EclipseService(ResourcesPlugin
					.getWorkspace(), project, monitor);
			WstProjectTemplateDescriptorService descriptorService = new WstProjectTemplateDescriptorService();
			TemplateEngine templateEngine = new VelocityTemplateEngine();
			WstProject wstProject = createModel(project,
					((DefaultFacetConfig) config), eclipseService);
			// set the JRE container if an eclipse only build
			if (!wstProject.isAntBuild()) {
				setDefaultJREContainer(eclipseService);
			}
			createRequiredFolders(eclipseService, wstProject);
			monitor.worked(1);

			// process common resources
			WstProjectTemplateDescriptor commonDescriptor = descriptorService
					.loadDefinition(WstProjectTemplateDescriptorService.COMMON_DESCRIPTOR);
			monitor.worked(1);

			CommonTemplateFacetActionHandler commonHandler = new CommonTemplateFacetActionHandler(
					eclipseService, wstProject, commonDescriptor,
					templateEngine);
			commonHandler.execute();
			monitor.worked(1);

			// process springmvc resources
			WstProjectTemplateDescriptor springmvcDescriptor = descriptorService
					.loadDefinition(WstProjectTemplateDescriptorService.SPRINGMVC_DESCRIPTOR);
			monitor.worked(1);

			SpringMvcExampleTemplateFacetActionHandler springmvcHandler = new SpringMvcExampleTemplateFacetActionHandler(
					eclipseService, wstProject, springmvcDescriptor,
					templateEngine);
			springmvcHandler.execute();
			monitor.worked(1);

			// refresh the project
			eclipseService.refreshProject();
			monitor.worked(1);
		} catch (Exception ex) {
			throw new CoreException(EclipseService.createStatusForException(ex
					.getMessage(), ex));
		} finally {
			monitor.done();
		}

	}

}
