package com.mind_era.knime.util.preprocessor;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "PreprocessorStart" Node.
 * Common preprocessing for training and evaluate start node.
 *
 * @author Mind Eratorsthenes Kft.
 */
public class PreprocessorStartNodeFactory 
        extends NodeFactory<PreprocessorStartNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PreprocessorStartNodeModel createNodeModel() {
        return new PreprocessorStartNodeModel();
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
    public NodeView<PreprocessorStartNodeModel> createNodeView(final int viewIndex,
            final PreprocessorStartNodeModel nodeModel) {
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
        return new PreprocessorStartNodeDialog();
    }

}

