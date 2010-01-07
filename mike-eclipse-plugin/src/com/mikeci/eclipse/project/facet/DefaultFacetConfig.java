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
package com.mikeci.eclipse.project.facet;

import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

/**
 * The default configuration options for facets.
 * @author Adam Leggett
 *
 */
public final class DefaultFacetConfig
{
    private String unitTestFolder= FacetPluginConstants.DEFAULT_TEST_FOLDER;
    private String packageName = "";
    private boolean generateAnt = true;

    public String getUnitTestFolder() {
		return unitTestFolder;
	}

	public void setUnitTestFolder(String unitTestFolder) {
		this.unitTestFolder = unitTestFolder;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isGenerateAnt() {
		return generateAnt;
	}

	public void setGenerateAnt(boolean generateAnt) {
		this.generateAnt = generateAnt;
	}


	public static final class Factory implements IActionConfigFactory
    {
        public Object create()
        {
            return new DefaultFacetConfig();
        }
    }
}
