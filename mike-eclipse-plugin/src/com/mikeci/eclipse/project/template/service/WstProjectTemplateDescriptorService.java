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
package com.mikeci.eclipse.project.template.service;

import java.io.InputStream;

import com.mikeci.eclipse.project.template.model.FileResource;
import com.mikeci.eclipse.project.template.model.FileResourceContainer;
import com.mikeci.eclipse.project.template.model.LibDir;
import com.mikeci.eclipse.project.template.model.SrcDir;
import com.mikeci.eclipse.project.template.model.TemplateFileResource;
import com.mikeci.eclipse.project.template.model.TestDir;
import com.mikeci.eclipse.project.template.model.WebContentDir;
import com.mikeci.eclipse.project.template.model.WebInfDir;
import com.mikeci.eclipse.project.template.model.WebInfLibDir;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Service class that loads template project xml-based descriptors from this bundle and
 * parses them into a model using XStream.
 * 
 * @author Adam Leggett
 * 
 */
public class WstProjectTemplateDescriptorService {

	public static final String COMMON_DESCRIPTOR = "common-project.xml";
	public static final String SPRINGMVC_DESCRIPTOR = "springmvc-project.xml";
	public static final String STRUTS2_DESCRIPTOR = "struts2-project.xml";
	private final XStream xs;

	public WstProjectTemplateDescriptorService() {
		xs = new XStream(new DomDriver());
	}

	/**
	 * Loads the descriptor for a template project.
	 * 
	 * @param descriptorPath
	 * @return
	 */
	public WstProjectTemplateDescriptor loadDefinition(String descriptor) {
		String descriptorPath = this.getClass().getPackage().getName().replace(
				".", "/")
				+ "/" + descriptor;
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				descriptorPath);
		xs.alias("project", WstProjectTemplateDescriptor.class);
		xs.addImplicitCollection(FileResourceContainer.class, "fileResources");
		xs.alias("srcDir", SrcDir.class);
		xs.alias("testDir", TestDir.class);
		xs.alias("libDir", LibDir.class);
		xs.alias("webContentDir", WebContentDir.class);
		xs.alias("webInfDir", WebInfDir.class);
		xs.alias("webInfLibDir", WebInfLibDir.class);
		xs.alias("template", TemplateFileResource.class);
		xs.alias("bin", FileResource.class);
		WstProjectTemplateDescriptor def = (WstProjectTemplateDescriptor) xs
				.fromXML(is);
		return def;
	}
}
