/**
 * 
 */
package com.mind_era.knime.util.preprocessor;

/**
 * Data types of preprocessing.
 * 
 * @author Gabor Bakos
 */
public enum PreprocessingContent {
	Training, Validation, Prediction;
}

interface CommonConstants {
	static final String preprocessingDataFlowVariableName = "preprocessing data";
}