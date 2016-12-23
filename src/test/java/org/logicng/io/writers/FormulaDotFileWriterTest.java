///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015-2016 Christoph Zengler                                //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.io.writers;

import org.junit.Assert;
import org.junit.Test;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.io.parsers.PseudoBooleanParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Unit tests for the {@link FormulaDotFileWriter}.
 * @version 1.1
 * @since 1.0
 */
public class FormulaDotFileWriterTest {

  private final FormulaFactory f = new FormulaFactory();
  private final PropositionalParser p = new PropositionalParser(f);
  private final PseudoBooleanParser pp = new PseudoBooleanParser(f);

  @Test
  public void testConstants() throws IOException {
    testFiles("false", f.falsum());
    testFiles("true", f.verum());
  }

  @Test
  public void testLiterals() throws IOException {
    testFiles("x", f.variable("x"));
    testFiles("not_x", f.literal("x", false));
  }

  @Test
  public void testFormulas() throws IOException, ParserException {
    final Formula f1 = p.parse("(a & b) <=> (~c => (x | z))");
    final Formula f2 = p.parse("a & b | b & ~c");
    final Formula f3 = p.parse("(a & b) <=> (~c => (a | b))");
    final Formula f4 = p.parse("~(a & b) | b & ~c");
    final Formula f5 = pp.parse("a | ~b | (2*a + 3*~b + 4*c <= 23)");
    testFiles("f1", f1);
    testFiles("f2", f2);
    testFiles("f3", f3);
    testFiles("f4", f4);
    testFiles("f5", f5);
  }

  @Test
  public void testDuplicateFormulaParts() throws ParserException, IOException {
    final Formula f6 = p.parse("(a & b) | (c & ~(a & b))");
    testFiles("f6", f6);
    final Formula f7 = p.parse("(c & d) | (a & b) | ((c & d) <=> (a & b))");
    testFiles("f7", f7);
  }

  private void testFiles(final String fileName, final Formula formula) throws IOException {
    FormulaDotFileWriter.write("tests/writers/temp/" + fileName + "_t.dot", formula, true);
    FormulaDotFileWriter.write("tests/writers/temp/" + fileName + "_f", formula, false);
    final File expectedT = new File("tests/writers/formulas-dot/" + fileName + "_t.dot");
    final File expectedF = new File("tests/writers/formulas-dot/" + fileName + "_f.dot");
    final File tempT = new File("tests/writers/temp/" + fileName + "_t.dot");
    final File tempF = new File("tests/writers/temp/" + fileName + "_f.dot");
    assertFilesEqual(expectedT, tempT);
    assertFilesEqual(expectedF, tempF);
  }

  private void assertFilesEqual(final File expected, final File actual) throws IOException {
    final BufferedReader expReader = new BufferedReader(new FileReader(expected));
    final BufferedReader actReader = new BufferedReader(new FileReader(actual));
    for (int lineNumber = 1; expReader.ready() && actReader.ready(); lineNumber++)
      Assert.assertEquals("Line " + lineNumber + " not equal", expReader.readLine(), actReader.readLine());
    if (expReader.ready())
      Assert.fail("Missing line(s) found, starting with \"" + expReader.readLine() + "\"");
    if (actReader.ready())
      Assert.fail("Additional line(s) found, starting with \"" + actReader.readLine() + "\"");
  }
}
