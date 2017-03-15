package com.mind_era.knime.util.preprocessor;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;

/**
 * <code>NodeDialog</code> for the "PreprocessorEnd" Node. Common preprocessing
 * for training and evaluate end node.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more
 * complex dialog please derive directly from
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Mind Eratosthenes Kft.
 */
public class PreprocessorEndNodeDialog extends DefaultNodeSettingsPane {

	/**
	 * New pane for configuring PreprocessorEnd node dialog. This is just a
	 * suggestion to demonstrate possible default dialog components.
	 */
	protected PreprocessorEndNodeDialog() {
		super();

	}
}
