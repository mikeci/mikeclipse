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
package com.mikeci.eclipse.project.facet;

/**
 * Constants interface.
 * 
 * @author Adam Leggett
 */
public interface FacetPluginConstants {

	static final String DEFAULT_TEST_FOLDER = "test";
	static final String DEFAULT_LIB_FOLDER = "lib";
	static final String DEFAULT_TEST_OUTPUT_FOLDER = "build/test-classes";
	static final String DEFAULT_IMAGES_FOLDER = "images";
	static final String WEB_INF = "WEB-INF";
	static final String WEB_INF_LIB = "WEB-INF/lib";
	static final String DEFAULT_WIZARD_PAGE_ID = "mike.examples.default.install.page";
	static final String DEFAULT_WIZARD_PAGE_TITLE = "Mike project configuration";
	static final String DEFAULT_WIZARD_PAGE_DESC = "Configure the location for your unit tests, your Java source code package and optionally generate a 'Mike-friendly' Ant build file.\r\n";
	static final String DEFAULT_WIZARD_PAGE_IMAGE = "icons/mike-dialog.gif";

}
