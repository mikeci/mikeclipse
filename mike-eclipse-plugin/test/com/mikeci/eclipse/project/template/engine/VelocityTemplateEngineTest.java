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
package com.mikeci.eclipse.project.template.engine;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Test;

public class VelocityTemplateEngineTest {
	
	VelocityTemplateEngine classUnderTest;
	File tmpDir;

	@Before
	public void setUp() throws Exception {
		classUnderTest = new VelocityTemplateEngine();
		tmpDir = new File(System.getProperty("java.io.tmpdir"));
	}

	@Test
	public void testMergeTemplateInputStreamVelocityContextWriter() throws Exception{
		VelocityContext ctx = new VelocityContext();
		ctx.put(VelocityTemplateEngine.PROJECT_NAME, "myproject");
		ctx.put(VelocityTemplateEngine.PACKAGE, "com.acme");
		ctx.put(VelocityTemplateEngine.PACKAGE_STATEMENT, "package com.acme;");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/test.xml.vm");
		File f = new File(tmpDir,"test.xml");
		FileWriter fw = new FileWriter(f);
		classUnderTest.mergeTemplate(is, ctx, fw);
		assertTrue(f.exists());
	}

}
