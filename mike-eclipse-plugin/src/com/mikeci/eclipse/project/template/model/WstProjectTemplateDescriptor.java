/*
 * Copyright (C) 2008 The Ultimate People Company Ltd ("UPCO").
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
package com.mikeci.eclipse.project.template.model;

/**
 * POJO representation of a template descriptor for a Mike faceted project.
 * @author Adam Leggett
 *
 */
public class WstProjectTemplateDescriptor {

	private ProjectDir projectDir;
	private SrcDir srcDir;
	private TestDir testDir;
	private LibDir libDir;
	private WebContentDir webContentDir;
	private WebInfDir webInfDir;
	private WebInfLibDir webInfLibDir;
	
	public ProjectDir getProjectDir() {
		return projectDir;
	}
	public void setProjectDir(ProjectDir projectDir) {
		this.projectDir = projectDir;
	}
	public SrcDir getSrcDir() {
		return srcDir;
	}
	public void setSrcDir(SrcDir srcDir) {
		this.srcDir = srcDir;
	}
	public TestDir getTestDir() {
		return testDir;
	}
	public void setTestDir(TestDir testDir) {
		this.testDir = testDir;
	}
	public WebContentDir getWebContentDir() {
		return webContentDir;
	}
	public void setWebContentDir(WebContentDir webContentDir) {
		this.webContentDir = webContentDir;
	}
	public WebInfDir getWebInfDir() {
		return webInfDir;
	}
	public void setWebInfDir(WebInfDir webInfDir) {
		this.webInfDir = webInfDir;
	}
	public WebInfLibDir getWebInfLibDir() {
		return webInfLibDir;
	}
	public void setWebInfLibDir(WebInfLibDir webInfLibDir) {
		this.webInfLibDir = webInfLibDir;
	}
	public LibDir getLibDir() {
		return libDir;
	}
	public void setLibDir(LibDir libDir) {
		this.libDir = libDir;
	}
	
	
}
