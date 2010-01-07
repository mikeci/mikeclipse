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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author Adam Leggett
 *
 */
public final class FacetPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.mikeci.wst.project.facet";

	private static FacetPlugin plugin;

	public FacetPlugin() {
		plugin = this;
	}

	public static FacetPlugin getInstance() {
		return plugin;
	}

	public static void log(final Exception e) {
		final String msg = e.getMessage() + "";
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, e));
	}

	public static void log(final IStatus status) {
		getInstance().getLog().log(status);
	}

	public static void log(final String msg) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
	}

	public static IStatus createErrorStatus(final String msg) {
		return createErrorStatus(msg, null);
	}

	public static IStatus createErrorStatus(final String msg, final Exception e) {
		return new Status(IStatus.ERROR, PLUGIN_ID, 0, msg, e);
	}

	/**
	 * Returns the shared instance
	 * @return the shared instance
	 */
	public static FacetPlugin getDefault() {
		return plugin;
	}

}
