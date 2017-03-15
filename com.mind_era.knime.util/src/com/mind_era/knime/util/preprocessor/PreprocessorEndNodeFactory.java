package com.mind_era.knime.util.preprocessor;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "PreprocessorEnd" Node.
 * Common preprocessing for training and evaluate end node.
 *
 * @author Mind Eratosthenes Kft.
 */
public class PreprocessorEndNodeFactory 
        extends NodeFactory<PreprocessorEndNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PreprocessorEndNodeModel createNodeModel() {
        return new PreprocessorEndNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<PreprocessorEndNodeModel> createNodeView(final int viewIndex,
            final PreprocessorEndNodeModel nodeModel) {
    	throw new IndexOutOfBoundsException("No views: " + viewIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new PreprocessorEndNodeDialog();
    }

}

