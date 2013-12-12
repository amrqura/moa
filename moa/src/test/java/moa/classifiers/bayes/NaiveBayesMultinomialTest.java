/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * NaiveBayesMultinomialTest.java
 * Copyright (C) 2013 University of Waikato, Hamilton, New Zealand
 */
package moa.classifiers.bayes;

import junit.framework.Test;
import junit.framework.TestSuite;
import moa.classifiers.AbstractClassifierTestCase;
import moa.classifiers.Classifier;
import moa.evaluation.BasicClassificationPerformanceEvaluator;
import moa.evaluation.ClassificationPerformanceEvaluator;

/**
 * Tests the NaiveBayesMultinomial classifier.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class NaiveBayesMultinomialTest
  extends AbstractClassifierTestCase {

  /**
   * Constructs the test case. Called by subclasses.
   *
   * @param name 	the name of the test
   */
  public NaiveBayesMultinomialTest(String name) {
    super(name);
  }

  /**
   * Called by JUnit before each test method.
   *
   * @throws Exception if an error occurs.
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    m_TestHelper.copyResourceToTmp("naivebayes.arff");
  }
  
  /**
   * Called by JUnit after each test method.
   *
   * @throws Exception	if tear-down fails
   */
  @Override
  protected void tearDown() throws Exception {
    m_TestHelper.deleteFileFromTmp("naivebayes.arff");
    
    super.tearDown();
  }

  /**
   * Returns the filenames (without path) of the input data files to use
   * in the regression test.
   *
   * @return		the filenames
   */
  @Override
  protected String[] getRegressionInputFiles() {
    return new String[]{
	"naivebayes.arff",
    };
  }

  /**
   * Returns the class index for the datasets.
   * 
   * @return		the class indices (0-based)
   */
  @Override
  protected int[] getRegressionInputClassIndex() {
    return new int[]{
	10,
    };
  }

  /**
   * Returns the index of the instances in the stream to inspect the 
   * performance/classification output of the classifiers.
   * 
   * @return		the inspection indices
   */
  @Override
  protected int[][] getRegressionInspectionPoints() {
    return new int[][]{
	new int[]{10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000},
    };
  }
  
  /**
   * Returns the classifier setups to use in the regression test.
   *
   * @return		the setups
   */
  @Override
  protected Classifier[] getRegressionClassifierSetups() {
    return new Classifier[]{
	new NaiveBayesMultinomial(),
    };
  }

  /**
   * Returns the evaluator setups to use in the regression test.
   *
   * @return		the setups
   */
  @Override
  protected ClassificationPerformanceEvaluator[] getRegressionEvaluatorSetups() {
    return new ClassificationPerformanceEvaluator[]{
	new BasicClassificationPerformanceEvaluator(),
    };
  }

  /**
   * Returns a test suite.
   *
   * @return		the test suite
   */
  public static Test suite() {
    return new TestSuite(NaiveBayesMultinomialTest.class);
  }

  /**
   * Runs the test from commandline.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    runTest(suite());
  }
}
