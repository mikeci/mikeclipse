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
package com.mikeci.eclipse.project.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.ClasspathEntry;
import org.osgi.framework.Bundle;

import com.mikeci.eclipse.project.facet.FacetPlugin;
import com.mikeci.eclipse.project.facet.action.handler.J2EEFacetInstallDelegateAdapter;

/**
 * Service class that contains useful wrapper methods for Eclipse IDE specifics.
 * 
 * @author Adam Leggett
 * 
 */
@SuppressWarnings("restriction")
public class EclipseService {

	private final IWorkspace workspace;
	private final IProject project;
	private final IJavaProject jproject;
	private final IProgressMonitor monitor;

	public EclipseService(IWorkspace workspace, IProject project,
			IProgressMonitor monitor) {
		this.workspace = workspace;
		this.project = project;
		jproject = JavaCore.create(project);
		this.monitor = monitor;
	}

	/**
	 * Creates dirs for the supplied path.
	 * 
	 * @param path
	 * @return true if dirs are created.
	 */
	public boolean mkdirs(IPath path) {
		return toAbsolutePath(path).mkdirs();
	}

	/**
	 * Gets an absolute java.io.File for the supplied path.
	 * 
	 * @param path
	 * @return File
	 */
	public File toAbsolutePath(IPath path) {
		return workspace.getRoot().getFolder(path).getLocation().toFile();
	}

	/**
	 * Gets an absolute java.io.File for this eclipse project.
	 * 
	 * @param path
	 * @return File
	 */
	public File getProjectAbsolutePath() {
		return project.getLocation().toFile();
	}

	/**
	 * Refreshes the eclipse project.
	 * @throws CoreException
	 */
	public void refreshProject() throws CoreException {
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	/**
	 * Adds the supplied source entry, with no exclusions, to the project
	 * classpath.
	 * 
	 * @param path
	 * @param outPath
	 * @throws CoreException
	 */
	public void addSourceEntryToClasspath(IPath path, IPath outPath)
			throws CoreException {
		IClasspathEntry entry = JavaCore.newSourceEntry(path,
				ClasspathEntry.EXCLUDE_NONE, outPath);
		J2EEFacetInstallDelegateAdapter.addToClasspath(jproject, entry);
	}

	/**
	 * Adds the supplied non exported lib entry to the project classpath.
	 * 
	 * @param path
	 * @throws CoreException
	 */
	public void addNonExportedLibEntryToClasspath(IPath path)
			throws CoreException {
		IClasspathEntry entry = JavaCore.newLibraryEntry(path, null, null);
		J2EEFacetInstallDelegateAdapter.addToClasspath(jproject, entry);
	}

	/**
	 * Removes an entry from project classpath and replaces it with the
	 * provided one.
	 * 
	 * @param searchForPath
	 * @param replaceWithPath
	 * @throws CoreException
	 */
	public void replaceClasspathEntry(String searchForPath,
			String replaceWithPath) throws CoreException {
		List<IClasspathEntry> l = new ArrayList<IClasspathEntry>();
		IClasspathEntry[] entries = jproject.getRawClasspath();
		boolean match = false;
		for (int i = 0; i < entries.length; i++) {
			IPath path = entries[i].getPath();
			if (!(path.toString().equals(searchForPath))
					&& !(path.toString().startsWith(searchForPath))) {
				l.add(entries[i]);
			} else {
				match = true;
			}
		}
		IClasspathEntry[] newRawClasspath = (IClasspathEntry[]) l
				.toArray(new IClasspathEntry[l.size()]);
		jproject.setRawClasspath(newRawClasspath, monitor);
		if (match) {
			addContainerEntryToClasspath(new Path(replaceWithPath));
		}
	}

	/**
	 * Add the supplied container entry to the project classpath.
	 * 
	 * @param path
	 * @throws CoreException
	 */
	public void addContainerEntryToClasspath(IPath path) throws CoreException {
		IClasspathEntry entry = JavaCore.newContainerEntry(path);
		J2EEFacetInstallDelegateAdapter.addToClasspath(jproject, entry);
	}

	/**
	 * Gets all source kind classpath entry paths
	 * @return List<IPath>
	 * @throws CoreException
	 */
	public List<IPath> getSourceKindClasspathEntryPaths() throws CoreException {
		List<IPath> l = new ArrayList<IPath>();
		IClasspathEntry[] entries = jproject.getRawClasspath();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				l.add(entries[i].getPath());
			}
		}
		return l;
	}
	
	/**
	 * Gets the first classpath entry with the supplied path.
	 * @param pathStr
	 * @return {@link IClasspathEntry}
	 * @throws CoreException
	 */
	public IClasspathEntry getClasspathEntry(String pathStr)
			throws CoreException {
		IClasspathEntry[] entries = jproject.getRawClasspath();
		IClasspathEntry entry = null;
		for (int i = 0; i < entries.length; i++) {
			IPath path = entries[i].getPath();
			if (path.toString().equals(pathStr)) {
				entry = entries[i];
				break;
			}
		}
		return entry;
	}

	/**
	 * Copies a resource from within the plugin to a destination in the
	 * workspace.
	 * 
	 * @param src the path of the resource within the plugin
	 * @param dest the destination path within the workspace
	 */

	public void copyFromPlugin(final IPath src, final IFile dest)
			throws CoreException {
		try {
			final Bundle bundle = FacetPlugin.getDefault().getBundle();
			final InputStream in = FileLocator.openStream(bundle, src, false);
			dest.create(in, true, null);
		} catch (IOException ex) {
			throw new CoreException(
					EclipseService.createStatusForException(
							"Error copying " + src + "to " + dest
							, ex));
		}
	}

	/**
	 * Gets the specified plugin resource as a stream
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public InputStream getPluginResource(final String src) throws IOException {
		final Bundle bundle = FacetPlugin.getDefault().getBundle();
		return FileLocator.openStream(bundle, new Path(src), false);
	}

	public IWorkspace getWorkspace() {
		return workspace;
	}

	public IProject getProject() {
		return project;
	}

	public IJavaProject getJavaProject() {
		return jproject;
	}

	public IProgressMonitor getMonitor() {
		return monitor;
	}
	
	/**
	 * Logs an info status message using the plugin logger.
	 * @param message
	 */
	public void logInfo(String message) {
		FacetPlugin.getDefault().getLog()
				.log(new Status(Status.INFO, FacetPlugin.PLUGIN_ID, 0,
								message, null));
	}
	
	/**
	 * helper method to return an IStatus for a CoreException
	 * @param message
	 * @param ex
	 * @return {@link IStatus}
	 */
	public static IStatus createStatusForException(String message, Exception ex){
		return new Status(IStatus.ERROR, FacetPlugin.PLUGIN_ID,
				0, message, ex);
	}

}
