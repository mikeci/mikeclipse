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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.mikeci.eclipse.project.model.WstProject;

/**
 * Interface used for abstracting the template engine implementation.
 * @author Adam Leggett
 *
 */
public interface TemplateEngine {
	
	/**
	 * Merges the template represented by the supplied InputStream to the supplied Writer.
	 * @param is
	 * @param project
	 * @param writer
	 * @throws IOException
	 */
	public void mergeTemplate(InputStream is,
			WstProject project, Writer writer)
		throws IOException;

}
