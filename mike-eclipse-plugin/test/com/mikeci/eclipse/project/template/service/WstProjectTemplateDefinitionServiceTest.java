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
package com.mikeci.eclipse.project.template.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mikeci.eclipse.project.template.model.FileResource;
import com.mikeci.eclipse.project.template.model.TemplateFileResource;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;


public class WstProjectTemplateDefinitionServiceTest {

	WstProjectTemplateDescriptorService classUnderTest;

	@Before
	public void setUp() {
		classUnderTest = new WstProjectTemplateDescriptorService();
	}

	@Test
	public void testLoadDefinition() {
		String descriptorPath = "project.xml";
		WstProjectTemplateDescriptor d = classUnderTest
				.loadDefinition(descriptorPath);
		assertNotNull("template def not null", d);
		// get the src dir text file
		TemplateFileResource f = (TemplateFileResource) d.getSrcDir()
				.getFileResources().get(0);
		assertEquals(1, d.getSrcDir().getFileResources().size());
		assertEquals("HomeController.java", f.getName());
		assertTrue(f.isInPackage());
		FileResource jar = d.getWebInfLibDir().getFileResources().get(1);
		assertEquals("spring-core-2.5.6.jar", jar.getName());
		assertFalse(jar.isInPackage());
	}

}
