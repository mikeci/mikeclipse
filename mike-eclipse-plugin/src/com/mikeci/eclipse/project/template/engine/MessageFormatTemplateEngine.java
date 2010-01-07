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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.MessageFormat;

import com.mikeci.eclipse.project.model.WstProject;

/**
 * java.text.MessageFormat {@link TemplateEngine} implementation.
 * @author Adam Leggett
 * @deprecated
 */
		
public class MessageFormatTemplateEngine implements TemplateEngine {
	
	/**
	 * {@inheritDoc}
	 */
	public void mergeTemplate(InputStream is, WstProject project, Writer writer)
			throws IOException {
		String projectName = project.getProjectName();
		String packageName = project.getPackageName();
		String pkgStatement = "package " + packageName + ";";
		Object[] params = { projectName, packageName, pkgStatement };
		mergeTemplate(is, params, writer);
	}
	
	/**
	 * Performs the actual merge.
	 * @param is
	 * @param params
	 * @param writer
	 * @throws IOException
	 */
	protected void mergeTemplate(InputStream is, Object[] params, Writer writer)
			throws IOException {
		// load the template
		String content = streamToString(is);
		MessageFormat mf = new MessageFormat(content);
		String result = mf.format(params);
		try{
			writer.write(result);
		}finally{
			is.close();
			writer.close();
		}
	}
	
	/**
	 * Helper method to convert a template InputStream to a String.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String streamToString(InputStream is) throws IOException {
		BufferedReader in = null;
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader input = new InputStreamReader(is);
			in = new BufferedReader(input);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
				buffer.append("\r\n");
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return buffer.toString();
	}

}
