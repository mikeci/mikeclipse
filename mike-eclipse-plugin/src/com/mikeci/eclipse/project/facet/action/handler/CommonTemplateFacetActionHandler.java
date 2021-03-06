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
package com.mikeci.eclipse.project.facet.action.handler;

import com.mikeci.eclipse.project.model.WstProject;
import com.mikeci.eclipse.project.template.engine.TemplateEngine;
import com.mikeci.eclipse.project.template.model.WstProjectTemplateDescriptor;
import com.mikeci.eclipse.project.util.EclipseService;

/**
 * {@link AbstractTemplateFacetActionHandler} implementation for all common templates.
 * @author Adam Leggett
 *
 */
public class CommonTemplateFacetActionHandler extends
		AbstractTemplateFacetActionHandler {

	private static final String COMMON_TEMPLATE_TYPE = "common";


	public CommonTemplateFacetActionHandler(EclipseService eclipseService,
			WstProject wstProject, WstProjectTemplateDescriptor desc,
			TemplateEngine templateEngine) {
		super(eclipseService, wstProject, desc, templateEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTemplateProjectType() {
		return COMMON_TEMPLATE_TYPE;
	}


}
