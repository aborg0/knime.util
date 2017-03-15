package com.mind_era.knime.util.preprocessor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;

import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.inactive.InactiveBranchPortObject;
import org.knime.core.node.port.inactive.InactiveBranchPortObjectSpec;
import org.knime.core.node.workflow.LoopEndNode;
import org.knime.core.node.workflow.LoopStartNode;

/**
 * This is the model implementation of PreprocessorEnd. Common preprocessing for
 * training and evaluate end node.
 *
 * @author Mind Eratosthenes Kft.
 */
public class PreprocessorEndNodeModel extends NodeModel implements LoopEndNode {
	// the logger instance
	//private static final NodeLogger logger = NodeLogger.getLogger(PreprocessorEndNodeModel.class);
	private final EnumMap<PreprocessingContent, BufferedDataTable> contents = new EnumMap<>(PreprocessingContent.class);

	/**
	 * Constructor for the node model.
	 */
	protected PreprocessorEndNodeModel() {
		super(new PortType[] { BufferedDataTable.TYPE }, new PortType[] { BufferedDataTable.TYPE,
				BufferedDataTable.TYPE_OPTIONAL, BufferedDataTable.TYPE_OPTIONAL });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PortObject[] execute(final PortObject[] inData, final ExecutionContext exec) throws Exception {
		final LoopStartNode rawStartNode = getLoopStartNode();
		if (rawStartNode instanceof PreprocessorStartNodeModel) {
			final PreprocessorStartNodeModel startNode = (PreprocessorStartNodeModel) rawStartNode;
			final PreprocessingContent preprocessingContent = PreprocessingContent.valueOf(getAvailableFlowVariables()
					.get(CommonConstants.preprocessingDataFlowVariableName).getStringValue());
			contents.put(preprocessingContent, exec.createWrappedTable((BufferedDataTable) inData[0]));
			final Set<PreprocessingContent> contentsAvailable = startNode.getAvailableContents();
			if (preprocessingContent
					.compareTo(PreprocessingContent.values()[PreprocessingContent.values().length - 1]) < 0) {
				if (hasLater(contentsAvailable, preprocessingContent)) {
					continueLoop();
				}
			}
			final PortObject[] tables = new PortObject[PreprocessingContent.values().length];
			for (final PreprocessingContent pc : PreprocessingContent.values()) {
				final BufferedDataTable ret = contents.get(pc);
				if (ret == null) {
					tables[pc.ordinal()] = InactiveBranchPortObject.INSTANCE;
				} else {
					tables[pc.ordinal()] = ret;
				}
			}
			return tables;
		} else {
			throw new IllegalStateException(
					"This node should be used only with Preprocessor Start nodes. It is used currently with: "
							+ rawStartNode);
		}
	}

	/**
	 * @param contentsAvailable
	 * @param preprocessingContent
	 * @return Checks whether there is anything in
	 *         {@link PreprocessingContent#values()} after
	 *         {@code preprocessingContent} that is also in {@code contentsAvailable}.
	 */
	private boolean hasLater(Set<PreprocessingContent> contentsAvailable, PreprocessingContent preprocessingContent) {
		boolean found = false;
		for (final PreprocessingContent pv : PreprocessingContent.values()) {
			if (found && contentsAvailable.contains(pv)) {
				return true;
			}
			if (pv == preprocessingContent) {
				found = true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void reset() {
		contents.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PortObjectSpec[] configure(final PortObjectSpec[] inSpecs) throws InvalidSettingsException {
		// TODO: check if user settings are available, fit to the incoming
		// table structure, and the incoming types are feasible for the node
		// to execute. If the node can execute in its current state return
		// the spec of its output data table(s) (if you can, otherwise an array
		// with null elements), or throw an exception with a useful user message
		final LoopStartNode rawStartNode = getLoopStartNode();
		if (rawStartNode instanceof PreprocessorStartNodeModel) {
			final Set<PreprocessingContent> available = ((PreprocessorStartNodeModel) rawStartNode)
					.getAvailableContents();
			final PortObjectSpec[] ret = new PortObjectSpec[PreprocessingContent.values().length];
			Arrays.fill(ret, InactiveBranchPortObjectSpec.INSTANCE);
			for (final PreprocessingContent content : available) {
				ret[content.ordinal()] = inSpecs[0];
			}
			return ret;
		} else {
			throw new IllegalStateException(
					"This node should be used only with Preprocessor Start nodes. It is used currently with: "
							+ rawStartNode);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {
		// TODO save user settings to the config object.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
		// TODO load (valid) settings from the config object.
		// It can be safely assumed that the settings are valided by the
		// method below.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
		// TODO check if the settings could be applied to our model
		// e.g. if the count is in a certain range (which is ensured by the
		// SettingsModel).
		// Do not actually set any values of any member variables.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// TODO load internal data.
		// Everything handed to output ports is loaded automatically (data
		// returned by the execute method, models loaded in loadModelContent,
		// and user settings set through loadSettingsFrom - is all taken care
		// of). Load here only the other internals that need to be restored
		// (e.g. data used by the views).
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// TODO save internal models.
		// Everything written to output ports is saved automatically (data
		// returned by the execute method, models saved in the saveModelContent,
		// and user settings saved through saveSettingsTo - is all taken care
		// of). Save here only the other internals that need to be preserved
		// (e.g. data used by the views).
	}

}
