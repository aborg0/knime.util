package com.mind_era.knime.util.preprocessor;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.port.PortType;
import org.knime.core.node.workflow.LoopStartNode;

/**
 * This is the model implementation of PreprocessorStart. Common preprocessing
 * for training and evaluate start node.
 *
 * @author Mind Eratorsthenes Kft.
 */
public class PreprocessorStartNodeModel extends NodeModel implements LoopStartNode {
	private int currentIteration = 0;
	private final Set<PreprocessingContent> nonEmptyContents = EnumSet.of(PreprocessingContent.Training);

	/**
	 * Constructor for the node model.
	 */
	protected PreprocessorStartNodeModel() {
		super(new PortType[] { BufferedDataTable.TYPE, BufferedDataTable.TYPE_OPTIONAL,
				BufferedDataTable.TYPE_OPTIONAL }, new PortType[] { BufferedDataTable.TYPE });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
			throws Exception {
		nonEmptyContents.clear();
		for (final PreprocessingContent content : PreprocessingContent.values()) {
			if (inData[content.ordinal()] != null) {
				nonEmptyContents.add(content);
			}
		}
		while ( currentIteration < PreprocessingContent.values().length && inData[currentIteration] == null) {
			++currentIteration;
		}
		if (currentIteration >= PreprocessingContent.values().length) {
			--currentIteration;
		}
		pushFlowVariableString(CommonConstants.preprocessingDataFlowVariableName,
				PreprocessingContent.values()[currentIteration].name());
		return new BufferedDataTable[] { inData[currentIteration++] };
	}

	Set<PreprocessingContent> getAvailableContents() {
		return Collections.unmodifiableSet(nonEmptyContents);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void reset() {
		currentIteration = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DataTableSpec[] configure(final DataTableSpec[] inSpecs) throws InvalidSettingsException {
		nonEmptyContents.clear();
		for (final PreprocessingContent content : PreprocessingContent.values()) {
			if (inSpecs[content.ordinal()] != null) {
				nonEmptyContents.add(content);
			}
		}
		return new DataTableSpec[] { inSpecs[currentIteration] };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {
		// TODO: generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
		// TODO: generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
		// TODO: generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// TODO: generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// TODO: generated method stub
	}

}
