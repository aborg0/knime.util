package com.mind_era.knime.util.preprocessor;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;

/**
 * <code>NodeDialog</code> for the "PreprocessorStart" Node.
 * Common preprocessing for training and evaluate start node.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Mind Eratorsthenes Kft.
 */
public class PreprocessorStartNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring the PreprocessorStart node.
     */
    protected PreprocessorStartNodeDialog() {

    }
}

