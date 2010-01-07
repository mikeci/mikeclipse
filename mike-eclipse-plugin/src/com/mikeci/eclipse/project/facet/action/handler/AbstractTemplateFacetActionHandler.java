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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.mikeci.eclipse.project.model.WstProject;
import com.mikeci.eclipse.project.template.engine.TemplateEngine;
import com.mikeci.eclipse.project.template.model.FileResource;
import com.mikeci.eclipse.project.template.model.FileResourceContainer;
import com.mikeci.eclipse.project.template.model.TemplateFileResource;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;
import com.mikeci.eclipse.project.util.EclipseService;

/**
 * Base class for all template based FacetActionHandlers
 * 
 * @author Adam Leggett
 * 
 */
public abstract class AbstractTemplateFacetActionHandler extends
		AbstractFacetActionHandler {

	private static final String PROJECT_NAME_TOKEN = "${projectName}";
	private static final String TEMPLATE_EXT = ".vm";

	public AbstractTemplateFacetActionHandler(EclipseService eclipseService,
			WstProject wstProject, WstProjectTemplateDescriptor desc,
			TemplateEngine templateEngine) {
		super(eclipseService, wstProject, desc, templateEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws CoreException {
		copyBinaries();
		mergeAllTemplates();
	}

	/**
	 * Merges all templates required by the selected Facet.
	 * @throws CoreException
	 */
	protected void mergeAllTemplates() throws CoreException {
		if (wstProject.isAntBuild()) {
			mergeProjectFolderTemplates();
		}
		mergeSrcFolderTemplates();
		mergeTestFolderTemplates();
		mergeWebContentFolderTemplates();
		mergeWebInfFolderTemplates();
	}
	
	/**
	 * Merge all templates into the generated project root dir.
	 * @throws CoreException
	 */
	protected void mergeProjectFolderTemplates() throws CoreException {
		File folderRoot = eclipseService.getProjectAbsolutePath();
		mergeTemplates(wstTemplateProjectDescriptor.getProjectDir(), "",
				folderRoot);
	}
	
	/**
	 * Merge all templates into the generated project source dir.
	 * @throws CoreException
	 */
	protected void mergeSrcFolderTemplates() throws CoreException {
		File folderRoot = eclipseService.toAbsolutePath(wstProject.getSrcDir());
		mergeTemplates(wstTemplateProjectDescriptor.getSrcDir(), "src",
				folderRoot);
	}

	/**
	 * Merge all templates into the generated project test source dir.
	 * @throws CoreException
	 */
	protected void mergeTestFolderTemplates() throws CoreException {
		File folderRoot = eclipseService
				.toAbsolutePath(wstProject.getTestDir());
		mergeTemplates(wstTemplateProjectDescriptor.getTestDir(), "test",
				folderRoot);
	}
	
	/**
	 * Merge all templates into the generated project content dir.
	 * @throws CoreException
	 */
	protected void mergeWebContentFolderTemplates() throws CoreException {
		File folderRoot = eclipseService.toAbsolutePath(wstProject
				.getWebContentDir());
		mergeTemplates(wstTemplateProjectDescriptor.getWebContentDir(),
				"WebContent", folderRoot);
	}
	
	/**
	 * Merge all templates into the generated project WEB-INF dir.
	 * @throws CoreException
	 */
	protected void mergeWebInfFolderTemplates() throws CoreException {
		File folderRoot = eclipseService.toAbsolutePath(wstProject
				.getWebInfDir());
		mergeTemplates(wstTemplateProjectDescriptor.getWebInfDir(),
				"WebContent/WEB-INF", folderRoot);
	}

	/**
	 * Performs a merge.
	 * @param con
	 * @param subfolder
	 * @param destDir
	 * @throws CoreException
	 */
	protected void mergeTemplates(FileResourceContainer con, String subfolder,
			File destDir) throws CoreException {
		String templatesDir = PLUGIN_RESOURCES + "/" + getTemplateProjectType()
				+ "/" + subfolder;
		List<FileResource> l = con.getFileResources();
		if (l != null) {
			for (Iterator<FileResource> iterator = l.iterator(); iterator
					.hasNext();) {
				FileResource f = iterator.next();
				if (f instanceof TemplateFileResource) {
					TemplateFileResource res = (TemplateFileResource) f;
					String templateName = templatesDir + "/" + res.getName()
							+ TEMPLATE_EXT;
					File outputPath = null;
					String filename = res.getName();
					if (filename.contains(PROJECT_NAME_TOKEN)) {
						filename = substituteProjectNameInFileName(filename);
					}
					if (f.isInPackage()) {
						String pkgAsFolders = wstProject
								.getPackageNameAsFolders();
						outputPath = new File(destDir, pkgAsFolders + "/"
								+ filename);
					} else {
						outputPath = new File(destDir, filename);
					}
					mergeTemplate(templateName, outputPath);
				}
			}
		}
	}

	/**
	 * Perform the merging using the injected {@link TemplateEngine}
	 * @param templateName
	 * @param context
	 * @param path
	 */
	protected void mergeTemplate(String templateName, File path)
			throws CoreException {
		eclipseService.logInfo("Merging template " + templateName + " to "
				+ path);
		FileWriter writer = null;
		try {
			if (!path.exists()) {
				path.getParentFile().mkdirs();
			}
			InputStream is = eclipseService.getPluginResource(templateName);
			eclipseService.logInfo("Loaded resource " + templateName
					+ " as stream " + is);
			writer = new FileWriter(path);
			templateEngine.mergeTemplate(is, wstProject, writer);
			writer.close();

		} catch (Exception ex) {
			throw new CoreException(EclipseService.createStatusForException(
					"Error creating file using template " + templateName, ex));
		} finally {
			try {
				writer.close();
			} catch (IOException ex) {
				throw new CoreException(EclipseService
						.createStatusForException(
								"Error creating file using template "
										+ templateName, ex));
			}
		}

	}

	/**
	 * Substitutes the project name in the filename
	 * 
	 * @param fileToRename
	 * @return token substituted name
	 */
	private String substituteProjectNameInFileName(String fileToRename) {
		return fileToRename.replace(PROJECT_NAME_TOKEN, wstProject
				.getProjectName());
	}
}
