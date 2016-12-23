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

package org.logicng.explanations.unsatcores;

import org.logicng.propositions.Proposition;

import java.util.List;

/**
 * An unsatisfiable core (can be a minimal unsatisfiable sub-formula).
 * @version 1.1
 * @since 1.1
 */
final public class UNSATCore {

  private final List<Proposition> propositions;
  private final boolean isMUS;

  /**
   * Constructs a new unsatisfiable core.
   * @param propositions the propositions of the core
   * @param isMUS        {@code true} if it is a MUS, {@code false} otherwise
   */
  public UNSATCore(final List<Proposition> propositions, boolean isMUS) {
    this.propositions = propositions;
    this.isMUS = isMUS;
  }

  /**
   * Returns the propositions of this MUS.
   * @return the propositions of this MUS
   */
  public List<Proposition> propositions() {
    return this.propositions;
  }

  /**
   * Returns {@code true} if this core is a MUS, {@code false} otherwise.
   * @return {@code true} if this core is a MUS, {@code false} otherwise
   */
  public boolean isMUS() {
    return this.isMUS;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof UNSATCore))
      return false;
    UNSATCore unsatCore = (UNSATCore) o;
    if (isMUS != unsatCore.isMUS)
      return false;
    return propositions != null ? propositions.equals(unsatCore.propositions) : unsatCore.propositions == null;
  }

  @Override
  public int hashCode() {
    int result = propositions != null ? propositions.hashCode() : 0;
    result = 31 * result + (isMUS ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format("UNSATCore{isMUS=%s, propositions=%s}", this.isMUS, this.propositions);
  }
}
