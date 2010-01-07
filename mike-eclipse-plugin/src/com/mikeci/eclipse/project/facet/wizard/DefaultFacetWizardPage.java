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
package com.mikeci.eclipse.project.facet.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.project.facet.ui.AbstractFacetWizardPage;

import com.mikeci.eclipse.project.facet.DefaultFacetConfig;
import com.mikeci.eclipse.project.facet.FacetPlugin;
import com.mikeci.eclipse.project.facet.FacetPluginConstants;

/**
 * Default wizard page for Mike facets.
 * 
 * @author Adam Leggett
 * 
 */
public final class DefaultFacetWizardPage extends AbstractFacetWizardPage {
	private DefaultFacetConfig config;
	private Text testFolderTextField;
	private Text pkgTextField;
	private Button antBuildCheckbox;

	public DefaultFacetWizardPage() {
		super(FacetPluginConstants.DEFAULT_WIZARD_PAGE_ID);

		setTitle(FacetPluginConstants.DEFAULT_WIZARD_PAGE_TITLE);
		setDescription(FacetPluginConstants.DEFAULT_WIZARD_PAGE_DESC);
		this.setImageDescriptor(FacetPlugin.imageDescriptorFromPlugin(
				FacetPlugin.PLUGIN_ID,
				FacetPluginConstants.DEFAULT_WIZARD_PAGE_IMAGE));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		final Label label1 = new Label(composite, SWT.NONE);
		label1.setLayoutData(gdhfill());
		label1.setText("Unit test folder:");

		this.testFolderTextField = new Text(composite, SWT.BORDER);
		this.testFolderTextField.setLayoutData(gdhfill());
		this.testFolderTextField.setText(this.config.getUnitTestFolder());
		this.testFolderTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		final Label label2 = new Label(composite, SWT.NONE);
		label2.setLayoutData(gdhfill());
		label2.setText("Package:");

		this.pkgTextField = new Text(composite, SWT.BORDER);
		this.pkgTextField.setLayoutData(gdhfill());
		this.pkgTextField.setText(this.config.getPackageName());
		this.pkgTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		this.antBuildCheckbox = new Button(composite, SWT.CHECK);
		this.antBuildCheckbox.setLayoutData(gdhfill());
		this.antBuildCheckbox.setText("Generate Ant build file");
		this.antBuildCheckbox.setSelection(this.config.isGenerateAnt());

		setControl(composite);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setConfig(final Object config) {
		this.config = (DefaultFacetConfig) config;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void transferStateToConfig() {
		this.config.setUnitTestFolder(this.testFolderTextField.getText());
		this.config.setPackageName(this.pkgTextField.getText());
		this.config.setGenerateAnt(this.antBuildCheckbox.getSelection());
	}
	
	/**
	 * Creates the layout data
	 * @return {@link GridData}
	 */
	private static GridData gdhfill() {
		return new GridData(GridData.FILL_HORIZONTAL);
	}

	/**
	 * Called when text field elements are modified.
	 * Validates the field values.
	 */
	private void dialogChanged() {
		String testFolderStr = this.testFolderTextField.getText();
		String pkgStr = this.pkgTextField.getText();

		if (testFolderStr.length() == 0) {
			updateStatus("You must specify a test folder");
			return;
		}
		if (pkgStr.length() == 0) {
			updateStatus("You must specify a package");
			return;
		}
		updateStatus(null);
	}
	
	/**
	 * Updates the status of this wizard page.
	 * @param message the status message.
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
}
