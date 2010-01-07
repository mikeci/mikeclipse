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
package com.mikeci.eclipse.project.model;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;

import com.mikeci.eclipse.project.facet.FacetPluginConstants;

/**
 * Model POJO class that represents the Web standard tools faceted project to be generated.
 * @author Adam Leggett
 *
 */
public class WstProject {

	private final IProject project;
	private IPath projectDir;
	private IPath srcDir;
	private IPath testDir;
	private IPath libDir;
	private String testDirStr;
	private IPath outputDir;
	private IPath testOutputDir;
	private IPath webContentDir;
	private String packageName;
	private String srcDirStr;
	private String webContentDirStr;
	private boolean antBuild;
	
	public WstProject(IProject project){
		this.project = project;
		this.projectDir = project.getFullPath();
		IVirtualComponent vc = ComponentCore.createComponent( project );
		IVirtualFolder vf = vc.getRootFolder();
		IFolder folder = (IFolder) vf.getUnderlyingFolder();
		webContentDirStr = folder.getProjectRelativePath().toString();
		webContentDir = projectDir.append(folder.getProjectRelativePath());
	}

	public IPath getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(IPath srcDir) {
		String str = srcDir.toString();
		this.srcDirStr =  str.replace("/"+project.getName()+"/", "");
		this.srcDir = srcDir;
	}

	public IPath getTestDir() {
		return testDir;
	}

	public void setTestDir(IPath testDir) {
		this.testDir = testDir;
	}

	public IPath getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(IPath outputDir) {
		this.outputDir = outputDir;
	}

	public IPath getTestOutputDir() {
		return testOutputDir;
	}

	public void setTestOutputDir(IPath testOutputDir) {
		this.testOutputDir = testOutputDir;
	}

	public IPath getWebContentDir() {
		return webContentDir;
	}

	public void setOutputDir(String outputDirStr) {
		this.outputDir = projectDir.append(outputDirStr);
	}
	
	public void setTestOutputDir(String testOutputDirStr) {
		this.testOutputDir = projectDir.append(testOutputDirStr);
	}
	
	public void setWebContentDir(IPath webContentDir) {
		this.webContentDir = webContentDir;
	}
	
	public void setTestDir(String testDirStr) {
		this.testDirStr = testDirStr;
		this.testDir = projectDir.append(testDirStr);
	}
	
	public void setSrcDir(String srcDirStr) {
		this.srcDir = projectDir.append(srcDirStr);
	}
	
	public void setWebContentDir(String webContentDirStr) {
		this.webContentDir = projectDir.append(webContentDirStr);
	}

	public IPath getWebInfDir() {
		return webContentDir.append(FacetPluginConstants.WEB_INF);
	}

	public IPath getWebInfLibDir() {
		return webContentDir.append(FacetPluginConstants.WEB_INF_LIB);
	}

	public IPath getProjectDir() {
		return projectDir;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public String getPackageNameAsFolders() {
		return getPackageName().replace(".", File.separator);
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTestDirStr() {
		return testDirStr;
	}
	
	public String getProjectName(){
		return project.getName();
	}

	public IProject getProject() {
		return project;
	}
	
	public void setLibDir(String libDirStr) {
		this.libDir = projectDir.append(libDirStr);
	}

	public IPath getLibDir() {
		return libDir;
	}
	
	public boolean isDefaultPackage(){
		return (packageName == null || packageName.trim().length()==0);
	}

	public String getSrcDirStr() {
		return srcDirStr;
	}

	public String getWebContentDirStr() {
		return webContentDirStr;
	}

	public boolean isAntBuild() {
		return antBuild;
	}

	public void setAntBuild(boolean antBuild) {
		this.antBuild = antBuild;
	}
	
	
}
