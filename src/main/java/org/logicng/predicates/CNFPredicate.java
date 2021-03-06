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

package org.logicng.predicates;

import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaPredicate;

import static org.logicng.formulas.cache.PredicateCacheEntry.IS_CNF;

/**
 * CNF predicate.  Indicates whether a formula is in CNF or not.
 * @version 1.0
 * @since 1.0
 */
public final class CNFPredicate implements FormulaPredicate {
  @Override
  public boolean test(final Formula formula, boolean cache) {
    final Tristate cached = formula.predicateCacheEntry(IS_CNF);
    if (cached != Tristate.UNDEF)
      return cached == Tristate.TRUE;
    switch (formula.type()) {
      case FALSE:
      case TRUE:
      case LITERAL:
        return true;
      case NOT:
      case IMPL:
      case EQUIV:
      case PBC:
        return false;
      // AND and OR always have a cached CNF predicate (set in the constructor)
      default:
        throw new IllegalArgumentException("Cannot compute CNF predicate on " + formula.type());
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
