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

package org.logicng.explanations.unsatcores.algorithms;

import org.logicng.datastructures.Tristate;
import org.logicng.explanations.unsatcores.MUSConfig;
import org.logicng.explanations.unsatcores.UNSATCore;
import org.logicng.formulas.FormulaFactory;
import org.logicng.propositions.Proposition;
import org.logicng.solvers.MiniSat;

import java.util.ArrayList;
import java.util.List;

/**
 * A naive plain insertion-based MUS algorithm.
 * @version 1.1
 * @since 1.1
 */
public class PlainInsertionBasedMUS extends MUSAlgorithm {

  @Override
  public UNSATCore computeMUS(List<Proposition> propositions, FormulaFactory f, MUSConfig config) {
    List<Proposition> currentFormula = new ArrayList<Proposition>(propositions.size());
    currentFormula.addAll(propositions);
    final List<Proposition> mus = new ArrayList<Proposition>(propositions.size());
    final MiniSat solver = MiniSat.miniSat(f);
    while (!currentFormula.isEmpty()) {
      final List<Proposition> currentSubset = new ArrayList<Proposition>(propositions.size());
      Proposition transitionProposition = null;
      solver.reset();
      for (final Proposition p : mus)
        solver.add(p);
      int count = currentFormula.size();
      while (solver.sat() == Tristate.TRUE) {
        if (count < 0)
          throw new IllegalArgumentException("Cannot compute a MUS for a satisfiable formula set.");
        final Proposition removeProposition = currentFormula.get(--count);
        currentSubset.add(removeProposition);
        transitionProposition = removeProposition;
        solver.add(removeProposition);
      }
      currentFormula.clear();
      currentFormula.addAll(currentSubset);
      if (transitionProposition != null) {
        currentFormula.remove(transitionProposition);
        mus.add(transitionProposition);
      }
    }
    return new UNSATCore(mus, true);
  }
}
