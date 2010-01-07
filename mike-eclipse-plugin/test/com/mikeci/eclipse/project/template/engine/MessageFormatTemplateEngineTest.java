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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class MessageFormatTemplateEngineTest {
	
	MessageFormatTemplateEngine classUnderTest;
	File tmpDir; 

	@Before
	public void setUp(){
		classUnderTest = new MessageFormatTemplateEngine();
		tmpDir = new File(System.getProperty("java.io.tmpdir"));
	}
	
	@Test
	public void testMergeTemplate() throws Exception{
		String template = "templates/build.xml.tpl";
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(template);
		Object params[] = {"ex","com.acme","package com.acme;"};
		File f = new File(tmpDir,"build.xml");
		FileWriter writer = new FileWriter(f);
		classUnderTest.mergeTemplate(is, params, writer);
		assertTrue(f.exists());
		
	}

}
