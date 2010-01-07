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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.mikeci.eclipse.project.model.WstProject;
/**
 * Apache Velocity {@link TemplateEngine} implementation.
 * @author Adam Leggett
 */
public class VelocityTemplateEngine implements TemplateEngine {

	// Template related constants
	protected static final String PACKAGE_STATEMENT = "packageStatement";
	protected static final String PACKAGE = "package";
	protected static final String PROJECT_NAME = "projectName";
	protected static final String SRC_DIR = "srcDir";
	protected static final String TEST_SRC_DIR = "testSrcDir";
	protected static final String WEBCONTENT_DIR = "webContentDir";
	
	/**
	 * {@inheritDoc}
	 */
	public void mergeTemplate(InputStream is, WstProject project, Writer writer)
			throws IOException {
		VelocityContext ctx = new VelocityContext();
		ctx.put(PROJECT_NAME, project.getProjectName());
		ctx.put(PACKAGE, project.getPackageName());
		ctx.put(SRC_DIR, project.getSrcDirStr());
		ctx.put(TEST_SRC_DIR, project.getTestDirStr());
		ctx.put(WEBCONTENT_DIR, project.getWebContentDirStr());
		String pkgStatement = "";
		if (project.getPackageName() != null
				&& project.getPackageName().length() > 0) {
			pkgStatement = "package " + project.getPackageName() + ";";
		}
		ctx.put(PACKAGE_STATEMENT, pkgStatement);
		mergeTemplate(is, ctx, writer);

	}
	
	/**
	 * Performs the actual Velocity merge.
	 * @param is
	 * @param ctx
	 * @param writer
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	protected void mergeTemplate(InputStream is, VelocityContext ctx,
			Writer writer) throws IOException {
		try {
			Velocity.evaluate(ctx, writer, "unknown", is);
		} finally {
			is.close();
			writer.close();
		}
	}
}
